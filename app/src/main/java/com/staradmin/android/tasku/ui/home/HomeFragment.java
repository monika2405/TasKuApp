package com.staradmin.android.tasku.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.AppBarConfiguration;

import com.staradmin.android.tasku.Activities.Alarm.ListAlarmActivity;
import com.staradmin.android.tasku.Activities.Login.LoginActivity;
import com.staradmin.android.tasku.Activities.Menu.MenuActivity;
import com.staradmin.android.tasku.Activities.Notes.ListNotesActivity;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.Network.ConnectivityReceiver;
import com.staradmin.android.tasku.Network.MyApplication;
import com.staradmin.android.tasku.R;

public class HomeFragment extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener{
    private LinearLayout mNotes;
    private LinearLayout mAlarm;
    private Button mLogout;
    final LocalStorage localStorage = new LocalStorage();
    private AppBarConfiguration mAppBarConfiguration;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mNotes = view.findViewById(R.id.notes);
        mAlarm= view.findViewById(R.id.alarm);

        checkConnection();

    }



    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        doSomething(isConnected);
    }
    @Override
    public void onResume() {
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
                    Intent intent = new Intent(getActivity().getApplication(), ListNotesActivity.class);
                    startActivity(intent);
                }
            });

            mAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity().getApplication(), ListAlarmActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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

