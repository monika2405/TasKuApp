package com.staradmin.android.tasku.Activities.Alarm;

import android.app.TimePickerDialog;
import android.content.Intent;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.staradmin.android.tasku.Callback.callback_create_alarm;
import com.staradmin.android.tasku.Fragment.TimePickerFragment;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateAlarmActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    EditText timePicker;
    EditText mTitle, mDesc, mTime;
    ToggleButton mSunday, mMonday, mTuesday, mWednesday, mThursday, mFriday, mSaturday;
    String mTitleNew, mDescNew, mTimeNew;
    String mFreq="";
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timePicker = findViewById(R.id.time);
        mTitle = findViewById(R.id.title);
        mDesc = findViewById(R.id.desc);
        mTime = findViewById(R.id.time);
        mSunday = findViewById(R.id.sunday);
        mMonday = findViewById(R.id.monday);
        mTuesday = findViewById(R.id.tues);
        mWednesday = findViewById(R.id.wed);
        mThursday = findViewById(R.id.thu);
        mFriday = findViewById(R.id.fri);
        mSaturday = findViewById(R.id.sat);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(), "time picker");
            }
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timePicker.setText(hourOfDay+": "+minute);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_alarm_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        final LocalStorage localStorage = new LocalStorage();
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }else if(id == R.id.finish){
            if(mSunday.isChecked()){
                mFreq += "Sunday;";
            }
            if(mMonday.isChecked()){
                mFreq += "Monday;";
            }
            if(mTuesday.isChecked()){
                mFreq += "Tuesday;";
            }
            if(mWednesday.isChecked()){
                mFreq += "Wednesday;";
            }
            if(mThursday.isChecked()){
                mFreq += "Thursday;";
            }
            if (mFriday.isChecked()){
                mFreq += "Friday;";
            }
            if (mSaturday.isChecked()){
                mFreq += "Saturday;";
            }
            ArrayList<HashMap<String, String>> alBookPick;
            mTitleNew = mTitle.getText().toString();
            mDescNew = mDesc.getText().toString();
            mTimeNew = mTime.getText().toString();
            String id_user = localStorage.getCustomerId(getApplicationContext());
            alBookPick = Eksekusi(id_user,mTitleNew,mDescNew,mTimeNew,mFreq);
            if(alBookPick.size()>0){
                Intent intent = new Intent(CreateAlarmActivity.this, ListAlarmActivity.class);
                startActivity(intent);
            }
        }
        return true;
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1, String v2, String v3, String v4, String v5) {
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
