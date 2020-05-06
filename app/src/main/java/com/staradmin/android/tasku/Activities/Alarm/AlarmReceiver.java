package com.staradmin.android.tasku.Activities.Alarm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import com.staradmin.android.tasku.R;

import androidx.legacy.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends BroadcastReceiver {
    // this hold pendingIntend id if one pendingIntend trigger. The PendingIntent'id is alarm'id
    public static String pendingId;
    MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

//        if (intent != null) {
//            // this hold information to service
//            Intent intentToService = new Intent(context, ListAlarmActivity.class);
//            try {
//                // getting intent key "intentType"
//                String intentType = intent.getExtras().getString("intentType");
//                switch (intentType) {
//                    case Constants.ADD_INTENT:
//                        // assign pendingId
//                        pendingId = intent.getExtras().getString("PendingId");
//                        intentToService.putExtra("ON_OFF", Constants.ADD_INTENT);
//                        context.startService(intentToService);
//
//                        break;
//                    case Constants.OFF_INTENT:
//                        // get alarm'id from extras
//                        String alarmId = intent.getExtras().getString("AlarmId");
//                        // sending to AlarmService
//                        intentToService.putExtra("ON_OFF", Constants.OFF_INTENT);
//                        intentToService.putExtra("AlarmId", alarmId);
//                        context.startService(intentToService);
//
//                        break;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();


    }
}
