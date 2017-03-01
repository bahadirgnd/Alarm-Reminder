package com.bignerdranch.android.myproject;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by hakyu on 10/2/2017.
 */

public class RingtonePlayingService extends Service {
    MediaPlayer media_song;
    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startId;




    @Nullable
    @Override
    public IBinder onBind(Intent intent){

        Log.e("MyActivity", "In the Richard service");
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //Toast.makeText(this, "REACH RINGTONE SERVICE!", Toast.LENGTH_LONG).show();
        Log.i("LocalService","Received start id" + startId +":" + intent);

        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        int repeatalarm = intent.getIntExtra("REPEAT_ALARM", 0);
        String uriString = intent.getStringExtra("SEC_RINGTONE_URI");
        int checkbox = intent.getIntExtra("CHECK_BOX", 0);
        String start = (String) intent.getExtras().get("START");
        //String stop = (String) intent.getExtras().get("STOP");
        String ring_chosen = intent.getExtras().getString("RING_CHOSEN");
        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        String newString;

        if(checkbox == 1) {
            //Create notification when there is needed by user
            NotificationCompat.Builder b = new NotificationCompat.Builder(this);

            b.setContentTitle("ALARM RANG")
                    .setContentText(uriString)
                    .setSmallIcon(R.drawable.notice)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, b.build());
        }

        assert start != null;
        switch (start) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }

        //AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);
        if(!this.isRunning && startId == 1) {

            Log.e("if there was no sound", "and you want to start");
            //get the song to play from raw folder
            int resID = getResources().getIdentifier(ring_chosen, "raw", getPackageName());
            media_song = MediaPlayer.create(this, resID);
            media_song.start();
            this.isRunning = true;
            this.startId = 0;
        }
       else if (!this.isRunning && startId == 0){
            Log.e("if there was no sound", "and you want end");

            this.isRunning = false;
            this.startId = 0;
        }
        else if(this.isRunning && startId == 1){
            Log.e("if there is a sound", "and you want start");
            if(repeatalarm > 1) {
                media_song.stop();
                int resID = getResources().getIdentifier(ring_chosen, "raw", getPackageName());
                media_song = MediaPlayer.create(this, resID);
                media_song.start();
            }
                this.isRunning = true;
                this.startId = 0;
        }
        else{
            Log.e("if there is a sound", "and you want end");
            media_song.stop();
            media_song.reset();
            this.isRunning = false;
            this.startId = 0;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        //make it stopped
        Toast.makeText(this, "On Destroy called", Toast.LENGTH_SHORT).show();
    }
}
