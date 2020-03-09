package com.staradmin.android.tasku.Activities.Notes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.staradmin.android.tasku.Activities.Notes.Adapter.NotesAdapter;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.Model.NotesItem;
import com.staradmin.android.tasku.Callback.callback_read_notes;
import com.staradmin.android.tasku.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListNotesActivity extends AppCompatActivity {
    private FloatingActionButton FAB;
    String stringID;
    private String stringIDNotes, stringTitle, stringDesc, stringDate;
    private static final String TAG = "LoginActivity";
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);

        LocalStorage localStorage = new LocalStorage();

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<NotesItem> notesItems = new ArrayList<>();
        stringID = localStorage.getCustomerId(getApplicationContext());
        ArrayList<HashMap<String, String>> alBookPick;
        alBookPick = Eksekusi(stringID);

        for(int i=0; i<alBookPick.size();++i){
            stringIDNotes = alBookPick.get(i).get("id_notes");
            stringTitle = alBookPick.get(i).get("notes_title");
            stringDesc = alBookPick.get(i).get("isi_notes");
            stringDate = alBookPick.get(i).get("time_upload");
            notesItems.add(
                    new NotesItem(stringIDNotes,stringTitle, stringDesc,stringDate));
        }


        RecyclerView notesRecycleView = findViewById(R.id.rv_main);
        FAB = findViewById(R.id.add);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        notesRecycleView.setLayoutManager(staggeredGridLayoutManager);

        notesRecycleView.setAdapter(new NotesAdapter(this,notesItems));

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListNotesActivity.this, CreateNotesActivity.class);
                startActivity(intent);
            }
        });
    }
    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_notes read_notes = new callback_read_notes(ListNotesActivity.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_notes.execute(
                    v1
            ).get();
            Log.d(TAG, "Eksekusi: "+v1);

        } catch (Exception e) {

        }

        return arrayList;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

}
