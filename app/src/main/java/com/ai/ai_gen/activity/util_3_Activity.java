package com.ai.ai_gen.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ai.ai_gen.R;

public class util_3_Activity extends AppCompatActivity {
    String title;
    LinearLayout ll_col;
    ImageView ico_img;
    Button util_btn;

    int img_id;
    String btn_info;
    String visiable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util3);
        ll_col=findViewById(R.id.ll_col);
        ico_img=findViewById(R.id.ico_img);
        util_btn=findViewById(R.id.util_btn);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            visiable= bundle.getString("visable");
            title= bundle.getString("title");
            img_id= bundle.getInt("img_id");
            btn_info= bundle.getString("btn_info");
        }
        getSupportActionBar().setTitle(title);
        if(TextUtils.equals(visiable,"no")){
            ll_col.setVisibility(View.GONE);
        } else if (TextUtils.equals(visiable,"yes")) {
            ll_col.setVisibility(View.VISIBLE);
        }
        ico_img.setImageResource(img_id);
        util_btn.setText(btn_info);

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