package com.staradmin.android.tasku.Activities.Alarm;

import android.app.TimePickerDialog;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.staradmin.android.tasku.Callback.callback_create_alarm;
import com.staradmin.android.tasku.Fragment.TimePickerFragment;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateAlarmActivity extends AppCompatActivity  {
    boolean check = false;
    private CheckBox mon, tue, wed, thurs, fri, sat, sun;
    EditText title;
    private TextView tv1;
    String mFreq,mTitleNew, mTimeNew;
    String stringID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
        LocalStorage localStorage = new LocalStorage();
        final TextView tv = (TextView) findViewById(R.id.tv);

        final TimePicker tp = (TimePicker) findViewById(R.id.tp);

        Button btn = (Button) findViewById(R.id.btn);
        mon = (CheckBox) findViewById(R.id.mon);
        tue = (CheckBox) findViewById(R.id.tue);
        wed = (CheckBox) findViewById(R.id.wed);
        thurs = (CheckBox) findViewById(R.id.thurs);
        fri = (CheckBox) findViewById(R.id.fri);
        sat = (CheckBox) findViewById(R.id.sat);
        sun = (CheckBox) findViewById(R.id.sun);
        tv1 = findViewById(R.id.tv1);
        title = findViewById(R.id.alarm_title);

        //Set the TimePicker view to 24 hour view
        tp.setIs24HourView(true);

        //Set a TimeChangedListener for TimePicker widget
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //Display the new time to app interface
                String AMPM = "AM";
                if(hourOfDay>11)
                {
                    hourOfDay = hourOfDay;
                    AMPM = "PM";
                }
                tv.setText("" + hourOfDay + ":" + minute + " " + AMPM);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            String msg="";
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int hourOfDay = tp.getCurrentHour(); //Get TimePicker current hour
                int minute = tp.getCurrentMinute(); //Get TimePicker current minute

                //Display the TimePicker current time to app interface
                String AMPM = "AM";
                if(hourOfDay>11)
                {
                    //Get the current hour as AM PM 12 hour format
                    hourOfDay = hourOfDay;
                    AMPM = "PM";
                }
                tv.setText(hourOfDay + ":" + minute + " " +AMPM );
                if(mon.isChecked())
                    msg = msg + " Mon, ";
                else
                    msg="";
                if(tue.isChecked())
                    msg = msg + " Tue, ";
                if(wed.isChecked())
                    msg = msg + " Wed, ";
                if(thurs.isChecked())
                    msg = msg + " Thu, ";
                if(fri.isChecked())
                    msg = msg + " Fri, ";
                if(sat.isChecked())
                    msg = msg + " Sat, ";
                if(sun.isChecked())
                    msg = msg + " Sun, ";
                tv1.setText(msg);
                String[]msg1 = msg.split(",");
                String msg2 = String.join(";",msg1);
                ArrayList<HashMap<String, String>> alBookPick;
                mFreq = msg2;
                mTitleNew = title.getText().toString();
                mTimeNew = tv.getText().toString();
                String id_user = localStorage.getCustomerId(getApplicationContext());
                alBookPick = Eksekusi(id_user,mTitleNew,mTimeNew,mFreq,"0");
                if(alBookPick.size()>0){
                    Intent intent = new Intent(CreateAlarmActivity.this, ListAlarmActivity.class);
                    startActivity(intent);
                }
            }

        });

    }


    public void onCheckBoxChecked(View view) {
        CheckBox checkBox = findViewById(view.getId());
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1, String v2, String v3, String v4,String v5) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_create_alarm update_book = new callback_create_alarm(CreateAlarmActivity.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = update_book.execute(
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
}
