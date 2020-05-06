package com.staradmin.android.tasku.Activities.Book;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.staradmin.android.tasku.R;

import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookItemViewHolder>{
    private List<Book> book_items;
    private Context context;
    ItemClickListener itemClickListener;

    public interface OnClicked{
        void onClick (Book bookData);
    }

    private BookAdapter.OnClicked onClick;
    public BookAdapter(Context context, List<Book> book_items, OnClicked onClick) {
        this.context = context;
        this.book_items = book_items;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public BookItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BookItemViewHolder holder, int position) {
        holder.bind(book_items.get(position), onClick);



    }

    @Override
    public int getItemCount() {
        return book_items.size();
    }

    BookAdapter(List<Book> book_items) {
        this.book_items = book_items;
    }

    class BookItemViewHolder extends RecyclerView.ViewHolder{
        private TextView textTitle;
        private ImageView imgCover;
        private CardView cv;

        public BookItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            imgCover = itemView.findViewById(R.id.img_book);
            cv = itemView.findViewById(R.id.cardView);

        }

        private void bind(final Book book, final OnClicked onClick){
            textTitle.setText(book.getTitle());
            new DownloadImageTask(imgCover).execute(book.getUrl_cover());


            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.onClick(book);
                }
            });
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
