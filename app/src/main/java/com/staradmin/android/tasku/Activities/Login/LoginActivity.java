package com.staradmin.android.tasku.Activities.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.staradmin.android.tasku.Callback.callback_login;
import com.staradmin.android.tasku.DrawerActivity;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.Activities.Menu.MenuActivity;
import com.staradmin.android.tasku.Network.ConnectivityReceiver;
import com.staradmin.android.tasku.Network.MyApplication;
import com.staradmin.android.tasku.R;
import com.staradmin.android.tasku.Activities.Register.RegisterActivity;
import com.staradmin.android.tasku.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;


public class LoginActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    private Button mSign;
    private TextView mCreate;
    private EditText mUsername, mPassword;
    private String stringUsername, stringPassword;
    private static final String TAG = "LoginActivity";
    private static final String DEBUG_TAG = "NetworkStatusExample";
    final LocalStorage localStorage = new LocalStorage();

    //Validation Email
    String user, pass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        if(localStorage.getCustomerId(getApplicationContext())!=""){

            finish();
            Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
            startActivity(intent);
        }
        mSign = findViewById(R.id.btnSign);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mCreate = findViewById(R.id.create);

        mSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringUsername = mUsername.getText().toString();
                stringPassword = mPassword.getText().toString();
                Log.d(TAG, "onClick: " + user);
                Log.d(TAG, "onClick: " + pass);
                if (stringUsername.isEmpty()) {
                    mUsername.setError("Field cannot be empty");
                }
                else if (stringPassword.isEmpty()) {
                    mPassword.setError("Field cannot be empty");
                }
                else {
                    checkConnection();
                }
            }

        });

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1, String v2) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_login update_book = new callback_login(LoginActivity.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = update_book.execute(
                    v1
                    , v2
            ).get();
            Log.d(TAG, "Eksekusi: "+v1);
            Log.d(TAG, "Eksekusi: "+v2);
        } catch (Exception e) {

        }

        return arrayList;
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
            ArrayList<HashMap<String, String>> alBookPick;
            alBookPick = Eksekusi(stringUsername, stringPassword);
            if(alBookPick.size()>0) {
                localStorage.setCustomerId(getApplicationContext(), alBookPick.get(0).get("id"));
                localStorage.setusername(getApplicationContext(), stringUsername);
                localStorage.setpassword(getApplicationContext(), stringPassword);

                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(LoginActivity.this,"Password or Email is wrong", Toast.LENGTH_SHORT).show();
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
