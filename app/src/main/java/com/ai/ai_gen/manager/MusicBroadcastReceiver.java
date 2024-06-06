package com.ai.ai_gen.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.ai.ai_gen.R;
import com.ai.ai_gen.adapter.MusicAdapter;
import com.ai.ai_gen.bean.MusicViewBean;

public class MusicBroadcastReceiver extends BroadcastReceiver {
    private static final String MUSIC_SERVICE_TO_ACTIVITY_CODE = "mpcode";
    private static final String MUSIC_SERVICE_TO_ACTIVITY_ISPLAY = "isplay";
    private static final String MUSIC_SERVICE_TO_ACTIVITY_MODEL = "model";

    private Boolean isPlaying = false;
    private MusicViewBean.MusicBean currentMusic;
    private final MusicAdapter.MusicViewHolder musicViewHolder;

    public MusicBroadcastReceiver(MusicAdapter.MusicViewHolder musicViewHolder) {
        this.musicViewHolder = musicViewHolder;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int code = intent.getIntExtra(MUSIC_SERVICE_TO_ACTIVITY_CODE, 0);
        if (code > 0) {
            handleMusicServiceIntent(intent);
        }
    }

    private void handleMusicServiceIntent(Intent intent) {
        isPlaying = intent.getBooleanExtra(MUSIC_SERVICE_TO_ACTIVITY_ISPLAY, true);
        currentMusic = intent.getParcelableExtra(MUSIC_SERVICE_TO_ACTIVITY_MODEL);

        if (currentMusic != null && TextUtils.equals(currentMusic.getMusicname(), musicViewHolder.music_title.getText())) {
            updatePlayButton(isPlaying);
        } else {
            resetPlayButton();
        }
    }

    private void updatePlayButton(boolean isPlaying) {
        if (isPlaying) {
            musicViewHolder.start_button.setImageResource(R.drawable.stop_music);
        } else {
            musicViewHolder.start_button.setImageResource(R.drawable.start_music);
        }
    }

    private void resetPlayButton() {
        musicViewHolder.start_button.setImageResource(R.drawable.start_music);
    }

    public Boolean getisplaying() {
        return this.isPlaying;
    }
}
