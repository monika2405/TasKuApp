package com.staradmin.android.tasku.Activities.Book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.staradmin.android.tasku.Activities.Menu.MenuActivity;
import com.staradmin.android.tasku.Callback.callback_read_book;
import com.staradmin.android.tasku.Callback.callback_search_buku;
import com.staradmin.android.tasku.LocalStorage;
import com.staradmin.android.tasku.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookList extends AppCompatActivity implements SearchView.OnQueryTextListener{
    WebView webView;
    LinearLayout title;
    FloatingActionButton addPDF;
    private String stringIDBuku, stringTitle, stringURLBuku, stringURLCover;
    RecyclerView BookView;
    public static List<Book> book_items;
    String stringID;
    LocalStorage localStorage = new LocalStorage();
    private boolean isPdfEmpty = true;
    private Activity mActivity;
    private Context mContext;
    private static final String WEB_URL = "web_url";
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        mActivity = BookList.this;
        mContext = getApplicationContext();
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();




    }

    public void initView(){

        BookView = findViewById(R.id.BookView);
        book_items = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        BookView.setLayoutManager(gridLayoutManager);
        importFromDatabase();
        BookAdapter adapter = new BookAdapter(getApplicationContext(),book_items, new BookAdapter.OnClicked() {
            @Override
            public void onClick(Book book) {
                String url = book.getUrl_buku();
                Intent intent = new Intent(BookList.this, PDFActivity.class);
                intent.putExtra(WEB_URL, url);
                startActivity(intent);
            }
        });

        BookView.setAdapter(adapter);
    }

    public void importFromDatabase(){
        book_items= new ArrayList<>();
        ArrayList<HashMap<String, String>> alBookPick;
        stringID = localStorage.getCustomerId(getApplicationContext());
        alBookPick = Eksekusi(stringID);
        for(int i=0; i<alBookPick.size();++i){
            stringIDBuku = alBookPick.get(i).get("id_buku");
            stringTitle = alBookPick.get(i).get("judul_buku");
            stringURLBuku = alBookPick.get(i).get("url_buku");
            stringURLCover = alBookPick.get(i).get("url_cover");
            book_items.add(
                    new Book(stringIDBuku,stringTitle, stringURLBuku,stringURLCover));
        }

    }
    public ArrayList<HashMap<String, String>> Eksekusi(String v1) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_read_book read_notes = new callback_read_book(BookList.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = read_notes.execute(
                    v1
            ).get();


        } catch (Exception e) {

        }

        return arrayList;
    }

    public void searchDatabase(String judul){
        book_items= new ArrayList<>();
        ArrayList<HashMap<String, String>> alBookPick;
        stringID = localStorage.getCustomerId(getApplicationContext());
        alBookPick = searchAction(judul);
        for(int i=0; i<alBookPick.size();++i){
            stringIDBuku = alBookPick.get(i).get("id_buku");
            stringTitle = alBookPick.get(i).get("judul_buku");
            stringURLBuku = alBookPick.get(i).get("url_buku");
            stringURLCover = alBookPick.get(i).get("url_cover");
            book_items.add(
                    new Book(stringIDBuku,stringTitle, stringURLBuku,stringURLCover));
        }
        BookView.setVisibility(View.VISIBLE);
        BookAdapter adapter = new BookAdapter(getApplicationContext(),book_items, new BookAdapter.OnClicked() {
            @Override
            public void onClick(Book book) {
                String url = book.getUrl_buku();
                Intent intent = new Intent(BookList.this, PDFActivity.class);
                intent.putExtra(WEB_URL, url);
                startActivity(intent);
            }
        });

        BookView.setAdapter(adapter);
    }

    public ArrayList<HashMap<String,String>> searchAction(String nama_buku){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        callback_search_buku search_bengkel = new callback_search_buku(BookList.this);
        //Log.d("CEKIDBOOK",id_book);
        try {
            arrayList = search_bengkel.execute(
                    nama_buku
            ).get();

        } catch (Exception e) {

        }

        return arrayList;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(BookList.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        book_items.clear();
        BookView.setVisibility(View.GONE);
        searchDatabase(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
