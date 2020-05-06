package com.staradmin.android.tasku.Activities.Menu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.staradmin.android.tasku.Activities.Alarm.ListAlarmActivity;
import com.staradmin.android.tasku.Activities.Book.BookList;
import com.staradmin.android.tasku.Activities.Keuangan.TransactionActivity;
import com.staradmin.android.tasku.Activities.Login.LoginActivity;
import com.staradmin.android.tasku.Activities.Main.MainActivity;
import com.staradmin.android.tasku.Activities.Notes.ListNotesActivity;
import com.staradmin.android.tasku.Activities.Profile.ProfileActivity;
import com.staradmin.android.tasku.Callback.callback_read_profile;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.Network.ConnectivityReceiver;
import com.staradmin.android.tasku.Network.MyApplication;
import com.staradmin.android.tasku.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

public class MenuActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener ,NavigationView.OnNavigationItemSelectedListener{
    private LinearLayout mNotes;
    private LinearLayout mAlarm;
    private LinearLayout mBook;
    private LinearLayout mDompet;
    private Button mLogout;
    final LocalStorage localStorage = new LocalStorage();
    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    String mId, mName, mEmail, mImageURL;
    TextView nama, email;
    ImageView profPic;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mNotes = findViewById(R.id.notes);
        mAlarm= findViewById(R.id.alarm);
        mBook = findViewById(R.id.buku);
        mDompet = findViewById(R.id.dompetKu);
        mContext = getApplicationContext();

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        nama = header.findViewById(R.id.name);
        email = header.findViewById(R.id.email);
        profPic = header.findViewById(R.id.img_profile);
        initDrawer();


        checkConnection();

    }

    public void initDrawer(){
        mId = localStorage.getCustomerId(getApplicationContext());
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi(mId);
        for(int i=0; i<alBookPick.size();++i){
            mName = alBookPick.get(i).get("nama");
            mEmail = alBookPick.get(i).get("email");
            mImageURL = alBookPick.get(i).get("image_profile");
        }

        nama.setText(mName);
        email.setText(mEmail);
        if (!mImageURL.equals("null")) {
            String IMAGE_URL = "https://dev.projectlab.co.id/mit/1417002/images/profPic/";
            Glide.with(mContext).load(IMAGE_URL + mImageURL).into(profPic);
        } else {
            Glide.with(mContext).load(R.drawable.baseline_account_circle_black_48).into(profPic);
        }
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

            mBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuActivity.this, BookList.class);
                    startActivity(intent);
                }
            });

            mDompet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuActivity.this, TransactionActivity.class);
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent homeIntent = new Intent(MenuActivity.this, MenuActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.nav_profile:
                Intent profileIntent = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.nav_log_out:
                localStorage.logout(getApplicationContext(), localStorage.getCustomerId(getApplicationContext()));
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                homeIntent = new Intent(MenuActivity.this, MenuActivity.class);
                startActivity(homeIntent);
                break;
        }
        return false;
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_profile read_profile = new callback_read_profile(MenuActivity.this);
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
