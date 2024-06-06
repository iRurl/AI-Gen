package com.ai.ai_gen.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.adapter.AiUtilComAdapter;
import com.ai.ai_gen.bean.ConversionBean;
import com.ai.ai_gen.bean.UtilViewBean;

public class AiUtil_comActivity extends AppCompatActivity {
    Context mContect = this;
    RecyclerView recyclerView;

    AiUtilComAdapter aiUtilComAdapter;
    ProgressBar video_loading;
    int position;

    ConversionBean conversionBean;
    UtilViewBean utilViewBean;
    TextView tv_title;
    EditText et;
    TextView tv_confirm;
    int con_num = 1;
    LinearLayout ll_loading;
    ImageView btn_home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_util);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tv_title = findViewById(R.id.tv_title);
        et = findViewById(R.id.et);
        tv_confirm = findViewById(R.id.tv_confirm);
        video_loading = findViewById(R.id.video_loading);
        ll_loading = findViewById(R.id.ll_loading);
        btn_home = findViewById(R.id.btn_home);
        // 接收传递的数据
        Intent intent = getIntent();
        utilViewBean = intent.getParcelableExtra("utilViewBean");
        position = intent.getIntExtra("position", 0);
        if (utilViewBean != null) {
            tv_title.setText(utilViewBean.getRows().get(position).getName());
        }
        initData();
        initet();
    }

    private void initet() {
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取输入内容
                String ask = et.getText().toString();
                ConversionBean.RowsBean con_info = new ConversionBean.RowsBean(ask, 1);
                conversionBean.rows.add(con_info);
                con_num += 1;
                conversionBean.setTotal(con_num);

                // 更新 RecyclerView 适配器
                aiUtilComAdapter = new AiUtilComAdapter(AiUtil_comActivity.this, conversionBean);
                recyclerView.setAdapter(aiUtilComAdapter);

                // 清空输入框
                et.setText("");
                // 显示加载动画
                ll_loading.setVisibility(View.VISIBLE);
                // 创建 Handler 实现 1 秒延迟
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 隐藏加载动画
                        ll_loading.setVisibility(View.GONE);
                    }
                }, 1000); // 1 秒延迟

            }
        });
    }

    private void initData() {
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        conversionBean = new ConversionBean();
        conversionBean.setTotal(con_num);
        ConversionBean.RowsBean con_info = new ConversionBean.RowsBean(utilViewBean.getRows().get(position).getContent(), 0);
        conversionBean.rows.add(con_info);
        aiUtilComAdapter = new AiUtilComAdapter(mContect, conversionBean);
        recyclerView.setAdapter(aiUtilComAdapter);

    }

}
