package com.staradmin.android.tasku.Activities.Book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;
import com.staradmin.android.tasku.R;

import java.io.File;

public class PDFViewerActivity extends AppCompatActivity {
    private PDFView pdfView;
    private ProgressBar progressBar;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pdfView = findViewById(R.id.PDFView);
        progressBar = findViewById(R.id.pb);

        Intent intent = getIntent();
        final int position = intent.getIntExtra("position",0);

        progressBar.setVisibility(View.VISIBLE);

        FileLoader.with(this)
                .load(BookList.book_items.get(position).getUrl_buku())
                .fromDirectory("PDFFiles",FileLoader.DIR_EXTERNAL_PUBLIC)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        progressBar.setVisibility(View.GONE);

                        final File pdfFile = response.getBody();
                        pdfView.fromUri(Uri.parse(BookList.book_items.get(position).getUrl_buku()))
                                .password(null)
                                .defaultPage(0)
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .onDraw(new OnDrawListener() {
                                    @Override
                                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                    }
                                }).onDrawAll(new OnDrawListener() {
                            @Override
                            public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                            }
                        }).onPageError(new OnPageErrorListener() {
                            @Override
                            public void onPageError(int page, Throwable t) {
                                Toast.makeText(PDFViewerActivity.this, "Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).onPageChange(new OnPageChangeListener() {
                            @Override
                            public void onPageChanged(int page, int pageCount) {

                            }
                        }).onTap(new OnTapListener() {
                            @Override
                            public boolean onTap(MotionEvent e) {
                                return true;
                            }
                        }).onRender(new OnRenderListener() {
                            @Override
                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                pdfView.fitToWidth();
                            }
                        }).enableAnnotationRendering(true)
                                .invalidPageColor(Color.WHITE)
                                .load();

                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {

                        Toast.makeText(PDFViewerActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(PDFViewerActivity.this, BookList.class);
            startActivity(intent);
            finish();
        }
        return true;
    }


}
