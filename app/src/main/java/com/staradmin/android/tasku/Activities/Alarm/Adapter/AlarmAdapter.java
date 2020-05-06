package com.staradmin.android.tasku.Activities.Alarm.Adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.staradmin.android.tasku.Activities.Alarm.DisplayDetailAlarm;
import com.staradmin.android.tasku.Interfaces.ItemClickListener;
import com.staradmin.android.tasku.Model.AlarmItem;
import com.staradmin.android.tasku.R;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter  extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private List<AlarmItem> alarm_items;
    private CallBack mCallBack;

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlarmViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item,parent,false), mCallBack
        );
    }

    public interface CallBack {
        //Callback xử lý logic start alarm
        void startAlarm(AlarmItem timeItem);

        //Callback xử lý logic cancel alarm
        void cancelAlarm(AlarmItem timeItem);
    }


    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.time.setText(alarm_items.get(position).getTime_alarm());
        holder.date.setText(alarm_items.get(position).getFreq_alarm());
        holder.alarm_title.setText(alarm_items.get(position).getTitle_alarm());
        if(alarm_items.get(position).getOnOff_alarm()==0){
            holder.mSwitch.setChecked(false);
        }else{
            holder.mSwitch.setChecked(true);
        }
        holder.mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mSwitch.isChecked()){
                    mCallBack.startAlarm(alarm_items.get(position));
                }else{
                    mCallBack.cancelAlarm(alarm_items.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return alarm_items.size();
    }

    public AlarmAdapter(List<AlarmItem> alarm_items, CallBack mCallBack) {
        this.alarm_items = alarm_items;
        this.mCallBack = mCallBack;
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder{
        private TextView time, date, alarm_title;
        private Switch mSwitch;

        public AlarmViewHolder(@NonNull View itemView, CallBack CallBack) {
            super(itemView);
            alarm_title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            mSwitch = itemView.findViewById(R.id.switch1);
            mCallBack = CallBack;
        }
    }


}
