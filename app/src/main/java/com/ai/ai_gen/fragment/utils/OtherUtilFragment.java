package com.ai.ai_gen.fragment.utils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.activity.AiUtil_comActivity;
import com.ai.ai_gen.activity.util_1_Activity;
import com.ai.ai_gen.activity.util_2_Activity;
import com.ai.ai_gen.activity.util_3_Activity;
import com.ai.ai_gen.bean.UtilViewBean;
import com.ai.ai_gen.fragment.BaseFragment;

public class OtherUtilFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = AiGenFragment.class.getSimpleName();

    CardView util_spqsy;
    CardView util_tqwz;
    CardView util_wjccx;
    CardView util_ypzwz;
    CardView util_xyg;
    CardView util_tpzwz;
    CardView util_sgscq;
    CardView util_tpqsy;
    CardView util_spzwz;
    CardView util_xxs;
    CardView util_xzy;
    CardView util_xdl;



    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.util_otherutil, null);
        util_spqsy=view.findViewById(R.id.util_spqsy);
        util_tqwz=view.findViewById(R.id.util_tqwz);
        util_wjccx=view.findViewById(R.id.util_wjccx);
        util_ypzwz=view.findViewById(R.id.util_ypzwz);
        util_xyg=view.findViewById(R.id.util_xyg);
        util_tpzwz=view.findViewById(R.id.util_tpzwz);
        util_sgscq=view.findViewById(R.id.util_sgscq);
        util_tpqsy=view.findViewById(R.id.util_tpqsy);
        util_spzwz=view.findViewById(R.id.util_spzwz);
        util_xxs=view.findViewById(R.id.util_xxs);
        util_xzy=view.findViewById(R.id.util_xzy);
        util_xdl=view.findViewById(R.id.util_xdl);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        util_spqsy.setOnClickListener(this::onClick);
        util_tqwz.setOnClickListener(this::onClick);
        util_wjccx.setOnClickListener(this::onClick);
        util_ypzwz.setOnClickListener(this::onClick);
        util_xyg.setOnClickListener(this::onClick);
        util_tpzwz.setOnClickListener(this::onClick);
        util_sgscq.setOnClickListener(this::onClick);
        util_tpqsy.setOnClickListener(this::onClick);
        util_spzwz.setOnClickListener(this::onClick);
        util_xxs.setOnClickListener(this::onClick);
        util_xzy.setOnClickListener(this::onClick);
        util_xdl.setOnClickListener(this::onClick);


    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.util_spqsy){
            Intent intent =new Intent(getContext(), util_1_Activity.class);
            Bundle bundle=new Bundle();
            bundle.putString("visable","no");
            bundle.putString("title","视频去水印");
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        }else if (v.getId()==R.id.util_tqwz){
            Intent intent =new Intent(getContext(), util_1_Activity.class);
            Bundle bundle=new Bundle();
            bundle.putString("visable","yes");
            bundle.putString("title","链接转文字");
            intent.putExtras(bundle);
            getContext().startActivity(intent);

        }else if (v.getId()==R.id.util_wjccx){
            Intent intent =new Intent(getContext(), util_2_Activity.class);
            Bundle bundle=new Bundle();
            bundle.putString("title","违禁词查询");
            intent.putExtras(bundle);
            getContext().startActivity(intent);

        }else if (v.getId()==R.id.util_ypzwz){
            Intent intent =new Intent(getContext(), util_3_Activity.class);
            Bundle bundle=new Bundle();
            bundle.putString("visable","no");
            bundle.putString("title","音频转文字");
            bundle.putInt("img_id",R.mipmap.icon_util_audio);
            bundle.putString("btn_info","点击选择音频");
            intent.putExtras(bundle);
            getContext().startActivity(intent);

        }else if (v.getId()==R.id.util_xyg){

        }else if (v.getId()==R.id.util_tpzwz){
            Intent intent =new Intent(getContext(), util_3_Activity.class);
            Bundle bundle=new Bundle();
            bundle.putString("visable","no");
            bundle.putString("title","图片转文字");
            bundle.putInt("img_id",R.mipmap.icon_util_photo);
            bundle.putString("btn_info","点击选择图片");
            intent.putExtras(bundle);
            getContext().startActivity(intent);

        }else if (v.getId()==R.id.util_sgscq){

        }else if (v.getId()==R.id.util_tpqsy){
            Intent intent =new Intent(getContext(), util_1_Activity.class);
            Bundle bundle=new Bundle();
            bundle.putString("visable","no");
            bundle.putString("title","图片去水印");
            intent.putExtras(bundle);
            getContext().startActivity(intent);

        }else if (v.getId()==R.id.util_spzwz){
            Intent intent =new Intent(getContext(), util_3_Activity.class);
            Bundle bundle=new Bundle();
            bundle.putString("visable","yes");
            bundle.putString("title","视频转文字");
            bundle.putInt("img_id",R.mipmap.icon_util_video);
            bundle.putString("btn_info","点击选择视频");
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        }
        else if (v.getId()==R.id.util_xxs){
            Intent intent =new Intent(getContext(), AiUtil_comActivity.class);
            UtilViewBean mList=new UtilViewBean();
            UtilViewBean.RowsBean rowsBean=new UtilViewBean.RowsBean("写小说","",false,"请告诉我您的小说主题");
            mList.rows.add(rowsBean);
            intent.putExtra("utilViewBean", mList);
            intent.putExtra("position", 0);
            getContext().startActivity(intent);
        }
        else if (v.getId()==R.id.util_xzy){
            Intent intent =new Intent(getContext(), AiUtil_comActivity.class);
            UtilViewBean mList=new UtilViewBean();
            UtilViewBean.RowsBean rowsBean=new UtilViewBean.RowsBean("写摘要","",false,"请告诉我您需要摘要的文章");
            mList.rows.add(rowsBean);
            intent.putExtra("utilViewBean", mList);
            intent.putExtra("position", 0);
            getContext().startActivity(intent);
        }
        else if (v.getId()==R.id.util_xdl){
            Intent intent =new Intent(getContext(), AiUtil_comActivity.class);
            UtilViewBean mList=new UtilViewBean();
            UtilViewBean.RowsBean rowsBean=new UtilViewBean.RowsBean("写对联","",false,"请告诉我您的对联主题");
            mList.rows.add(rowsBean);
            intent.putExtra("utilViewBean", mList);
            intent.putExtra("position", 0);
            getContext().startActivity(intent);
        }
    }
}

