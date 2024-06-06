package com.ai.ai_gen.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ai_gen.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import cn.jzvd.JzvdStd;

public class VideoPalyerActivity extends AppCompatActivity {
    JzvdStd jzvdStd;
    LinearLayout ll;
    String videourl;
    String title;
    TextView video_title;
    int layout;
    int image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_palyer);
//        getSupportActionBar().hide();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        ll = findViewById(R.id.videolayout);
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        videourl = bundle.getString("url");
        title = bundle.getString("title");
        image = bundle.getInt("image");
        layout = bundle.getInt("layout");
        jzvdStd = (JzvdStd) findViewById(R.id.jz_video);
        video_title=findViewById(R.id.video_title);
        initData();
    }

    private void initData() {
        video_title.setText(title);
        ll.addView(View.inflate(VideoPalyerActivity.this, layout, null));

        jzvdStd.setUp(videourl
                , title);
        jzvdStd.posterImageView.setImageResource(image);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}