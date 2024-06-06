package com.ai.ai_gen.fragment.recommend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ai.ai_gen.R;
import com.ai.ai_gen.activity.Item_Detail_type1Activity;
import com.ai.ai_gen.fragment.BaseFragment;

public class jieriFragment extends BaseFragment {

    private Context mContext;
    private View mView;
    private  int type;
    private LinearLayout jieri;



    public jieriFragment(Context context, int type){
        this.mContext = context;
        this.type = type;
    }
    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.tj_jieri,null);
        jieri=view.findViewById(R.id.jieri);

        initdata();
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
//            Toast.makeText(mContext, type+"", Toast.LENGTH_SHORT).show();
        }
    }

    public void initdata() {
        jieri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Item_Detail_type1Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("imageurl", null);
                bundle.putString("content", "童年只有一次，成长不会再来，很荣幸参与你的童年见证。");
                bundle.putString("desc",  "儿童节");
                bundle.putString("zannum", "1.2w");
                bundle.putInt("resid",  R.mipmap.etj);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }

}
