package com.ai.ai_gen.fragment.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.ai.ai_gen.R;
import com.ai.ai_gen.fragment.BaseFragment;

public class AimycollectionFragment extends BaseFragment {

    private Context mContext;
    private View mView;
    private  int type;


    public AimycollectionFragment(Context context, int type){
        this.mContext = context;
        this.type = type;
    }
    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.util_ai_mycollection,null);
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
