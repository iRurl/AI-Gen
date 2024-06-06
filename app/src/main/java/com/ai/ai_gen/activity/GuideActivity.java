package com.ai.ai_gen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ai.ai_gen.R;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ai.ai_gen.adapter.*;
import com.ai.ai_gen.bean.User;
import com.ai.ai_gen.utils.DBOpenHelper;

public class GuideActivity extends AppCompatActivity {

    private ViewPager vp;
    private DBOpenHelper mDBOpenHelper;
    private List<ImageView> imageViews;
    private int[] imgs= {R.mipmap.guide};
    private GuideAdapter adapter;
    ArrayList<User> loginuser;
    private static int SPLASH_DISPLAY_LENGHT= 2000;    //延迟2秒

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);//去掉标题
        setContentView(R.layout.activity_guide);
        getSupportActionBar().hide();
        vp= findViewById(R.id.guide_vp);
        //初始化图片
        initImgs();
        adapter= new GuideAdapter(imageViews);
        vp.setAdapter(adapter);
        mDBOpenHelper=new DBOpenHelper(this);
        loginuser=mDBOpenHelper.getAllLoggedInUsers();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(loginuser.size()==1){
                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);	//第二个参数即为执行完跳转后的Activity
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(GuideActivity.this, LoginActivity.class);	//第二个参数即为执行完跳转后的Activity
                    startActivity(intent);
                }
                GuideActivity.this.finish();   //关闭splashActivity，将其回收，否则按返回键会返回此界面
            }
        }, SPLASH_DISPLAY_LENGHT);

    }
    /**
     * 初始化图片
     */
    private void initImgs() {
        ViewPager.LayoutParams params= new ViewPager.LayoutParams();
        imageViews= new ArrayList<ImageView>();
        for (int i= 0; i< imgs.length; i++){
            ImageView imageView= new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setImageResource(imgs[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(imageView);
        }
    }


}

