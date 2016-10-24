package com.example.jit.plain.Utils;

/**
 * Created by Max on 2016/6/30.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by QZ on 2016/4/12.
 * 兼容ScrollView的ListView
 */
public class ScrollListView extends ListView {
    public ScrollListView(Context context) {
        super(context);
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}