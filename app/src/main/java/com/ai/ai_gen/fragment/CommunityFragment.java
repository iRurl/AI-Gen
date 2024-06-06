package com.ai.ai_gen.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.ai.ai_gen.R;
import com.ai.ai_gen.adapter.HomeViewPagerAdapter;
import com.ai.ai_gen.fragment.community.CominfoFragment;
import com.ai.ai_gen.fragment.utils.AiGenFragment;
import com.ai.ai_gen.fragment.utils.AimycollectionFragment;
import com.ai.ai_gen.fragment.utils.AiutilsFragment;
import com.ai.ai_gen.utils.CustomViewpager;

import java.util.ArrayList;

public class CommunityFragment extends BaseFragment {

    private final String[] com_List = new String[]{"最新", "最热"};
    private final int[] com_idList = new int[]{0, 1};
    private final int mTitleMargin =2;
    private ArrayList<Integer> moveToList;
    private LinearLayout com_titleLayout;
    private ArrayList<String> com_titleList;            //标题文字集合
    private HomeViewPagerAdapter com_Adapter;
    private ArrayList<Fragment> com_fragmentsList;      //viewPager加载类
    private CustomViewpager com_viewPager;
    private HorizontalScrollView com_scrollView;
    private ArrayList<TextView> com_textViewList;       //标题控件集合
    private int com_currentPos;                         //现在显示的是第几页

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_community,null);
        com_scrollView = view.findViewById(R.id.com_scrollView);
        com_titleLayout = view.findViewById(R.id.com_titleLayout);
        com_viewPager = view.findViewById(R.id.com_viewPager);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        init_com_page();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 初始化工具界面
     */
    private void init_com_page() {
        com_fragmentsList = new ArrayList<>();
        com_titleList = new ArrayList<>();
        com_textViewList = new ArrayList<>();
        moveToList = new ArrayList<>();
        com_Adapter = new HomeViewPagerAdapter(getChildFragmentManager());
        com_titleList.add(com_List[0]);
        CominfoFragment comfragment = new CominfoFragment(getActivity(), com_idList[0]);
        com_fragmentsList.add(comfragment);
        add_com_TitleLayout(com_titleList.get(0), com_idList[0]);
            com_titleList.add(com_List[1]);
        CominfoFragment fragment = new CominfoFragment(getActivity(), com_idList[1]);
            com_fragmentsList.add(fragment);
            add_com_TitleLayout(com_titleList.get(1), com_idList[1]);
        // 如果不设置，可能第三个页面以后就显示不出来了，因为offset就是默认值1了
        com_Adapter.setData(com_fragmentsList);
        com_viewPager.setAdapter(com_Adapter);
        com_viewPager.setOffscreenPageLimit(com_List.length);
        com_textViewList.get(0).setTextColor(Color.rgb(20, 178, 177));
        com_textViewList.get(0).setTextSize(18);
        com_textViewList.get(0).setTypeface(null, Typeface.BOLD);

        com_currentPos = 0;
        com_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                // 切换到当前页面，重置高度
                com_viewPager.requestLayout();
                com_textViewList.get(com_currentPos).setTextColor(Color.rgb(94, 94, 94));
                com_textViewList.get(com_currentPos).setTypeface(null, Typeface.NORMAL);
                com_textViewList.get(com_currentPos).setTextSize(14);
                com_textViewList.get(position).setTextColor(Color.rgb(20, 178, 177));
                com_textViewList.get(position).setTextSize(18);
                com_textViewList.get(position).setTypeface(null, Typeface.BOLD);
                com_currentPos = position;
                com_scrollView.scrollTo(moveToList.get(position), 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    private void add_com_TitleLayout(String title, int position) {
        final TextView textView = (TextView) getLayoutInflater().inflate(R.layout.tj_horizontal_title, null);
        textView.setText(title);
        textView.setTag(position);
        textView.setOnClickListener(new posOnClickListener());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 40;
        params.rightMargin = dip2px(getActivity(), mTitleMargin) * 8;
        com_titleLayout.addView(textView, params);
        com_textViewList.add(textView);
        int width;
        if (position == 0) {
            width = 0;
            moveToList.add(width);
        } else {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            com_textViewList.get(position - 1).measure(w, h);
            width = com_textViewList.get(position - 1).getMeasuredWidth() + mTitleMargin * 8;
            moveToList.add(width + moveToList.get(moveToList.size() - 1));
        }
    }

    class posOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if ((int) view.getTag() == com_currentPos) {
                return;
            }
            com_textViewList.get(com_currentPos).setTextColor(Color.rgb(0, 0, 0));
            com_currentPos = (int) view.getTag();
            com_textViewList.get(com_currentPos).setTextColor(Color.rgb(255, 0, 0));
            com_viewPager.setCurrentItem(com_currentPos);
            com_viewPager.requestLayout();
        }
    }

}

