package com.staradmin.android.tasku.Activities.Alarm;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.staradmin.android.tasku.Activities.Alarm.Adapter.AlarmAdapter;
import com.staradmin.android.tasku.Callback.callback_read_alarm;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.Model.AlarmItem;
import com.staradmin.android.tasku.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListAlarmActivity extends AppCompatActivity {
    private FloatingActionButton FAB;
    String stringID;
    private String stringIDAlarm, stringTitle, stringDesc, stringTime, stringFreq;
    private static final String TAG = "AlarmActivity";
    private Toolbar mToolbar;
    private RecyclerView.LayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alarm);

        LocalStorage localStorage = new LocalStorage();

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<AlarmItem> alarmItems = new ArrayList<>();
        stringID = localStorage.getCustomerId(getApplicationContext());
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi(stringID);

        for(int i=0; i<alBookPick.size();++i){
            stringIDAlarm = alBookPick.get(i).get("id_alarm");
            stringTitle = alBookPick.get(i).get("alarm_title");
            stringDesc = alBookPick.get(i).get("alarm_desc");
            stringTime = alBookPick.get(i).get("alarm_time");
            stringFreq = alBookPick.get(i).get("alarm_freq");

            alarmItems.add(
                    new AlarmItem(stringIDAlarm,stringTime,stringTitle, stringDesc, stringFreq));
        }


        RecyclerView alarmRecycleView = findViewById(R.id.alarm_main);
        FAB = findViewById(R.id.add);
        gridLayoutManager = new GridLayoutManager(this, 1);
        alarmRecycleView.setHasFixedSize(true);
        alarmRecycleView.setLayoutManager(gridLayoutManager);
        alarmRecycleView.setAdapter(new AlarmAdapter(this,alarmItems));

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListAlarmActivity.this, CreateAlarmActivity.class);
                startActivity(intent);
            }
        });
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
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }
}
