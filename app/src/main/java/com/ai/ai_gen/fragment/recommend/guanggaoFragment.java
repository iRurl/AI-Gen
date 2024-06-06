package com.ai.ai_gen.fragment.recommend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ai.ai_gen.R;
import com.ai.ai_gen.activity.Item_Detail_type3Activity;
import com.ai.ai_gen.fragment.BaseFragment;

public class guanggaoFragment extends BaseFragment {

    private Context mContext;
    private View mView;
    private  int type;
    LinearLayout ll1;
    LinearLayout ll2;



    public guanggaoFragment(Context context, int type){
        this.mContext = context;
        this.type = type;
    }
    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.tj_guanggao,null);
        ll1=view.findViewById(R.id.ll_gg1);
        ll2=view.findViewById(R.id.ll_gg2);
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
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Item_Detail_type3Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", "有多...就有多...");
                bundle.putString("content", "由多少杯空，就有多少心空。");
                bundle.putString("desc",  "江小白");
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Item_Detail_type3Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("content", "这世界上有多少人，就有多少位子。");
                bundle.putString("desc",  "肯德基");
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

}
