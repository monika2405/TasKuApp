package com.staradmin.android.tasku.Activities.Notes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.staradmin.android.tasku.Activities.Notes.DisplayDetailNote;
import com.staradmin.android.tasku.Interfaces.ItemClickListener;
import com.staradmin.android.tasku.Model.NotesItem;
import com.staradmin.android.tasku.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<NotesItem> mNotesItems;
    Context c;


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.rev_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.textTitle.setText(mNotesItems.get(position).getTitle());
        holder.Description.setText(mNotesItems.get(position).getNotes());
        holder.date.setText(mNotesItems.get(position).getDate());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String titleNote = mNotesItems.get(position).getTitle();
                String descNote = mNotesItems.get(position).getNotes();
                String dateNote = mNotesItems.get(position).getDate();
                String idNote = mNotesItems.get(position).getId();
//                BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.mImageView.getDrawable();
//                Bitmap bitmap = bitmapDrawable.getBitmap();
//
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] bytes = stream.toByteArray();


                Intent intent = new Intent(c, DisplayDetailNote.class);
                intent.putExtra("iID", idNote);
                intent.putExtra("iTitle", titleNote);
                intent.putExtra("iDescription", descNote);
                intent.putExtra("iDate", dateNote);
//                intent.putExtra("iImage", bytes);
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotesItems.size();
    }

    public NotesAdapter(Context c, List<NotesItem> bengkelItems) {
        this.mNotesItems = bengkelItems;
        this.c = c;
    }



    class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textTitle, Description, date;
        ItemClickListener mItemClickListener;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            Description = itemView.findViewById(R.id.Description);
            date = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            this.mItemClickListener.onItemClickListener(v,getLayoutPosition());

        }

        public void setItemClickListener(ItemClickListener ic){
            this.mItemClickListener=ic;

        }
    }
}
