package com.ai.ai_gen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.adapter.WenanViewAdapter;
import com.ai.ai_gen.bean.WenanListViewBean;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import net.csdn.roundview.RoundImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class wahj_listActivity extends AppCompatActivity {
    Context mContext=this;
    WenanListViewBean wenanListViewBean=new WenanListViewBean();
    private WenanViewAdapter wenanViewAdapter;
    private ListView listView;

    RoundedImageView wahj_img;
    TextView wahj_title;
    String url;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wahj_list);
        getSupportActionBar().hide();
        wahj_img=findViewById(R.id.wahj_img);
        wahj_title=findViewById(R.id.wahj_title);
        initWenanData();
    }

    private void initWenanData() {
        Bundle bundle = getIntent().getExtras();

        url = bundle.getString("url");
        listView = findViewById(R.id.wahj_list);
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
                    wenanListViewBean = gson.fromJson(result, WenanListViewBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            wahj_title.setText(wenanListViewBean.getTitle());
                            Glide.with(mContext)
                                    .load(wenanListViewBean.getImgurl())
                                    .timeout(1500)
                                    .into(wahj_img);
                            wenanViewAdapter = new WenanViewAdapter(wahj_listActivity.this, wenanListViewBean);
                            listView.setAdapter(wenanViewAdapter);
                        }
                    });
                }
            }
        });
    }
}