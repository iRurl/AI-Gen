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
import com.ai.ai_gen.activity.Item_Detail_type2Activity;
import com.ai.ai_gen.fragment.BaseFragment;

public class haowenFragment extends BaseFragment {

    private Context mContext;
    private View mView;
    private  int type;
    TextView jr_content;

LinearLayout jrhw;
    public haowenFragment(Context context, int type){
        this.mContext = context;
        this.type = type;
    }
    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.tj_jinrihaowen,null);
        jrhw=view.findViewById(R.id.jrhw);
        jr_content=view.findViewById(R.id.jr_content);
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
        jrhw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Item_Detail_type2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", "梁龙英");
                bundle.putString("content", (String) jr_content.getText());
                bundle.putString("desc",  "再遇我的老师");
                bundle.putString("zannum", "2.6w");
                bundle.putInt("resid",  R.mipmap.ic_launcher);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }


}
