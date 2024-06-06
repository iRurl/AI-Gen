package com.ai.ai_gen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.bean.User;
import com.ai.ai_gen.utils.DBOpenHelper;

import net.csdn.roundview.RoundButton;

public class SettingActivity extends AppCompatActivity {
    Context mContext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();
        ImageView iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RoundButton tv_out=findViewById(R.id.tv_out);
        tv_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBOpenHelper mDBOpenHelper=new DBOpenHelper(mContext);
                User user=mDBOpenHelper.getAllLoggedInUsers().get(0);
                mDBOpenHelper.updateLoginStatus(user.getPhonenum(),0);
                Intent intent = new Intent(mContext, LoginActivity.class);	//第二个参数即为执行完跳转后的Activity
                startActivity(intent);
                finish();
            }
        });
    }
}