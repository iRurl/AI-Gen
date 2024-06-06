package com.ai.ai_gen.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.adapter.CommentAdapter;
import com.ai.ai_gen.adapter.MusicAdapter;
import com.ai.ai_gen.bean.CommentBean;
import com.ai.ai_gen.bean.MusicViewBean;
import com.ai.ai_gen.utils.APIConfig;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommunityActivity extends AppCompatActivity {
    Context mContext=this;
    Activity mActivity=this;
    RoundedImageView com_img;
    TextView com_name;
    TextView com_content;
    TextView com_commentnum;
    Button like_num;
    Button com_collection;
    ImageView back;
    String name;
    String content;
    String img;
    String like;
    String collection;
    int commentnum;
    String commentid;

    CommentBean commentList;
    CommentAdapter commentAdapter;
    RecyclerView recyclerView;
    FrameLayout comment_empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        getSupportActionBar().hide();
        com_img = findViewById(R.id.com_img);
        com_name = findViewById(R.id.com_name);
        com_content = findViewById(R.id.com_content);
        like_num = findViewById(R.id.like_num);
        com_collection = findViewById(R.id.com_collection);
        com_commentnum=findViewById(R.id.com_commentnum);
        back = findViewById(R.id.back);
        recyclerView=findViewById(R.id.comment_recycler);
        comment_empty=findViewById(R.id.comment_empty);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            content = bundle.getString("content");
            img = bundle.getString("img");
            like = bundle.getString("like");
            collection = bundle.getString("collection");
            commentnum = bundle.getInt("commentnum");
            commentid = bundle.getString("commentid");
        }
        initData();
    }

    private void initData() {
        String comment_num="评论（"+commentnum+"）";
        Glide.with(this).load(img).into(com_img);
        com_name.setText(name);
        com_content.setText(content);
        like_num.setText(like);
        com_collection.setText(collection);
        com_commentnum.setText(comment_num);
        if(commentnum!=0){
            comment_empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            getHttpData();
        }else {
            recyclerView.setVisibility(View.GONE);
            comment_empty.setVisibility(View.VISIBLE);
        }

        like_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.equals(String.valueOf(like_num.getTag()),"1")){
                    Drawable sel = getResources().getDrawable(R.mipmap.detail_zan_sel);
                    sel.setBounds(0, 0, sel.getMinimumWidth(),sel.getMinimumHeight());
                    like_num.setCompoundDrawables(sel,null,null,null);
                    like_num.setTag("1");
                }else {
                    Drawable sel = getResources().getDrawable(R.mipmap.detail_zan_nor);
                    sel.setBounds(0, 0, sel.getMinimumWidth(),sel.getMinimumHeight());
                    like_num.setCompoundDrawables(sel,null,null,null);
                    like_num.setTag("0");
                }

            }
        });
        com_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.equals(String.valueOf(com_collection.getTag()),"1")){

                    Drawable sel = getResources().getDrawable(R.mipmap.detail_collect_sel);
                    sel.setBounds(0, 0, sel.getMinimumWidth(),sel.getMinimumHeight());
                    com_collection.setCompoundDrawables(sel,null,null,null);
                    com_collection.setTag("1");
                }else {
                    Drawable sel = getResources().getDrawable(R.mipmap.detail_collect_nor);
                    sel.setBounds(0, 0, sel.getMinimumWidth(),sel.getMinimumHeight());
                    com_collection.setCompoundDrawables(sel,null,null,null);
                    com_collection.setTag("0");
                }
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getHttpData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(APIConfig.BASE_URL + "/community/comment?id="+commentid)
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
                    commentList = gson.fromJson(result, CommentBean.class);
                    commentAdapter = new CommentAdapter(mContext, commentList);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(commentAdapter);
                        }
                    });
                }
            }
        });
    }
}