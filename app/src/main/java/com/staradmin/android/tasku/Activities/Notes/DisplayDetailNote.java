package com.staradmin.android.tasku.Activities.Notes;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.staradmin.android.tasku.Callback.callback_delete_notes;
import com.staradmin.android.tasku.Callback.callback_update_notes;
import com.staradmin.android.tasku.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DisplayDetailNote extends AppCompatActivity {
    private static final String TAG = "detail_note";
    TextView mTitle, mDesc, mDate;
    String mTitleNew, mDescNew, mDateNew, mIdNew;
    ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
//    ImageView mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detail_note);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mTitle = findViewById(R.id.titleNote);
        mDesc= findViewById(R.id.descNote);
        mDate = findViewById(R.id.dateNote);
//        mImage = findViewById(R.id.imageID);

        Intent intent = getIntent();
        mTitleNew = intent.getStringExtra("iTitle");
        mDescNew = intent.getStringExtra("iDescription");
        mDateNew = intent.getStringExtra("iDate");
        mIdNew = intent.getStringExtra("iID");

//        byte[] mBytes = getIntent().getByteArrayExtra("iImage");

//        Bitmap bitmap = BitmapFactory.decodeByteArray(mBytes,0,mBytes.length);
        mTitle.setText(mTitleNew);
        mDesc.setText(mDescNew);
        mDate.setText(mDateNew);
//        mImage.setImageBitmap(bitmap);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==android.R.id.home){
            Intent intent = new Intent(DisplayDetailNote.this,ListNotesActivity.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String title = mTitle.getText().toString();
            String desc = mDesc.getText().toString();
            intent.putExtra(Intent.EXTRA_SUBJECT,title);
            intent.putExtra(Intent.EXTRA_TEXT,desc);
            startActivity(Intent.createChooser(intent,"Share using"));
        }else if(id == R.id.delete){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Confirm Delete...");
            alertDialog.setMessage("Are you sure you want delete this?");
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            // Write your code here to execute after dialog
                            mProgressDialog = new ProgressDialog(DisplayDetailNote.this);
                            mProgressDialog.show();
                            mProgressDialog.setContentView(R.layout.progress_dialog);
                            mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            ArrayList<HashMap<String, String>> alBookPick;
                            alBookPick = Eksekusi(mIdNew);
                            if(alBookPick.size()>0){
                                Intent intent = new Intent(DisplayDetailNote.this, ListNotesActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,	int which) {
                            // Write your code here to execute after dialog
                            dialog.cancel();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();

        }else if(id == R.id.finish){
            mProgressDialog = new ProgressDialog(DisplayDetailNote.this);
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_dialog);
            mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String datetime = dateFormat.format(date);
            ArrayList<HashMap<String, String>> alBookPick;
            mTitleNew = mTitle.getText().toString();
            mDescNew = mDesc.getText().toString();
            alBookPick = Eksekusi2(mTitleNew,mDescNew,datetime,mIdNew);
            if(alBookPick.size()>0){
                Intent intent = new Intent(DisplayDetailNote.this, ListNotesActivity.class);
                startActivity(intent);
            }
        }
        return true;
    }

    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_delete_notes update_book = new callback_delete_notes(DisplayDetailNote.this);
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

    public ArrayList<HashMap<String, String>> Eksekusi2(String v1,String v2,String v3,String v4) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_update_notes update_book = new callback_update_notes(DisplayDetailNote.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = update_book.execute(
                    v1,
                    v2,
                    v3,
                    v4
            ).get();
            Log.d(TAG, "Eksekusi: "+v1);
        } catch (Exception e) {

        }

        return arrayList;
    }
}
