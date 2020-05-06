package com.staradmin.android.tasku.Activities.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.staradmin.android.tasku.Activities.Alarm.Adapter.AlarmAdapter;
import com.staradmin.android.tasku.Activities.Menu.MenuActivity;
import com.staradmin.android.tasku.Callback.callback_read_alarm;
import com.staradmin.android.tasku.Callback.callback_update_alarm;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.Model.AlarmItem;
import com.staradmin.android.tasku.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ListAlarmActivity extends AppCompatActivity implements AlarmAdapter.CallBack{
    FloatingActionButton FAB;
    String stringID;
    private String stringIDAlarm, stringTitle, stringTime, stringFreq, stringOnOff;
    private static final String TAG = "AlarmActivity";
    private Toolbar toolbar;
    List<AlarmItem> alarm_items;
    LocalStorage localStorage = new LocalStorage();
    private AlarmAdapter alarmAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alarm);

        initView();
        FAB = findViewById(R.id.fab_btn);

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListAlarmActivity.this, CreateAlarmActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initView(){
        toolbar = findViewById(R.id.toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AlarmKu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView alarmRecycle = findViewById(R.id.alarmRecycle);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        alarmRecycle.setLayoutManager(gridLayoutManager);
        importfromDatabase();
        alarmRecycle.setAdapter(alarmAdapter);
    }

    public void importfromDatabase(){
        alarm_items  = new ArrayList<>();
        ArrayList<HashMap<String, String>> alBookPick;
        stringID = localStorage.getCustomerId(getApplicationContext());
        alBookPick = Eksekusi(stringID);

        for(int i=0; i<alBookPick.size();++i){
            stringIDAlarm = alBookPick.get(i).get("id_alarm");
            stringTitle = alBookPick.get(i).get("alarm_title");
            stringTime = alBookPick.get(i).get("alarm_time");
            stringFreq = alBookPick.get(i).get("alarm_freq");
            stringOnOff = alBookPick.get(i).get("alarm_onOff");

            alarm_items.add(
                    new AlarmItem(stringIDAlarm,stringTime,stringTitle,stringFreq,Integer.parseInt(stringOnOff)));
        }
        alarmAdapter = new AlarmAdapter(alarm_items, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void sendIntent(AlarmItem alarm, String intentType) {
        Intent intent1 = new Intent(ListAlarmActivity.this, AlarmReceiver.class);
//        intent1.putExtra("intentType", intentType);
//        intent1.putExtra("AlarmId", alarm.getId_alarm());
        sendBroadcast(intent1);
    }

    @Override
    public void startAlarm(AlarmItem timeItem) {
        timeItem.setOnOff_alarm(1);
        updateOnOff(timeItem.getId_alarm(),timeItem.getTitle_alarm(),timeItem.getTime_alarm(),timeItem.getFreq_alarm(),"1");
        setAlarm(timeItem,0);
    }

    @Override
    public void cancelAlarm(AlarmItem timeItem) {
        timeItem.setOnOff_alarm(0);
        updateOnOff(timeItem.getId_alarm(),timeItem.getTitle_alarm(),timeItem.getTime_alarm(),timeItem.getFreq_alarm(),"0");
        deleteCancel(timeItem);
//        sendIntent(timeItem, Constants.OFF_INTENT);
    }

    public void setAlarm(AlarmItem alarm, int flags){
        Calendar myCalendar = Calendar.getInstance();
        Calendar calendar = (Calendar) myCalendar.clone();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(alarm.getTime_alarm().split(":")[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(alarm.getTime_alarm().split(":")[1].split(" ")[0]));
        calendar.set(Calendar.SECOND, 0);
        if (calendar.compareTo(myCalendar) <= 0) {
            calendar.add(Calendar.DATE, 1);
        }
        String alarmId = alarm.getId_alarm();
        Intent intent = new Intent(ListAlarmActivity.this, AlarmReceiver.class);
//        intent.putExtra("intentType", Constants.ADD_INTENT);
//        intent.putExtra("PendingId", alarmId);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(ListAlarmActivity.this, Integer.parseInt(alarmId),
                intent, flags);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    private void deleteCancel(AlarmItem alarm) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        String alarmId = alarm.getId_alarm();
        Intent intent = new Intent(ListAlarmActivity.this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(ListAlarmActivity.this, Integer.parseInt(alarmId),
                intent, 0);
        alarmManager.cancel(alarmIntent);
    }


    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_alarm read_alarm = new callback_read_alarm(ListAlarmActivity.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_alarm.execute(
                    v1
            ).get();
            Log.d(TAG, "Eksekusi: "+v1);

        } catch (Exception e) {

        }

        return arrayList;
    }

    public ArrayList<HashMap<String, String>> updateOnOff(String v1, String v2, String v3, String v4, String v5) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_update_alarm read_alarm = new callback_update_alarm(ListAlarmActivity.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_alarm.execute(
                    v1,
                    v2,
                    v3,
                    v4,
                    v5
            ).get();

        } catch (Exception e) {

        }

        return arrayList;
    }



    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(ListAlarmActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }



}
