package com.staradmin.android.tasku.Activities.Menu;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.staradmin.android.tasku.Activities.Alarm.ListAlarmActivity;
import com.staradmin.android.tasku.Activities.Login.LoginActivity;
import com.staradmin.android.tasku.Activities.Notes.ListNotesActivity;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.Network.ConnectivityReceiver;
import com.staradmin.android.tasku.Network.MyApplication;
import com.staradmin.android.tasku.R;

import androidx.navigation.ui.AppBarConfiguration;

public class MenuActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    private LinearLayout mNotes;
    private LinearLayout mAlarm;
    private Button mLogout;
    final LocalStorage localStorage = new LocalStorage();
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mNotes = findViewById(R.id.notes);
        mLogout = findViewById(R.id.logout);
        mAlarm= findViewById(R.id.alarm);

        checkConnection();

    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        doSomething(isConnected);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        doSomething(isConnected);
    }

    private void doSomething(boolean isConnected) {
        if (isConnected){
            mNotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuActivity.this, ListNotesActivity.class);
                    startActivity(intent);
                }
            });

            mAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuActivity.this, ListAlarmActivity.class);
                    startActivity(intent);
                }
            });

            mLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    localStorage.logout(getApplicationContext(), localStorage.getCustomerId(getApplicationContext()));
                    Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("No Internet Connection");
            alertDialog.setMessage("Check your Internet Connection");
            alertDialog.setNegativeButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,	int which) {
                            // Write your code here to execute after dialog
                            dialog.cancel();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();
        }

    }


}
