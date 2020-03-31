package com.staradmin.android.tasku.Activities.Notes;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.staradmin.android.tasku.Callback.callback_notes;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.Network.ConnectivityReceiver;
import com.staradmin.android.tasku.Network.MyApplication;
import com.staradmin.android.tasku.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CreateNotesActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    private EditText mTitle, mContent;
    private Button mSubmit;
    private String stringTitle, stringContent;
    final LocalStorage localStorage = new LocalStorage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        mTitle = findViewById(R.id.title);
        mContent = findViewById(R.id.content);
        mSubmit = findViewById(R.id.submit);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();

            }
        });
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1, String v2,String v3, String v4){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_notes update_book = new callback_notes(CreateNotesActivity.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = update_book.execute(
                    v1
                    , v2
                    , v3
                    , v4

            ).get();
        }catch (Exception e){

        }

        return arrayList;
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
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String id = localStorage.getCustomerId(getApplicationContext());
            String datetime = dateFormat.format(date);
            stringTitle = mTitle.getText().toString();
            stringContent = mContent.getText().toString();
            ArrayList<HashMap<String, String>> alBookPick = null;
            alBookPick = Eksekusi(id,stringTitle,stringContent,datetime);

            if (alBookPick.size()>0){
                Toast.makeText(CreateNotesActivity.this,"Notes Saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateNotesActivity.this, ListNotesActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(CreateNotesActivity.this,"Error", Toast.LENGTH_SHORT).show();
            }
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
