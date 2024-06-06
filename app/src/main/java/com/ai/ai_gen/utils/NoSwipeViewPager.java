package com.ai.ai_gen.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

public class NoSwipeViewPager extends ViewPager {
    public NoSwipeViewPager(Context context) {
        super(context);
    }

    public NoSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 返回 false 禁止滑动事件
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // 返回 false 禁止拦截事件
        return false;
    }
}
