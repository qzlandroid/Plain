package com.example.jit.plain.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jit.plain.Adapter.AdpFrageStatePager;
import com.example.jit.plain.Fragment.AffairsFragment;
import com.example.jit.plain.Fragment.NewFragment;
import com.example.jit.plain.Fragment.PolicyFragment;
import com.example.jit.plain.Fragment.ProjectFragment;
import com.example.jit.plain.R;
import com.example.jit.plain.UI.AnimManager;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    private Context context;
    private static final String TAG = "MainActivity";
    /** 主题色 **/
    protected int mainThemeColor;
    /** 黑色 **/
    protected int blackColor;

    /** 表示当前的游标在哪里,默认第一个 **/
    private int CursorIndex = 0;
    /** 屏幕宽度 **/
    private int screenW;
    // 本身
    @ViewInject(R.id.iv_cursor_activity_main)
    private ImageView cursor;
    @ViewInject(R.id.vp_activity_main)
    private ViewPager viewPager;
    @ViewInject(R.id.tv_vpitem1_activity_main)
    private TextView am_new;
    @ViewInject(R.id.tv_vpitem2_activity_main)
    private TextView am_policy;
    @ViewInject(R.id.tv_vpitem3_activity_main)
    private TextView am_affairs;
    @ViewInject(R.id.tv_vpitem4_activity_main)
    private TextView am_project;
    @ViewInject(R.id.ll_search_activity_main)
    private LinearLayout linearLayout_search;

    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private MyOnPageChangeListener myOnPageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
    }

    public void init() {
        context = this;
        initView();
        initData();
        initListener();
    }

    public void initView() {
        mainThemeColor = getResources().getColor(R.color.colorPrimary);
        blackColor = getResources().getColor(R.color.colorBlack);

        initCursorView();
    }

    public void initData() {
        initFragments();
    }
    /**
     * 初始化Fragments
     */
    private void initFragments() {
        fragmentList = new ArrayList<Fragment>();
        NewFragment newFragment = new NewFragment();
        AffairsFragment affairsFragment = new AffairsFragment();
        PolicyFragment policyFragment = new PolicyFragment();
        ProjectFragment projectFragment = new ProjectFragment();
        fragmentList.add(newFragment);
        fragmentList.add(policyFragment);
        fragmentList.add(projectFragment);
        fragmentList.add(affairsFragment);

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new AdpFrageStatePager(
                getSupportFragmentManager(), fragmentList, context));
    }

    public void initListener() {
        // 初始化子页面——详情的适配器
        myOnPageChangeListener = new MyOnPageChangeListener();
        viewPager.setOnPageChangeListener(myOnPageChangeListener);
    }

    /**
     * 初始化移动的光标
     */
    private void initCursorView() {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenW = dm.widthPixels;// 获取分辨率宽度
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) cursor
                .getLayoutParams();
        lp.width = screenW / 4;
        lp.height = DensityUtil.dip2px(2);
        cursor.setLayoutParams(lp);
        Matrix matrix = new Matrix();
        matrix.postScale(screenW / 4 , DensityUtil.dip2px(2));
        matrix.postTranslate(0, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置
        CursorIndex = 0;
        viewPager.setCurrentItem(CursorIndex);
    }


    @Event({ R.id.tv_vpitem1_activity_main, R.id.tv_vpitem2_activity_main,
            R.id.tv_vpitem3_activity_main, R.id.tv_vpitem4_activity_main })
    private void clickDetailItems(View view) {
        switch (view.getId()) {
            case R.id.tv_vpitem1_activity_main:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.tv_vpitem2_activity_main:
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.tv_vpitem3_activity_main:
                viewPager.setCurrentItem(2, true);
                break;
            case R.id.tv_vpitem4_activity_main:
                viewPager.setCurrentItem(3, true);
                break;
        }
    }

    @Event(R.id.ll_search_activity_main)
    private void clickSearch(View view){
        Intent _Intent = new Intent();
        _Intent.setClass(this, SearchActivity.class);
        startActivity(_Intent);
    }

    /**
     * 内部类：为ViewPager提供你内容切换监听
     */
    public class MyOnPageChangeListener implements OnPageChangeListener {

        Animation animation = null;

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            animation = AnimManager.getCursor(context, CursorIndex, arg0,
                    screenW, 4);
            CursorIndex = arg0;
            setCursorTextUI();
            cursor.startAnimation(animation);

        }
    }

    /**
     * 设置选择按钮的UI更新
     */
    public void setCursorTextUI() {
        am_new.setTextColor(blackColor);
        am_affairs.setTextColor(blackColor);
        am_policy.setTextColor(blackColor);
        am_project.setTextColor(blackColor);
        switch (CursorIndex) {
            case 0:
                am_new.setTextColor(mainThemeColor);
                break;
            case 1:
                am_policy.setTextColor(mainThemeColor);
                break;
            case 2:
                am_affairs.setTextColor(mainThemeColor);
                break;
            case 3:
                am_project.setTextColor(mainThemeColor);
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 过滤按键动作
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            moveTaskToBack(true);

        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
        super.onBackPressed();
    }
}
