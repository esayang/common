package com.sczy.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FixListView extends ListView {
    private int mShowItemCount = 5;
    public FixListView(Context context) {
        super(context);
    }

    public FixListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getShowItemCount() {
        return mShowItemCount;
    }

    public void setShowItemCount(int mShowItemCount) {
        this.mShowItemCount = mShowItemCount;
    }

    public  void setListViewHeightBasedOnChildren() {
        ListAdapter listAdapter = getAdapter();
        if (listAdapter == null || listAdapter.getCount()<=0) {
            return;
        }
        int totalHeight = 0;
        int count = listAdapter.getCount() >=mShowItemCount ? mShowItemCount : listAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, this);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = totalHeight + (getDividerHeight() * count);
        setLayoutParams(params);
        requestLayout();
    }
}