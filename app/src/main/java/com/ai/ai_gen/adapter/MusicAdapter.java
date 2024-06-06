package com.ai.ai_gen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.bean.MusicViewBean;
import com.ai.ai_gen.manager.MusicBroadcastReceiver;
import com.ai.ai_gen.manager.MusicNotification;
import com.ai.ai_gen.utils.MediaPlayerManager;
import com.bumptech.glide.Glide;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private Context context;
    protected MusicViewBean mList;
    private MyItemClickListener mItemClickListener;
    MediaPlayerManager mediaPlayerManager;
    private int lastSelectedPosition = -1;
    Intent musicIntent=null;
    private final String MAIN_ACTIVIY_ACTION = "mainActivity.To.MusicService";
    private final String MAIN_MUSIC_INTENT_KEY = "mIntent";
    private final String MUSIC_SERVICE_RECEIVER_ACTION = "service.to.musicactivity";
    private final int MUSIC_INTENT_FLAG = 20001;
    MusicBroadcastReceiver musicBroadcastReceiver;

    @SuppressLint("WrongConstant")
    public MusicAdapter(Context context, MusicViewBean musiclist) {
        super();
        this.context = context;
        this.mList = musiclist;
        musicIntent = new Intent();
        musicIntent.setAction(MAIN_ACTIVIY_ACTION);
        musicIntent.addFlags(MUSIC_INTENT_FLAG);
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_card, parent, false);
        MusicViewHolder myViewHolder = new MusicViewHolder(view, mItemClickListener);
        this.mediaPlayerManager = MediaPlayerManager.getInstance();
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final MusicViewBean e = mList;
        musicBroadcastReceiver=new MusicBroadcastReceiver(holder);
        IntentFilter filter = new IntentFilter(MUSIC_SERVICE_RECEIVER_ACTION);
        context.registerReceiver(musicBroadcastReceiver,filter);
        holder.start_button.setImageResource(lastSelectedPosition == position ? R.drawable.stop_music : R.drawable.start_music); // 修改这一行
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view, position);
                    musicIntent.putExtra(MAIN_MUSIC_INTENT_KEY, position);
                    context.sendBroadcast(musicIntent);
                    holder.start_button.setImageResource(R.drawable.stop_music);
                    notifyItemChanged(lastSelectedPosition); // 新增这一行
                    lastSelectedPosition = position; // 新增这一行
                }
            });
        }
        onBindData(holder, e, position);
    }

    protected void onBindData(MusicViewHolder holder, MusicViewBean musicModel, int positon) {
        holder.music_title.setTag(R.id.music_title, positon);
        String url = musicModel.getRows().get(positon).getAlbum_img();
        Glide.with(context)
                .load(url)
                .timeout(1500)
                .into(holder.album_img);
        Boolean isplaying= musicBroadcastReceiver.getisplaying();
        holder.music_title.setText(musicModel.getRows().get(positon).getMusicname());
        if (positon == (int) holder.music_title.getTag(R.id.music_title) && isplaying) {
            holder.start_button.setImageResource(R.drawable.stop_music);
        } else {
            holder.start_button.setImageResource(R.drawable.start_music);
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.getTotal();
    }

    public String getmusicpath(int position) {
        return mList.getRows().get(position).getSong_url();
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }

    //播放
    public void play(String path) {
        if (mediaPlayerManager.isPlaying()) {
            //如果再次获得的url与播放的相同则暂停
            if (TextUtils.equals(mediaPlayerManager.getMusicpath(), path)) {
                mediaPlayerManager.pause();
            } else {//否则播放新音乐
                mediaPlayerManager.stop();
                mediaPlayerManager.playOnOne(path);
            }
        } else {
            mediaPlayerManager.playOnOne(path);
        }
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView album_img;
        public ImageView start_button;
        public TextView music_title;
        MyItemClickListener myListener;

        public MusicViewHolder(@NonNull View itemView, MyItemClickListener mItemClickListener) {
            super(itemView);
            album_img = itemView.findViewById(R.id.music_img);
            start_button = itemView.findViewById(R.id.start_button);
            music_title = itemView.findViewById(R.id.music_title);
            myListener = mItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (myListener != null) {
                myListener.onItemClick(view, getPosition());
            }
        }
    }


}
