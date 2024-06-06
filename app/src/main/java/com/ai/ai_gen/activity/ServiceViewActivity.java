package com.ai.ai_gen.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ai.ai_gen.R;
import com.ai.ai_gen.adapter.ServiceViewAdapter;
import com.ai.ai_gen.bean.ServiceviewBean;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceViewActivity extends AppCompatActivity {

    String url;
    String title;
    Context mcontext = this;
    private ServiceviewBean serviceviewBean = new ServiceviewBean();
    private ServiceViewAdapter serviceViewAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        setContentView(R.layout.activity_service);
        initServiceData();
    }

    private void initServiceData() {
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        title = bundle.getString("title");
        listView = findViewById(R.id.service_recyclerView);
        getHttpData();
    }

    public void getHttpData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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
                    serviceviewBean = gson.fromJson(result, ServiceviewBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            serviceViewAdapter = new ServiceViewAdapter(ServiceViewActivity.this, serviceviewBean);
                            listView.setAdapter(serviceViewAdapter);
                        }
                    });
                }
            }
        });
    }
    // 处理ActionBar返回按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
