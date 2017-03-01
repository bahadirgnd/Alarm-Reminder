package com.bignerdranch.android.myproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;



/**
 * Created by hakyu on 10/2/2017.
 */

public class AlarmReceiver extends BroadcastReceiver{
    private static final int MY_NOTIFICATION_ID=1;
    private final String myBlog = "http://android-er.blogspot.com/";

    @Override
    public void onReceive(Context context, Intent intent) {
        int repeatalarm = intent.getIntExtra("REPEAT_ALARM", 0);
        String ring_chosen = intent.getExtras().getString("RING_CHOSEN");
        int checkboxnotif = intent.getIntExtra("CHECK_BOX", 0);
        //String stop = (String)intent.getExtras().get("STOP");
        String start = (String)intent.getExtras().get("START");
        Log.e("MyActivity", "In the receiver with " + start);
        //Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
        String uriString = intent.getStringExtra("KEY_TONE_URL");

        Intent serviceIntent = new Intent(context,RingtonePlayingService.class);
        serviceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        serviceIntent.putExtra("REPEAT_ALARM", repeatalarm);
        serviceIntent.putExtra("SEC_RINGTONE_URI", uriString);
        serviceIntent.putExtra("CHECK_BOX", checkboxnotif);
        serviceIntent.putExtra("START", start);
        //serviceIntent.putExtra("STOP", stop);
        serviceIntent.putExtra("RING_CHOSEN", ring_chosen);
        context.startService(serviceIntent);

    }

}
