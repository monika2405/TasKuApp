package com.staradmin.android.tasku.Activities.Alarm.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.staradmin.android.tasku.Activities.Alarm.DisplayDetailAlarm;
import com.staradmin.android.tasku.Interfaces.ItemClickListener;
import com.staradmin.android.tasku.Model.AlarmItem;
import com.staradmin.android.tasku.R;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private List<AlarmItem> mAlarmItems;
    Context c;

    @NonNull
    @Override
    public AlarmAdapter.AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlarmAdapter.AlarmViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.AlarmViewHolder holder, int position) {
        holder.textTitle.setText(mAlarmItems.get(position).getTitle_alarm());
        holder.Description.setText(mAlarmItems.get(position).getDesc_alarm());
//       holder.freq.setText(mAlarmItems.get(position).getFreq_alarm());
        holder.time.setText(mAlarmItems.get(position).getTime_alarm());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String titleAlarm = mAlarmItems.get(position).getTitle_alarm();
                String descAlarm = mAlarmItems.get(position).getDesc_alarm();
                String freqAlarm = mAlarmItems.get(position).getFreq_alarm();
                String idAlarm = mAlarmItems.get(position).getId_alarm();
                String timeAlarm = mAlarmItems.get(position).getTime_alarm();

                Intent intent = new Intent(c, DisplayDetailAlarm.class);
                intent.putExtra("iID", idAlarm);
                intent.putExtra("iTitle", titleAlarm);
                intent.putExtra("iDescription", descAlarm);
                intent.putExtra("iFreq", freqAlarm);
                intent.putExtra("iTime", timeAlarm);

                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAlarmItems.size();
    }

    public AlarmAdapter(Context c, List<AlarmItem> alarmItems) {
        this.mAlarmItems = alarmItems;
        this.c = c;
    }



    class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textTitle, Description, freq, time;
        ItemClickListener mItemClickListener;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.title);
            Description = itemView.findViewById(R.id.desc);
            time = itemView.findViewById(R.id.time);

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
