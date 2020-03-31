package com.staradmin.android.tasku.Activities.Profile;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.staradmin.android.tasku.Callback.callback_read_profile;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.R;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profPic, addProfPic;
    private TextView name, email, username, birthDate, handphoneNumb;
    private String mName, mEmail, mUsername, mBirthDate, mHandphone, mId;
    final LocalStorage localStorage = new LocalStorage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void initiateElement(){
        name = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        birthDate = findViewById(R.id.birthDate);
        handphoneNumb = findViewById(R.id.phone);
    }

    public void getDataFromDatabase(){
        mId = localStorage.getCustomerId(getApplicationContext());
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi(mId);
        for(int i=0; i<alBookPick.size();++i){
            mName = alBookPick.get(i).get("nama");
            mBirthDate = alBookPick.get(i).get("birth_date");
            mEmail = alBookPick.get(i).get("email");
            mHandphone = alBookPick.get(i).get("handphone_numb");
            mUsername = alBookPick.get(i).get("username");
        }
    }

    public void insertDatatoElement(){
        name.setText(mName);
        email.setText(mEmail);
        username.setText(mUsername);
        birthDate.setText(mBirthDate);
        handphoneNumb.setText(mHandphone);
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_profile read_profile = new callback_read_profile(ProfileActivity.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_profile.execute(
                    v1
            ).get();

        } catch (Exception e) {

        }

        return arrayList;
    }


}
