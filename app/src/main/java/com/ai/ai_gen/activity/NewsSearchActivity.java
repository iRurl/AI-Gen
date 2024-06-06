package com.ai.ai_gen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ai.ai_gen.R;
import com.ai.ai_gen.bean.ServiceviewBean;
import com.ai.ai_gen.utils.APIConfig;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsSearchActivity extends AppCompatActivity {

    private TextView tv_new;
    private ListView listView;
    private List<ServiceviewBean.RowsBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_search);
        tv_new = findViewById(R.id.tv_new);
        listView = findViewById(R.id.listview_search);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        final String serach_txt = intent.getStringExtra("search");
        System.out.println(serach_txt);

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");

        Request request = new Request.Builder()
                .url(APIConfig.BASE_URL+"/press/press/list")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("","请求失败："+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int position = 0;
                String result = response.body().string();
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    ServiceviewBean list = (ServiceviewBean) gson.fromJson(result, ServiceviewBean.class).getRows();
                    int code = list.getCode();
//                    if (code==200){
//                        String title = list.getRows().get(position).getPressCategory();
//                        if (title == serach_txt) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ServiceViewAdapter serviceViewAdapter = new ServiceViewAdapter(list, (List<Fragment>) getApplicationContext());
//                                    listView.setAdapter(serviceViewAdapter);
//                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                        @Override
//                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                            Toast.makeText(getApplicationContext(),"点击："+position,Toast.LENGTH_LONG).show();
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                    }
                }
            }
        });



    }

}