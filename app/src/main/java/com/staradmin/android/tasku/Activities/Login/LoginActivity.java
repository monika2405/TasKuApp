package com.staradmin.android.tasku.Activities.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.staradmin.android.tasku.Callback.callback_login;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.Activities.Menu.MenuActivity;
import com.staradmin.android.tasku.R;
import com.staradmin.android.tasku.Activities.Register.RegisterActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    private Button mSign;
    private TextView mCreate;
    private EditText mUsername, mPassword;
    private String stringUsername, stringPassword;
    private static final String TAG = "LoginActivity";

    //Validation Email
    String user, pass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        final LocalStorage localStorage = new LocalStorage();

        if(localStorage.getCustomerId(getApplicationContext())!=""){
            finish();
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
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
                    ArrayList<HashMap<String, String>> alBookPick;
                    alBookPick = Eksekusi(stringUsername, stringPassword);
                    if(alBookPick.size()>0){
                        localStorage.setCustomerId(getApplicationContext(),alBookPick.get(0).get("id"));
                        localStorage.setusername(getApplicationContext(),stringUsername);
                        localStorage.setpassword(getApplicationContext(),stringPassword);
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(LoginActivity.this,"Password or Email is wrong", Toast.LENGTH_SHORT).show();
                    }
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
}
