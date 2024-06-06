package com.ai.ai_gen.fragment.guanggao;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ai.ai_gen.R;
import com.ai.ai_gen.bean.GgviewBean;
import com.ai.ai_gen.fragment.BaseFragment;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

public class GgTitleFragment extends BaseFragment {
    private Context mContext;
    GgviewBean ggList;
    String url;
    RoundedImageView imageView;

    public GgTitleFragment(Context context , String url) {
        this.mContext = context;
        this.url=url;
    }

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.gg_horizontal_title, null);
        imageView=view.findViewById(R.id.gg_image);
        setview();
        return view;
    }

    private void setview() {
        Glide.with(this)
                .load(url)
                .timeout(1500)
                .into(imageView);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}