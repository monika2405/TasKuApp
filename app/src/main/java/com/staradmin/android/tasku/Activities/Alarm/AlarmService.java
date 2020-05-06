package com.staradmin.android.tasku.Activities.Alarm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.staradmin.android.tasku.R;

import androidx.core.app.NotificationCompat;

public class AlarmService extends IntentService {
    MediaPlayer mediaPlayer; // this object to manage media
    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//    }

    @Override
    public void onHandleIntent(Intent intent) {
        sendNotification("Wake Up! Wake Up!");
    }

    private void sendNotification(String msg) {
        Log.d("AlarmService", "Preparing to send notification...: " + msg);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, ListAlarmActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Alarm")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);


        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        //TODO: processing on and off ringtone
//        // get string from intent
//        String on_Off = intent.getExtras().getString("ON_OFF");
//        switch (on_Off) {
//            case Constants.ADD_INTENT: // if string like this set start media
//                // this is system default alarm alert uri
//                Uri uri = Settings.System.DEFAULT_ALARM_ALERT_URI;
//                // create mediaPlayer object
//                mediaPlayer = MediaPlayer.create(this, uri);
//                mediaPlayer.start();
//                break;
//            case Constants.OFF_INTENT:
//                // this check if user pressed cancel
//                // get the alarm cancel id to check if equal the
//                // pendingIntent'trigger id(pendingIntent request code)
//                // the AlarmReceiver.pendingIntentId is taken from AlarmReceiver
//                // when one pendingIntent trigger
//                String alarmId = intent.getExtras().getString("AlarmId");
//                // check if mediaPlayer created or not and if media is playing and id of
//                // alarm and trigger pendingIntent is same  then stop music and reset it
//                if (mediaPlayer != null && mediaPlayer.isPlaying() && alarmId == AlarmReceiver.pendingId) {
//                    // stop media
//                    mediaPlayer.stop();
//                    // reset it
//                    mediaPlayer.reset();
//                }
//                break;
//
//
//        }
//        return START_STICKY;
//
//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        //TODO: Xử lý logic tắt nhạc chuông
//        mediaPlayer.stop();
//        mediaPlayer.reset();
//    }
//
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
}
