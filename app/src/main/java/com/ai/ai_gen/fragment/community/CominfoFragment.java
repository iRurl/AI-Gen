package com.ai.ai_gen.fragment.community;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.adapter.AiUtilAdapter;
import com.ai.ai_gen.adapter.CommunityAdapter;
import com.ai.ai_gen.bean.CommunityBean;
import com.ai.ai_gen.bean.CommunityBean;
import com.ai.ai_gen.fragment.BaseFragment;
import com.ai.ai_gen.utils.APIConfig;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CominfoFragment extends BaseFragment {
    private Context mContext;
    private View mView;
    private RecyclerView mRecyclerView;
    CommunityAdapter communityAdapter;
    CommunityBean utilList;
    int type;


    public CominfoFragment(Context context , int type) {
        this.mContext = context;
        this.type=type;
    }

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.util_list, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.util_center_reView);
        getHttpData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }
    public void getHttpData(){
        OkHttpClient client = new OkHttpClient();
        Request request  = new Request.Builder()
                .url(APIConfig.BASE_URL+"/community/list")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Gson gson = new Gson();
                    utilList =  gson.fromJson(result,CommunityBean.class);
                    communityAdapter = new CommunityAdapter(mContext,utilList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.setAdapter(communityAdapter);
                        }
                    });
                    communityAdapter.setItemClickListener(new CommunityAdapter.MyItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                        }
                    });
                }
            }
        });
    }

}