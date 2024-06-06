package com.ai.ai_gen.fragment.guanggao;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.bean.GgviewBean;
import com.ai.ai_gen.fragment.BaseFragment;
import com.bumptech.glide.Glide;

public class GgContentFragment extends BaseFragment {
    private Context mContext;
    GgviewBean.RowsBean ggInfo;
    TextView gg_title;
    TextView gg_content;
    TextView gg_next;

    public GgContentFragment(Context context ,GgviewBean.RowsBean ggInfo) {
        this.mContext = context;
        this.ggInfo=ggInfo;
    }


    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.gg_info, null);
        gg_title=view.findViewById(R.id.gg_title);
        gg_content=view.findViewById(R.id.gg_content);
        gg_next=view.findViewById(R.id.gg_next);
        setview();
        return view;
    }

    private void setview() {
        gg_title.setText(ggInfo.getTitle());
        gg_content.setText(ggInfo.getContent());
        gg_next.setText(ggInfo.getNext());

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}