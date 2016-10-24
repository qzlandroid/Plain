package com.example.jit.plain.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.jit.plain.R;

import java.sql.Time;
import java.util.Timer;

/**
 * 上拉刷新ListView
 *
 * @author xiejinxiong
 */
public class LoadMoreListView extends ListView implements AbsListView.OnScrollListener {
    private View footer;

    private int totalItem;
    private int lastItem;

    private int temp = 0 ;   //记录上次的数目

    private SwipeRefreshLayout mSwipeView;
    private AbsListView.OnScrollListener mOnScrollListener;

    private boolean isLoading;

    private OnLoadMore onLoadMore;

    private OnSwipeIsValid isValid;

    private LayoutInflater inflater;

    public LoadMoreListView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @SuppressLint("InflateParams")
    private void init(Context context) {
        inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.footer, null, true);
        footer.setVisibility(View.GONE);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastItem = firstVisibleItem + visibleItemCount;
        this.totalItem = totalItemCount;


    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (lastItem == totalItem && scrollState == SCROLL_STATE_IDLE
                && totalItem > 4 && totalItem != temp) {
            temp = totalItem;
            Log.v("isLoading", "yes");
            if (!isLoading) {
                isLoading = true;
                footer.setVisibility(View.VISIBLE);
                onLoadMore.loadMore();
            }
        }
        //判断ListView是否滑动到第一个Item的顶部
        if (isValid != null&&this.getChildCount() > 0 && this.getFirstVisiblePosition() == 0
                && this.getChildAt(0).getTop() >= this.getPaddingTop()) {
            //解决滑动冲突，当滑动到第一个item，下拉刷新才起作用
            isValid.setSwipeEnabledTrue();
        } else {
            isValid.setSwipeEnabledFalse();
        }

    }

    public void setLoadMoreListen(OnLoadMore onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    public void setSwipeIsVaildListenr(OnSwipeIsValid isValid) {
        this.isValid = isValid;
    }

    /**
     * 加载完成调用此方法
     */
    public void onLoadComplete() {
        Handler handler = new Handler();

        handler.postDelayed(LOAD_DATA, 500);

        isLoading = false;

    }

    public interface OnLoadMore {
        public void loadMore();
    }
    private Runnable LOAD_DATA = new Runnable() {
        @Override
        public void run() {
            //在这里数据内容加载到Fragment上
            footer.setVisibility(View.GONE);
        }
    };


    public interface OnSwipeIsValid{
        public void setSwipeEnabledTrue();
        public void setSwipeEnabledFalse();
    }
}
