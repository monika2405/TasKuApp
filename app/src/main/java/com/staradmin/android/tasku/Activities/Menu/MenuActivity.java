package com.staradmin.android.tasku.Activities.Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.staradmin.android.tasku.Activities.Login.LoginActivity;
import com.staradmin.android.tasku.Activities.Notes.ListNotesActivity;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.R;

public class MenuActivity extends AppCompatActivity {
    private LinearLayout mNotes;
    private Button mLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final LocalStorage localStorage = new LocalStorage();

        mNotes = findViewById(R.id.notes);
        mLogout = findViewById(R.id.logout);

        mNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ListNotesActivity.class);
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
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
