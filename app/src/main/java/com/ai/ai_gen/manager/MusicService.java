package com.ai.ai_gen.manager;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.Toast;

import com.ai.ai_gen.bean.MusicViewBean;
import com.ai.ai_gen.utils.MediaPlayerManager;

public class MusicService extends Service implements OnPreparedListener,
        OnCompletionListener, OnErrorListener {
    /**
     * MusicService 音乐播放控制 ： 随着应用的启动而启动 基本步揍 ： 1.应用启动 ： 就启动 2.状态栏显示 ：
     * notification 3.注册 : bordcastReceiver 4.请求第1首歌 ： 更新状态栏 5.实现上一曲，下一曲，播放，暂停控制
     */
    // 常量
    private final String MUSIC_INTENT_KEY = "musics";
    private final int MUSIC_INTENT_FLAG = 20001;
    private final int MAIN_MUSIC_INTENT_FLAG = 20017;
    // 音乐列表
    private MusicViewBean musics = null;
    // 通知栏
    private MusicNotification musicNotifi = null;
    private MusicViewBean.MusicBean mm = null;

    // MediaPlay
    private MediaPlayerManager mp = null;
    private boolean isfirst = true;

    // Music广播接收
    private MusicBroadCast musicBroadCast = null;
    // MainActivity 来的 Action
    private final String MAIN_ACTIVIY_ACTION = "mainActivity.To.MusicService";
    // 来自通知栏的Action
    private final String MUSIC_NOTIFICATION_ACTION_PLAY = "musicnotificaion.To.PLAY";
    private final String MUSIC_NOTIFICATION_ACTION_NEXT = "musicnotificaion.To.NEXT";
    private final String MUSIC_NOTIFICATION_ACTION_CLOSE = "musicnotificaion.To.CLOSE";
    // MusicService 来的 Action
    private final String MUSIC_ACTIVITY_SERVICE_ACTION = "activity.to.musicservice";
    private final int MUSIC_ACTIVITY_SERVICE_REQUEST = 40001;

    // 给MusicActivity 的 Action
    private final String MUSIC_SERVICE_RECEIVER_ACTION = "service.to.musicactivity";
    private Intent mActivityIntent = null;
    private final String MUSIC_SERVICE_TO_ACTIVITY_MODEL = "model";
    private final String MUSIC_SERVICE_TO_ACTIVITY_ISPLAY = "isplay";
    // 响应码 : 41001 没数据 , 41002 : 有数据
    private final String MUSIC_SERVICE_TOACTIVITY_CODE = "mpcode";

    // Intent keys
    private final String MAIN_MUSIC_INTENT_KEY = "mIntent";

    @Override
    public void onCreate() {
        // 初始化MusicActivity 的 Intent ,给 MusicActivity 发送广播 ,修改音乐播放界面
        mActivityIntent = new Intent();
        mActivityIntent.setAction(MUSIC_SERVICE_RECEIVER_ACTION);
        // 初始化通知栏
        musicNotifi = MusicNotification.getMusicNotification();
        musicNotifi.setContext(getApplicationContext());
        musicNotifi.createNotificationChannel();
        // 初始化MediaPlay : 设置监听事件
        mp = MediaPlayerManager.getInstance();
        mp.setCompleteListener(this);
        mp.setOnErrorListener(this);
        // 注册广播
        musicBroadCast = new MusicBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MAIN_ACTIVIY_ACTION);
        filter.addAction(MUSIC_ACTIVITY_SERVICE_ACTION);
        filter.addAction(MUSIC_NOTIFICATION_ACTION_PLAY);
        filter.addAction(MUSIC_NOTIFICATION_ACTION_CLOSE);
        registerReceiver(musicBroadCast, filter);

        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            musics = intent
                    .getParcelableExtra(MUSIC_INTENT_KEY);
        } catch (Exception e) {

        }
        // showToast("1." + musics.get(1).getSongname());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
            musics = null;
        }
        // 取消注册的广播
        unregisterReceiver(musicBroadCast);
    }

    // //Music Util//
    // Toast
    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    // 音乐播放
    public void play(String path) {
        if (mp.isPlaying()) {
            //如果再次获得的url与播放的相同则暂停
            if (TextUtils.equals(mp.getMusicpath(), path)) {
                mp.pause();
                musicNotifi.updateMusicNotification(mm, true);
            } else {//否则播放新音乐
                mp.stop();
                mp.playOnOne(path);
                musicNotifi.updateMusicNotification(mm, false);
            }
        } else {
            mp.playOnOne(path);
            musicNotifi.updateMusicNotification(mm, false);
        }
        sendModelToMusicActivity();
    }

    // //Music MediaPlayListener
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // 出错的时候
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // 改变通知栏
        musicNotifi.updateMusicNotification(mm, true);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
    }
    // 其他工具方法//

    /**
     * 发送Model给MusicActivity
     */
    private void sendModelToMusicActivity() {
        if (mm != null) {
            if(isfirst){
                isfirst=false;
                mActivityIntent.putExtra(MUSIC_SERVICE_TOACTIVITY_CODE, 41002);
                mActivityIntent.putExtra(MUSIC_SERVICE_TO_ACTIVITY_ISPLAY,
                        true);
                mActivityIntent.putExtra(MUSIC_SERVICE_TO_ACTIVITY_MODEL,
                        (Parcelable) mm);
            }else {
                mActivityIntent.putExtra(MUSIC_SERVICE_TOACTIVITY_CODE, 41002);
                mActivityIntent.putExtra(MUSIC_SERVICE_TO_ACTIVITY_ISPLAY,
                        mp.isPlaying());
                mActivityIntent.putExtra(MUSIC_SERVICE_TO_ACTIVITY_MODEL,
                        (Parcelable) mm);
            }
        } else {
        }
        sendBroadcast(mActivityIntent);
    }

    // //Music BroadCastReceiver

    // 接收广播
    private class MusicBroadCast extends BroadcastReceiver {
        private int flag = 0, position = -1, kzhi = 0, musictype = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            // 2.MainActivity 控制
            flag = intent.getFlags();
            mainToService(intent);

            // 3.MusicNotification控制
            kzhi = intent.getIntExtra("type", -1);
            if (kzhi > 0) {
                musicNotificationService(kzhi);
            }
        }
        /**
         * musicNotification 来的控制
         */
        private void musicNotificationService(int k) {
            switch (k) {
                case 30001:
                    // 播放
                    play(mm.getSong_url());
                    break;
                case 30003:
                    break;
            }
        }
        /**
         * MainActivity来的数据
         *
         * @param intent
         */
        private void mainToService(Intent intent) {
            if (MAIN_MUSIC_INTENT_FLAG == flag) {
                // 来自MainActivity 的操作
                position = intent.getIntExtra(MAIN_MUSIC_INTENT_KEY, -1);
                // showToast("3.来自MainActivity 问候 : " + position);
                if (position > -1) {
                    musicNotifi.onCreateMusicNotifi();
                    // 播放
                    if (musics != null) {
                        mm = musics.getRows().get(position);
                    } else {
                        showToast("MUSICS IS NULL");
                    }
                    if (mm != null) {
                        /**
                         * 1.播放音乐 2.更新状态栏 3.如果进度条运行的话，通知改变
                         */
                        play(mm.getSong_url());

                    } else {
//						showToast("5.musics 数据去哪里了！");
                    }
                } else {
//					showToast("6.这怎么可能发生呢？！");
                }
            }else {
//				showToast("不是MainActivity 来的数据");
            }
        }
    }
}