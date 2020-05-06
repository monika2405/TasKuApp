package com.staradmin.android.tasku.Activities.Book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.staradmin.android.tasku.Activities.Book.BookList;
import com.staradmin.android.tasku.Activities.Main.MainActivity;
import com.staradmin.android.tasku.Activities.Menu.MenuActivity;
import com.staradmin.android.tasku.R;

public class PDFActivity extends AppCompatActivity {
    WebView webView;
    private ProgressBar progressBar;
    private static final String WEB_URL = "web_url";
    private String url;
    private Toolbar mToolbar;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        url = getIntent().getStringExtra(WEB_URL);

        webView = findViewById(R.id.WV);
        progressBar = findViewById(R.id.pb);
        progressBar.setVisibility(View.VISIBLE);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
                progressBar.setVisibility(View.GONE);
            }
        });
        //https://docs.google.com/viewerng/viewer?embedded=true&url=
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+ url);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(PDFActivity.this, BookList.class);
            startActivity(intent);
            finish();
        }
        return true;
    }


}
