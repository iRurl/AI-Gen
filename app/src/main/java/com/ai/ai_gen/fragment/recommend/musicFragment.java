package com.ai.ai_gen.fragment.recommend;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.adapter.MusicAdapter;
import com.ai.ai_gen.bean.MusicViewBean;
import com.ai.ai_gen.fragment.BaseFragment;
import com.ai.ai_gen.manager.MusicNotification;
import com.ai.ai_gen.manager.MusicService;
import com.ai.ai_gen.utils.APIConfig;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class musicFragment extends BaseFragment implements View.OnClickListener {
    private Context mContext;
    private View mView;
    private RecyclerView mRecyclerView;
    MusicAdapter musicAdapter;
    MusicViewBean musicList;
    MusicNotification musicNotification;
    private final String MUSIC_INTENT_KEY = "musics";
    private final int MUSIC_INTENT_FLAG = 20001;

    private String currentPath = null; // 跟踪当前播放的路径
    int type;

    public musicFragment(Context context, int type) {
        this.mContext = context;
        this.type = type;
    }

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.tj_list, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.tj_center_reView);
//        musicNotification =MusicNotification.getMusicNotification();
//        musicNotification.setContext(mContext);
//        musicNotification.createNotificationChannel();
        getHttpData();
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }
    public Activity getmActivity(){
        return getActivity();
    }


    @SuppressLint("WrongConstant")
    public void getMusicModelList(MusicViewBean models) {
        // 初始化 Service : 开启MUSIC服务
        Intent intent = new Intent(this.getActivity(), MusicService.class);
        intent.putExtra(MUSIC_INTENT_KEY, models);
        intent.addFlags(MUSIC_INTENT_FLAG);
        mContext.startService(intent);
    }

    public void getHttpData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(APIConfig.BASE_URL + "/home/tuijian/music")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Gson gson = new Gson();
                    musicList = gson.fromJson(result, MusicViewBean.class);
                    getMusicModelList(musicList);
                    musicAdapter = new MusicAdapter(mContext, musicList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.setAdapter(musicAdapter);
                        }
                    });
                    musicAdapter.setItemClickListener(
                            new MusicAdapter.MyItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position ) {
                                }
                            }
                    );
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}