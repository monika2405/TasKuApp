package com.staradmin.android.tasku.Activities.Alarm;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.staradmin.android.tasku.Callback.callback_update_alarm;
import com.staradmin.android.tasku.Fragment.TimePickerFragment;
import com.staradmin.android.tasku.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DisplayDetailAlarm extends AppCompatActivity {
    private static final String TAG = "detail_alarm";
    EditText timePicker;
    EditText mTitle, mDesc, mTime, mFreq;
    String mTitleNew, mDescNew, mTimeNew, mFreqNew;
    ToggleButton mSunday, mMonday, mTuesday, mWednesday, mThursday, mFriday, mSaturday;
    ProgressDialog mProgressDialog;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detail_alarm);

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

        Intent intent = getIntent();
        mTitleNew = intent.getStringExtra("iTitle");
        mDescNew = intent.getStringExtra("iDescription");
        mTimeNew = intent.getStringExtra("iTime");
        mFreqNew = intent.getStringExtra("iFreq");

        mTitle.setText(mTitleNew);
        mDesc.setText(mDescNew);
        mTime.setText(mTimeNew);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_alarm_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }else if(id == R.id.finish){
            mProgressDialog = new ProgressDialog(DisplayDetailAlarm.this);
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_dialog);
            mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String datetime = dateFormat.format(date);
            ArrayList<HashMap<String, String>> alBookPick;
            mTitleNew = mTitle.getText().toString();
            mDescNew = mDesc.getText().toString();
            alBookPick = Eksekusi(mTitleNew,mDescNew,datetime);
            if(alBookPick.size()>0){
                Intent intent = new Intent(DisplayDetailAlarm.this, ListAlarmActivity.class);
                startActivity(intent);
            }
        }
        return true;
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1, String v2, String v3) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_update_alarm update_book = new callback_update_alarm(DisplayDetailAlarm.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = update_book.execute(
                    v1

            ).get();
            Log.d(TAG, "Eksekusi: "+v1);
        } catch (Exception e) {

        }

        return arrayList;
    }
}
