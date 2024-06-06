package com.ai.ai_gen.fragment.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.ai.ai_gen.R;
import com.ai.ai_gen.adapter.HomeViewPagerAdapter;
import com.ai.ai_gen.adapter.util_AiGenAdapter;
import com.ai.ai_gen.fragment.BaseFragment;
import com.ai.ai_gen.fragment.recommend.guanggaoFragment;
import com.ai.ai_gen.fragment.recommend.haowenFragment;
import com.ai.ai_gen.fragment.recommend.jieriFragment;
import com.ai.ai_gen.fragment.recommend.meishiFragment;
import com.ai.ai_gen.fragment.recommend.musicFragment;
import com.ai.ai_gen.fragment.recommend.pinpaiFragment;
import com.ai.ai_gen.fragment.recommend.wenanFragment;
import com.ai.ai_gen.utils.CustomViewpager;

import java.util.ArrayList;
import java.util.List;

public class AiGenFragment extends BaseFragment {
    private static final String TAG = AiGenFragment.class.getSimpleName();
    private final String[] gj_List = new String[]{"我的收藏", "热门", "工作", "角色", "学习", "生活", "娱乐","情感"};
    private final int[] gj_idList = new int[]{0, 1, 2, 3, 4, 5, 6,7};
    private final int mTitleMargin =2;
    RecyclerView mRecyclerView;
    List<Integer> images;
    /**
     * 工具所需变量
     */
    private ArrayList<Integer> moveToList;
    private LinearLayout gj_titleLayout;
    private ArrayList<String> gj_titleList;            //标题文字集合
    private HomeViewPagerAdapter gj_Adapter;
    private ArrayList<Fragment> gj_fragmentsList;      //viewPager加载类
    private CustomViewpager gj_viewPager;
    private HorizontalScrollView gj_scrollView;
    private ArrayList<TextView> gj_textViewList;       //标题控件集合
    private int gj_currentPos;                         //现在显示的是第几页


    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.util_aigen, null);
        mRecyclerView = view.findViewById(R.id.util_recyclerview);

        gj_scrollView = view.findViewById(R.id.gj_scrollView);
        gj_titleLayout = view.findViewById(R.id.gj_titleLayout);
        gj_viewPager = view.findViewById(R.id.gj_viewPager);
        return view;
    }

    @Override

    public void initData() {
        super.initData();
        init_banner();
        init_gj_page();
    }

    public void init_banner() {
        images = new ArrayList<Integer>();
        images.add(R.mipmap.banner1);
        images.add(R.mipmap.banner2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        util_AiGenAdapter mAdapter = new util_AiGenAdapter(getContext(), images);
        mRecyclerView.setAdapter(mAdapter);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 初始化工具界面
     */
    private void init_gj_page() {
        gj_fragmentsList = new ArrayList<>();
        gj_titleList = new ArrayList<>();
        gj_textViewList = new ArrayList<>();
        moveToList = new ArrayList<>();
        gj_Adapter = new HomeViewPagerAdapter(getChildFragmentManager());

        gj_titleList.add(gj_List[0]);
        AimycollectionFragment colllectionfragment = new AimycollectionFragment(getActivity(), gj_idList[0]);
        gj_fragmentsList.add(colllectionfragment);
        add_gj_TitleLayout(gj_titleList.get(0), gj_idList[0]);

        for (int i = 1; i < gj_List.length; i++) {
            gj_titleList.add(gj_List[i]);
            AiutilsFragment fragment = new AiutilsFragment(getActivity(), gj_idList[i]);
            gj_fragmentsList.add(fragment);
            add_gj_TitleLayout(gj_titleList.get(i), gj_idList[i]);
        }
        // 如果不设置，可能第三个页面以后就显示不出来了，因为offset就是默认值1了
        gj_Adapter.setData(gj_fragmentsList);
        gj_viewPager.setAdapter(gj_Adapter);
        gj_viewPager.setOffscreenPageLimit(gj_List.length);
        gj_textViewList.get(0).setTextColor(Color.rgb(20, 178, 177));
        gj_textViewList.get(0).setTextSize(18);
        gj_textViewList.get(0).setTypeface(null, Typeface.BOLD);
        gj_currentPos = 0;
        gj_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                // 切换到当前页面，重置高度
                gj_viewPager.requestLayout();
                gj_textViewList.get(gj_currentPos).setTextColor(Color.rgb(94, 94, 94));
                gj_textViewList.get(gj_currentPos).setTextSize(14);
                gj_textViewList.get(gj_currentPos).setTypeface(null, Typeface.NORMAL);
                gj_textViewList.get(position).setTextColor(Color.rgb(20, 178, 177));
                gj_textViewList.get(position).setTextSize(18);
                gj_textViewList.get(position).setTypeface(null, Typeface.BOLD);
                gj_currentPos = position;
                gj_scrollView.scrollTo(moveToList.get(position), 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    private void add_gj_TitleLayout(String title, int position) {
        final TextView textView = (TextView) getLayoutInflater().inflate(R.layout.tj_horizontal_title, null);
        textView.setText(title);
        textView.setTag(position);
        textView.setOnClickListener(new posOnClickListener());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 40;
        params.rightMargin = dip2px(getActivity(), mTitleMargin) * 8;
        gj_titleLayout.addView(textView, params);
        gj_textViewList.add(textView);
        int width;
        if (position == 0) {
            width = 0;
            moveToList.add(width);
        } else {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            gj_textViewList.get(position - 1).measure(w, h);
            width = gj_textViewList.get(position - 1).getMeasuredWidth() + mTitleMargin * 8;
            moveToList.add(width + moveToList.get(moveToList.size() - 1));
        }
    }

    class posOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if ((int) view.getTag() == gj_currentPos) {
                return;
            }
            gj_textViewList.get(gj_currentPos).setTextColor(Color.rgb(0, 0, 0));
            gj_currentPos = (int) view.getTag();
            gj_textViewList.get(gj_currentPos).setTextColor(Color.rgb(255, 0, 0));
            gj_viewPager.setCurrentItem(gj_currentPos);
            gj_viewPager.requestLayout();
        }
    }

}

