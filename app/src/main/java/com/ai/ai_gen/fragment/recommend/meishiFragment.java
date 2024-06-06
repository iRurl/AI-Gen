package com.ai.ai_gen.fragment.recommend;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.adapter.MeishiAdapter;
import com.ai.ai_gen.bean.ServiceviewBean;
import com.ai.ai_gen.fragment.BaseFragment;
import com.ai.ai_gen.utils.APIConfig;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class meishiFragment extends BaseFragment {
    private Context mContext;
    private View mView;
    private RecyclerView mRecyclerView;
    MeishiAdapter meihsiAdapter;
    ServiceviewBean meishiList;
    int type;


    public meishiFragment(Context context ,int type) {
        this.mContext = context;
        this.type=type;
    }

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.tj_list, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.tj_center_reView);
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
                .url(APIConfig.BASE_URL+"/service/meishi")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Gson gson = new Gson();
                    meishiList =  gson.fromJson(result, ServiceviewBean.class);
                    meihsiAdapter = new MeishiAdapter(mContext,meishiList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.setAdapter(meihsiAdapter);
                        }
                    });
                    meihsiAdapter.setItemClickListener(new
                            MeishiAdapter.MyItemClickListener(){
                                @Override
                                public void onItemClick(View view, int position) {

                                }
                            });
                }
            }
        });
    }

}