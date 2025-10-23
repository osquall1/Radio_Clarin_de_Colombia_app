package com.example.radioclarinapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import android.app.Service;
import android.os.IBinder;

public class RadioService extends Service {

    private ExoPlayer player;
    private String radioUrl = "https://stream.zeno.fm/2kx8xugz668uv";
    private static final String CHANNEL_ID = "radio_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Radio Playback",
                    NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        player = new ExoPlayer.Builder(this).build();
        MediaItem mediaItem = MediaItem.fromUri(radioUrl);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("NotificationTrampoline")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Reproduciendo Radio")
                .setContentText("Escuchando radio en segundo plano...")
                .setSmallIcon(R.drawable.pause_ico)
                .setOngoing(true)
                .build();
        startForeground(1, notification);

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
        stopForeground(true);
    }

}
