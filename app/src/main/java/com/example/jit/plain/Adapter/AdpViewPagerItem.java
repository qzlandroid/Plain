package com.example.jit.plain.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.jit.model.CarouselValueBean;
import com.example.jit.plain.R;
import com.squareup.picasso.Picasso;

import org.xutils.image.ImageOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图片的适配器
 * Created by Max on 2016/6/30.
 */
public class AdpViewPagerItem extends PagerAdapter {
    private Context context;
    private ImageOptions options;
    List<ImageView> list = null;

    public AdpViewPagerItem(Context context ,List<ImageView> imgList) {
        this.context = context;
        this.list = imgList;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager)container).removeView(list.get(position));

    }

    @Override
    public Object instantiateItem(View container, int position) {
        // TODO Auto-generated method stub
        ((ViewPager) container).addView(list.get(position));
        return list.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return (arg0 == arg1);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
