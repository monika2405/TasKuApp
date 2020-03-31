package com.staradmin.android.tasku.Activities.Register;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.staradmin.android.tasku.Callback.callback_register;
import com.staradmin.android.tasku.Activities.Menu.MenuActivity;
import com.staradmin.android.tasku.Network.ConnectivityReceiver;
import com.staradmin.android.tasku.Network.MyApplication;
import com.staradmin.android.tasku.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    private Button mRegBtn;
    private TextInputEditText mfirstname, mlastname, memail, mphonenumb, mpassword, mconfirmpass, musername, mDisplayDate ;
    private String stringFirstName, stringLastName, stringEmail, stringPhoneNumb, stringPassword, stringConfirmPass, stringBirthDate, stringUsername;

    //set Birth Date
    private static final String TAG = "RegisterActivity";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //Validation Email
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegBtn= findViewById(R.id.registerBtn);
        mfirstname = findViewById(R.id.firstname);
        mlastname = findViewById(R.id.lastname);
        memail = findViewById(R.id.email);
        mphonenumb = findViewById(R.id.phonenumb);
        mpassword = findViewById(R.id.password);
        musername = findViewById(R.id.username);
        mconfirmpass = findViewById(R.id.confirmpass);

        //Birth Date
        mDisplayDate = findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,255,255)));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;

                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };


        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1, String v2,String v3, String v4, String v5, String v6){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_register update_book = new callback_register(RegisterActivity.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = update_book.execute(
                    v1
                    , v2
                    , v3
                    , v4
                    , v5
                    , v6
            ).get();
        }catch (Exception e){

        }

        return arrayList;
    }

    public boolean validasiPassword(String password, String confirmpass){
        if (password.equals(confirmpass)){
            return true;
        }else{
            return false;
        }
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
            stringFirstName = mfirstname.getText().toString();
            stringLastName = mlastname.getText().toString();
            stringEmail = memail.getText().toString();
            stringPhoneNumb = mphonenumb.getText().toString();
            stringPassword = mpassword.getText().toString();
            stringConfirmPass = mconfirmpass.getText().toString();
            stringUsername = musername.getText().toString();
            stringBirthDate = mDisplayDate.getText().toString();


            Log.d("First Name", stringFirstName);
            ArrayList<HashMap<String, String>> alBookPick = null;


            if(stringFirstName.isEmpty()){
                mfirstname.setError("Field cannot be empty");
            }
            else if(stringLastName.isEmpty()){
                mlastname.setError("Field cannot be empty");
            }else{
                alBookPick = Eksekusi(stringEmail, stringUsername, stringPassword, stringFirstName+" "+stringLastName, stringBirthDate, stringPhoneNumb);
                Intent intent = new Intent(RegisterActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        }else{
            androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(this);
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
