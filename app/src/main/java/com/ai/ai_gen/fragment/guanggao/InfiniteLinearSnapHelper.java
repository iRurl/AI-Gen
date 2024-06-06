package com.ai.ai_gen.fragment.guanggao;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class InfiniteLinearSnapHelper extends LinearSnapHelper {

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof ScaleCenterItemLayoutManager)) {
            return super.findSnapView(layoutManager);
        }

        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }

        View closestChild = null;
        int center = layoutManager.getWidth() / 2;
        int absClosest = Integer.MAX_VALUE;

        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            if (child == null) {
                continue;
            }
            int childCenter = (layoutManager.getDecoratedLeft(child) + layoutManager.getDecoratedRight(child)) / 2;
            int absDistance = Math.abs(childCenter - center);

            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }
        return closestChild;
    }
}
