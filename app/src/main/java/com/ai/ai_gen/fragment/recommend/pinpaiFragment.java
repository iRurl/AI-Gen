package com.ai.ai_gen.fragment.recommend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.ai.ai_gen.R;
import com.ai.ai_gen.activity.ServiceViewActivity;
import com.ai.ai_gen.activity.VideoPalyerActivity;
import com.ai.ai_gen.fragment.BaseFragment;

public class pinpaiFragment extends BaseFragment {

    private Context mContext;
    private View mView;
    private  int type;
    private CardView video1;
    private CardView video2;
    private CardView video3;
    private CardView video4;


    public pinpaiFragment(Context context, int type){
        this.mContext = context;
        this.type = type;
    }
    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.tj_pinpai,null);
        video1=view.findViewById(R.id.video1);
        video2=view.findViewById(R.id.video2);
        video3=view.findViewById(R.id.video3);
        video4=view.findViewById(R.id.video4);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        video1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoPalyerActivity.class);
                Bundle bundle = new Bundle();
                String url="https://apollo-new.cdn.bcebos.com/auto-driving/carrobot/top.mp4";
                bundle.putString("url", url);
                bundle.putString("title", "百度汽车机器人|会听话的汽车长什么样子");
                bundle.putInt("image",R.mipmap.baiducar);
                bundle.putInt("layout",R.layout.video_info1);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        video2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoPalyerActivity.class);
                Bundle bundle = new Bundle();
                String url="https://file.digitaling.com/eImg/uvideos/2023/1228/1703771805493359.mp4";
                bundle.putString("url", url);
                bundle.putString("title", "明星种草”破圈的品牌营销，这么搞才能降本增效！");
                bundle.putInt("image",R.mipmap.mxzc);
                bundle.putInt("layout",R.layout.video_info2);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        video3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoPalyerActivity.class);
                Bundle bundle = new Bundle();
                String url="http://192.168.202.41/profile/szn.mp4";
                bundle.putString("url", url);
                bundle.putString("title", "始祖鸟雪季纪录片 《山雪之道》：山雪所向，便是自由");
                bundle.putInt("image",R.mipmap.sxzd);
                bundle.putInt("layout",R.layout.video_info3);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        video4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoPalyerActivity.class);
                Bundle bundle = new Bundle();
                String url="https://consumer.huawei.com/content/dam/huawei-cbg-site/cn/mkt/pdp/phones/mate60-pro-plus/video/id-popup.mp4";
                bundle.putString("url", url);
                bundle.putString("title", "“HUAWEI Mate60系列——可靠 从未如此Mate");
                bundle.putInt("image",R.mipmap.hw);
                bundle.putInt("layout",R.layout.video_info4);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
//            Toast.makeText(mContext, type+"", Toast.LENGTH_SHORT).show();
        }
    }

}
