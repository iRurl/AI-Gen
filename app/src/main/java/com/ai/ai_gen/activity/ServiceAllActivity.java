package com.ai.ai_gen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.adapter.RecycleServiceAdapter;
import com.ai.ai_gen.bean.ServiceBean;
import com.ai.ai_gen.utils.APIConfig;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceAllActivity extends AppCompatActivity {

    private RecyclerView service_recyclerview;
    private RecycleServiceAdapter adapter;
    private List<ServiceBean.RowsBean> rowsBeanList;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==0) {
                ServiceBean serviceBean = (ServiceBean) msg.obj;
                rowsBeanList = serviceBean.getRows();
                // 实例化adapter
                adapter= new RecycleServiceAdapter(ServiceAllActivity.this,rowsBeanList);
                // 设置布局管理器
                service_recyclerview.setLayoutManager(new GridLayoutManager(ServiceAllActivity.this,5));
                // 设置适配器
                service_recyclerview.setAdapter(adapter);
                // 设置item点击跳转事件 分别到对应的页面,在Intent中可以自己创建跳转的activity
                adapter.setItemClickListener(new RecycleServiceAdapter.MyItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String url = APIConfig.BASE_URL+"/"+rowsBeanList.get(position).getLink();
                        Intent intent = null;
                        Bundle bundle = new Bundle();
                        bundle.putString("title",rowsBeanList.get(position).getServiceName());
                        bundle.putString("url",url);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        getSupportActionBar().hide();
        getServiceData();
    }

    // 请求全部服务
    private void getServiceData() {
        // 创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        // 创建 Request对象
        Request request = new Request.Builder()
                .url(APIConfig.BASE_URL+"/service/service/list")
                .build();
        try {
            // 回调
            Call call = client.newCall(request);
            // 异步请求
            call.enqueue(new Callback() {
                @Override
                // 请求失败
                public void onFailure(Call call, IOException e) {
                    Log.i("onFailure",e.getMessage());
                }
                // 响应成功
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String result = response.body().string();
                        // runOnUiThread()用于更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                ServiceBean serviceBean = gson.fromJson(result,ServiceBean.class);
                                Message msg = new Message();
                                msg.what=0;
                                msg.obj=serviceBean;
                                handler.sendMessage(msg);
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}