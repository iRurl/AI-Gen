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
import com.ai.ai_gen.activity.wahj_listActivity;
import com.ai.ai_gen.fragment.BaseFragment;
import com.ai.ai_gen.utils.APIConfig;

public class wenanFragment extends BaseFragment {

    private Context mContext;
    private View mView;
    private  int type;
    LinearLayout wahj1;
    TextView wahj2;
    TextView wahj3;
    TextView wahj4;


    public wenanFragment(Context context, int type){
        this.mContext = context;
        this.type = type;
    }
    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.tj_wenanheji,null);
        wahj1=view.findViewById(R.id.wahj1);
        wahj2=view.findViewById(R.id.wahj2);
        wahj3=view.findViewById(R.id.wahj3);
        wahj4=view.findViewById(R.id.wahj4);
        initData();
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
//            Toast.makeText(mContext, type+"", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initData() {
        super.initData();
        wahj1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), wahj_listActivity.class);
                Bundle bundle = new Bundle();
                String url = APIConfig.BASE_URL + "/" + "wahj/1";
                bundle.putString("url", url);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        wahj2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), wahj_listActivity.class);
                Bundle bundle = new Bundle();
                String url = APIConfig.BASE_URL + "/" + "wahj/2";
                bundle.putString("url", url);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        wahj3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), wahj_listActivity.class);
                Bundle bundle = new Bundle();
                String url = APIConfig.BASE_URL + "/" + "wahj/3";
                bundle.putString("url", url);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        wahj4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), wahj_listActivity.class);
                Bundle bundle = new Bundle();
                String url = APIConfig.BASE_URL + "/" + "wahj/4";
                bundle.putString("url", url);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
