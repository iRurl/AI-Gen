package com.ai.ai_gen.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.text.TextUtils;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 只支持音频播放
 * 结合MediaPlayer生命周期
 */
public class MediaPlayerManager {

    public static final String TAG = "MediaPlayerManager";

    private static final int STATE_UNINITIALIZED = 0;
    private static final int STATE_IDLE = 1;
    private static final int STATE_INITIALIZED = 2;
    private static final int STATE_PREPARING = 3;
    private static final int STATE_PREPARED = 4;
    private static final int STATE_STARTED = 5;
    private static final int STATE_PAUSED = 6;
    private static final int STATE_COMPLETED = 7;
    private static final int STATE_STOPED = 8;
    private static final int STATE_ERROR = 9;
    private static final int STATE_END = 10;


    private static volatile MediaPlayerManager sMediaPlayerManager;

    private MediaPlayer mMediaPlayer;

    private volatile int mCurrentSate = STATE_UNINITIALIZED;

    private MediaPlayer.OnCompletionListener mCompletionListener;
    private MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private MediaPlayer.OnSeekCompleteListener mSeekCompleteListener;
    private MediaPlayer.OnPreparedListener mPreparedListener;
    private MediaPlayer.OnErrorListener mOnErrorListener;
    private ScheduledExecutorService mScheduledExecutorService;
    private SendPlayProgress mSendPlayProgress = new SendPlayProgress();
    private OnMediaProgressUpdateListener mOnMediaProgressUpdateListener;
    private Handler mMainHandler = new Handler();
    private MainRunnable mMainRunnable = new MainRunnable();
    private String mPlayingUrl;

    /**
     * 初始化进度回调线程池
     */
    private void initProgressSchedule() {
        if (mOnMediaProgressUpdateListener == null) {
            return;
        }
        if (mScheduledExecutorService == null || mScheduledExecutorService.isShutdown()) {
            mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            mScheduledExecutorService.scheduleAtFixedRate(mSendPlayProgress, 0, 2, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 销毁进度回调线程池
     */
    private void destroyProgressSchedule() {
        if (mScheduledExecutorService != null && !mScheduledExecutorService.isShutdown()) {
            mScheduledExecutorService.shutdownNow();
        }
    }

    public void setOnMediaProgressUpdateListener(OnMediaProgressUpdateListener onMediaProgressUpdateListener) {
        mOnMediaProgressUpdateListener = onMediaProgressUpdateListener;
    }

    public void setOnErrorListener(MediaPlayer.OnErrorListener onErrorListener) {
        mOnErrorListener = onErrorListener;
    }

    /**
     * 进度更新回调
     */
    interface OnMediaProgressUpdateListener {
        /**
         * 进度更新
         *
         * @param progress 进度
         */
        void onProgress(int progress);
    }

    /**
     * 播放器单例
     *
     * @return 播放器
     */
    public static MediaPlayerManager getInstance() {
        if (sMediaPlayerManager == null) {
            synchronized (MediaPlayerManager.class) {
                if (sMediaPlayerManager == null) {
                    sMediaPlayerManager = new MediaPlayerManager();
                }
            }
        }
        return sMediaPlayerManager;
    }

    private MediaPlayerManager() {
        init();
    }

    /**
     * 初始化播放器
     */
    private synchronized void init() {
        release();
        mMediaPlayer = new MediaPlayer();
        mCurrentSate = STATE_IDLE;
        mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListenerInternal);
        mMediaPlayer.setOnCompletionListener(mOnCompletionListenerInternal);
        mMediaPlayer.setOnErrorListener(mOnErrorListenerInternal);
        mMediaPlayer.setOnInfoListener(mOnInfoListenerInternal);
        mMediaPlayer.setOnPreparedListener(mOnPreparedListenerInternal);
        mMediaPlayer.setOnSeekCompleteListener(mOnSeekCompleteListenerInternal);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public String getMusicpath(){
        return mPlayingUrl;
    }

    /**
     * 每次播放音频都会创建一个新的播放器,并且释放上一个播放器
     *
     * @param url 地址 支持本地地址或者网络地址
     */
    public synchronized void playOnCreate(String url) {
        if (mCurrentSate == STATE_PAUSED) {
            start();
            return;
        }
        init();
        setDataSource(url);
        initProgressSchedule();
    }

    /**
     * 每次使用同一个播放器播放音频
     *
     * @param url 地址 支持本地地址或者网络地址
     */
    public synchronized void playOnOne(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        //已经结束或者已经有正在播放的
        if (mCurrentSate == STATE_STARTED) {
            return;
        }
        //未创建MediaPlayer对象或者已经释放了
        if (mCurrentSate == STATE_UNINITIALIZED || mCurrentSate == STATE_END) {
            init();
        }
        //手动停止的情况下
        if (mCurrentSate == STATE_STOPED) {
            //如果此时断网然后重新播放同一个地址
            if (TextUtils.equals(mPlayingUrl, url)) {
                //使用异步准备方法可以继续播放，否则同个设置数据源的方式无法使用本地缓冲好的音频内容继续播放
                mMediaPlayer.prepareAsync();
                mCurrentSate = STATE_PREPARING;
                return;
            }
            reset();
        }
        //暂停播放
        if (mCurrentSate == STATE_PAUSED) {
            if (TextUtils.equals(mPlayingUrl, url)) {
                start();
                return;
            }
            reset();
        }
        //设置数据源
        mPlayingUrl = url;
        setDataSource(url);
        initProgressSchedule();
    }

    private MediaPlayer.OnSeekCompleteListener mOnSeekCompleteListenerInternal = new MediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(MediaPlayer mp) {
            if (mSeekCompleteListener != null) {
                mSeekCompleteListener.onSeekComplete(mp);
            }
        }
    };

    private MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListenerInternal = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if (mOnBufferingUpdateListener != null) {
                mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
            }
        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListenerInternal = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mCurrentSate = STATE_COMPLETED;
            stop();
       if (mCompletionListener != null) {
                mCompletionListener.onCompletion(mp);
            }
        }
    };

    /**
     * 返回true，在出错的情况下将不会在继续调用{@link MediaPlayer.OnCompletionListener#onCompletion(MediaPlayer)}
     */
    private MediaPlayer.OnErrorListener mOnErrorListenerInternal = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            mCurrentSate = STATE_ERROR;
            getInstance().release();

            if (mOnErrorListener != null) {
                mOnErrorListener.onError(mp, what, extra);
            }
            return true;
        }
    };

    private MediaPlayer.OnInfoListener mOnInfoListenerInternal = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {

            return false;
        }
    };

    private MediaPlayer.OnPreparedListener mOnPreparedListenerInternal = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mCurrentSate = STATE_PREPARED;
            getInstance().start();

            if (mPreparedListener != null) {
                mPreparedListener.onPrepared(mp);
            }
        }
    };

    /**
     * 播放器实例
     */
    public synchronized void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mCurrentSate = STATE_END;
            mMediaPlayer = null;
        }
        mPlayingUrl = "";
        destroyProgressSchedule();
    }

    /**
     * 设置完成回调
     *
     * @param listener 完成回调
     */
    public void setCompleteListener(MediaPlayer.OnCompletionListener listener) {
        mCompletionListener = listener;
    }

    /**
     * 设置缓存回调
     *
     * @param listener 缓存回调
     */
    public void setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener listener) {
        mOnBufferingUpdateListener = listener;
    }

    /**
     * 设置滑动条
     *
     * @param listener 滑条监听
     */
    public void setSeekCompleteListener(MediaPlayer.OnSeekCompleteListener listener) {
        mSeekCompleteListener = listener;
    }

    /**
     * 设置播放准备完成
     *
     * @param preparedListener 播放准备完成
     */
    public void setPreparedListener(MediaPlayer.OnPreparedListener preparedListener) {
        this.mPreparedListener = preparedListener;
    }

    /**
     * 为了重用一个处于Error状态的MediaPlayer对象，可以调用reset()方法来把这个对象恢复成Idle状态。
     */
    private synchronized void reset() {
        try {
            destroyProgressSchedule();
            mMediaPlayer.reset();
            mCurrentSate = STATE_IDLE;
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 设置数据源，可以说本地文件路径，也可以是在线音频流url；
     *
     * @param url 地址
     */
    private synchronized void setDataSource(String url) {
        try {
            if (mCurrentSate != STATE_IDLE) {
                return;
            }
            mMediaPlayer.setDataSource(url);
//            Class<MediaPlayer> clazz = MediaPlayer.class;
//            Method method = clazz.getDeclaredMethod("setDataSource", String.class, Map.class);
//            method.invoke(mMediaPlayer, path, new HashMap<String, String>());
            mCurrentSate = STATE_INITIALIZED;
            mMediaPlayer.prepareAsync();
            mCurrentSate = STATE_PREPARING;
        } catch (IllegalStateException e) {
            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * 异步准备方法只有在 {@link #STATE_INITIALIZED} {@link #STATE_STOPED}状态下可以调用
     *
     * @return true 可以调用 false 不可以调用
     */
    private boolean isCanPrepareAsync() {
        return mCurrentSate == STATE_INITIALIZED || mCurrentSate == STATE_STOPED;
    }

    /**
     * 如果之前pasue的话，从pasue处播放；否则，从bengin处播放
     *
     * @return 开始
     */
    public synchronized boolean start() {
        try {
            if (!isCanStart()) {
                return false;
            }
            if (null != mMediaPlayer) {
                mMediaPlayer.start();
            }
            mCurrentSate = STATE_STARTED;
            return true;
        } catch (IllegalStateException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    /**
     * 启动方法只有在
     * {@link #STATE_PREPARED}
     * {@link #STATE_PAUSED}
     * {@link #STATE_PAUSED}
     * 状态下可以调用
     *
     * @return true 可以调用 false 不可以调用
     */
    private boolean isCanStart() {
        return mCurrentSate == STATE_PREPARED || mCurrentSate == STATE_PAUSED || mCurrentSate == STATE_COMPLETED;
    }

    /**
     * 暂停方法只有在 {@link #STATE_STARTED} 状态下可以调用
     *
     * @return true 可以调用 false 不可以调用
     */
    private boolean isCanPause() {
        return mCurrentSate == STATE_STARTED;
    }

    /**
     * 暂停
     */
    public synchronized void pause() {
        if (!isCanPause()) {
            return;
        }
        mMediaPlayer.pause();
        mCurrentSate = STATE_PAUSED;
    }

    /**
     * 检查相关方法在生命周期内是否可以调用
     *
     * @return true 可以调用 false 不可以调用
     */
    private boolean isCanCall() {
        if (mCurrentSate == STATE_IDLE || mCurrentSate == STATE_END || mCurrentSate == STATE_PREPARING) {
            return false;
        }
        return true;
    }

    /**
     * 停止
     *
     * @return 停止
     */
    public synchronized boolean stop() {
        try {
            if (!isCanStop()) {
                return false;
            }
            mMediaPlayer.stop();
            mCurrentSate = STATE_STOPED;
            return true;
        } catch (IllegalStateException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        destroyProgressSchedule();
        return false;
    }

    /**
     * 暂停方法只需要在
     * {@link #STATE_PAUSED}
     * {@link #STATE_STARTED}
     * {@link #STATE_PREPARED}状态下调用
     * {@link #STATE_COMPLETED}状态下调用
     *
     * @return true 可以调用 false 不可以调用
     */
    private boolean isCanStop() {
        return mCurrentSate == STATE_PAUSED || mCurrentSate == STATE_STARTED || mCurrentSate == STATE_PREPARED
                || mCurrentSate == STATE_COMPLETED;
    }

    /**
     * 获取当前状态
     *
     * @return 当前状态
     */
    public synchronized int getCurrentState() {
        return mCurrentSate;
    }

    /**
     * 是否暂停状态
     *
     * @return 是否暂停
     */
    public synchronized boolean isPauseState() {
        return mCurrentSate == STATE_PAUSED;
    }

    /**
     * 是否已经开启的状态
     *
     * @return 是否开启
     */
    public synchronized boolean isStartedState() {
        return mCurrentSate == STATE_PREPARED;
    }

    /**
     * 设置左右声音音量
     *
     * @param left  左音道
     * @param right 右音道
     */
    public synchronized void setVolume(float left, float right) {
        if (!isCanSet()) {
            return;
        }
        mMediaPlayer.setVolume(left, right);
    }

    /**
     * 获取当前进度，单位是毫秒
     *
     * @return 进度
     */
    private synchronized int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * 获取时长，单位是毫秒；如果不可用，则返回-1；
     *
     * @return 时长
     */
    private synchronized int getDuration() {
        return mMediaPlayer.getDuration();
    }

    /**
     * 是否循环
     *
     * @param looping 是否循环
     */
    public synchronized void setLooping(boolean looping) {
        if (!isCanSet()) {
            return;
        }
        mMediaPlayer.setLooping(looping);
    }

    /**
     * 只有在 {@link #STATE_PREPARED}状态下才可以修改播放器属性
     *
     * @return true 可以调用 false 不可以调用
     */
    private synchronized boolean isCanSet() {
        return mCurrentSate == STATE_PREPARED;
    }


    /**
     * 是否循环
     *
     * @return 是否循环
     */
    public synchronized boolean isLooping() {
        return mMediaPlayer.isLooping();
    }

    /**
     * isPlaying()方法可以被调用来测试某个MediaPlayer对象是否在Started状态。
     *
     * @return 是否在播放
     */
    public synchronized boolean isPlaying() {
        if (mCurrentSate == STATE_END) {
            return false;
        }
        try {
            return mMediaPlayer.isPlaying();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    /**
     * 拉动进度
     *
     * @param position 进度
     */
    public synchronized void seekTo(int position) {
        if (!isCanSeekTo()) {
            return;
        }
        mMediaPlayer.seekTo(position);
    }

    /**
     * 该方法只需要在
     * {@link #STATE_PAUSED}
     * {@link #STATE_COMPLETED}
     * {@link #STATE_PREPARED}状态下调用
     *
     * @return true 可以调用 false 不可以调用
     */
    private boolean isCanSeekTo() {
        return mCurrentSate == STATE_PREPARED
                || mCurrentSate == STATE_PAUSED
                || mCurrentSate == STATE_COMPLETED
                || mCurrentSate == STATE_STARTED;
    }

    class MainRunnable implements Runnable {
        public int progress;

        @Override
        public void run() {
            if (mOnMediaProgressUpdateListener != null) {
                mOnMediaProgressUpdateListener.onProgress(progress);
            }
        }
    }

    private class SendPlayProgress implements Runnable {
        @Override
        public void run() {
            if (!isCanCall()) {
                return;
            }
            if (mMediaPlayer == null || !isPlaying()) {
                return;
            }
            mMainRunnable.progress = (int) ((getCurrentPosition() * 1.0f / getDuration() * 1.0f) * 100 + 0.9f);
            mMainHandler.post(mMainRunnable);

        }
    }

}
