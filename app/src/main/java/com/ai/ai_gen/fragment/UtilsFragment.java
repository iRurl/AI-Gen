package com.ai.ai_gen.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ai.ai_gen.R;
import com.ai.ai_gen.adapter.HomeViewPagerAdapter;
import com.ai.ai_gen.adapter.UtilTabAdapter;
import com.ai.ai_gen.fragment.recommend.guanggaoFragment;
import com.ai.ai_gen.fragment.recommend.haowenFragment;
import com.ai.ai_gen.fragment.recommend.jieriFragment;
import com.ai.ai_gen.fragment.recommend.meishiFragment;
import com.ai.ai_gen.fragment.recommend.musicFragment;
import com.ai.ai_gen.fragment.recommend.pinpaiFragment;
import com.ai.ai_gen.fragment.recommend.wenanFragment;
import com.ai.ai_gen.fragment.utils.AiGenFragment;
import com.ai.ai_gen.fragment.utils.OtherUtilFragment;
import com.ai.ai_gen.utils.CustomViewpager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class UtilsFragment extends BaseFragment {

    private UtilTabAdapter utilTabAdapter;
    private ViewPager util_viewpager;
    private TabLayout tabLayout;
    private ArrayList<TextView> textViewList;       //标题控件集合


    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_utils,null);
        tabLayout=view.findViewById(R.id.util_tab_layout);
        util_viewpager = view.findViewById(R.id.util_view_pager);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        initUtilsData();
    }



    private void initUtilsData() {
        // 新闻分类标题导航栏
        String[] title = {"AI创作工具", "其他工具"};
        //创建装载Fragment的列表
        List<Fragment> fragmentlist;
        // 初始化列表，并把创建的6个Fragment页面添加到列表中
        fragmentlist = new ArrayList<>();
        fragmentlist.add(new AiGenFragment());
        fragmentlist.add(new OtherUtilFragment());

        textViewList=new ArrayList<TextView>();

        utilTabAdapter = new UtilTabAdapter(getChildFragmentManager(), fragmentlist, title);
        // ViewPager与Adapter绑定
        util_viewpager.setAdapter(utilTabAdapter);
        // TabLayout与ViewPager绑定
        tabLayout.setupWithViewPager(util_viewpager);

        // 设置自定义视图
        for (int i = 0; i < title.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                TextView tabTextView = new TextView(getActivity());
                tabTextView.setText(title[i]);
                tabTextView.setGravity(Gravity.CENTER);
                tabTextView.setTextAppearance(getActivity(), R.style.NormalTabTextAppearance);
                tab.setCustomView(tabTextView);
                textViewList.add(tabTextView);
            }
        }
        textViewList.get(0).setTextAppearance(getActivity(), R.style.SelectedTabTextAppearance);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View tabView = tab.getCustomView();
                if (tabView != null) {
                    TextView tabTextView = (TextView) tabView;
                    tabTextView.setTextAppearance(getActivity(), R.style.SelectedTabTextAppearance);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View tabView = tab.getCustomView();
                if (tabView != null) {
                    TextView tabTextView = (TextView) tabView;
                    tabTextView.setTextAppearance(getActivity(), R.style.NormalTabTextAppearance);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 可以留空
            }
        });
    }



}

