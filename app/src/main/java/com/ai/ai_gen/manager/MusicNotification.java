package com.ai.ai_gen.manager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.ai.ai_gen.R;
import com.ai.ai_gen.activity.NotificationActivity;
import com.ai.ai_gen.bean.MusicViewBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class MusicNotification extends Notification {
    private static MusicNotification notifyInstance = null;
    private final int NOTIFICATION_ID = 10001;
    private Notification musicNotifi = null;
    private NotificationManager notificationManager = null;
    private Context context;
    private RemoteViews remoteViews;
    private final int REQUEST_CODE = 30000;

    private final String MUSIC_NOTIFICATION_ACTION_PLAY = "musicnotificaion.To.PLAY";
    private final String MUSIC_NOTIFICATION_ACTION_CLOSE = "musicnotificaion.To.CLOSE";
    private final String MUSIC_NOTIFICAION_INTENT_KEY = "type";
    private final int MUSIC_NOTIFICATION_VALUE_PLAY = 30001;
    private final int MUSIC_NOTIFICATION_VALUE_NEXT = 30002;
    private final int MUSIC_NOTIFICATION_VALUE_CLOSE = 30003;
    private Intent play = null, next = null, close = null;
    private PendingIntent musicPendIntent = null;

    private MusicNotification() {
        // Initialize control intents
        play = new Intent();
        play.setAction(MUSIC_NOTIFICATION_ACTION_PLAY);
    }

    public static MusicNotification getMusicNotification() {
        if (notifyInstance == null) {
            notifyInstance = new MusicNotification();
        }
        return notifyInstance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Default Channel";
            String description = "Channel for default notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("default", name, importance);
            channel.setDescription(description);

            notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
    }

    public void onCreateMusicNotifi() {
        if (remoteViews == null) {
            createNotificationChannel();
        }

        play.putExtra(MUSIC_NOTIFICAION_INTENT_KEY, MUSIC_NOTIFICATION_VALUE_PLAY);
        PendingIntent pplay = PendingIntent.getBroadcast(context, REQUEST_CODE, play, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.notification_play_button, pplay);

        Intent intent = new Intent(context, NotificationActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(context, "default")
                .setContentIntent(pi)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContent(remoteViews)
                .build();

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    public void updateMusicNotification(MusicViewBean.MusicBean mm, boolean isPlay) {
        if (remoteViews == null) {
            createNotificationChannel();
        }

        remoteViews.setTextViewText(R.id.notification_title, (mm.getMusicname() != null ? mm.getMusicname() : "Unknown") + "");
        Glide.with(context)
                .asBitmap()
                .load(mm.getAlbum_img())
                .timeout(1500)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        remoteViews.setImageViewBitmap(R.id.notification_icon, resource);
                        onCreateMusicNotifi();
                    }
                });

        int playButtonResource = isPlay ? R.drawable.not_stop : R.drawable.not_start;
        remoteViews.setInt(R.id.notification_play_button, "setBackgroundResource", playButtonResource);
    }
}
