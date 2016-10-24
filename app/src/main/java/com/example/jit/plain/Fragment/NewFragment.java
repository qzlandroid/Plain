package com.example.jit.plain.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jit.api.HttpEngine;
import com.example.jit.core.ActionCallbackListener;
import com.example.jit.model.ArticlesValueBean;
import com.example.jit.model.CarouselValueBean;
import com.example.jit.plain.Activity.AllDetailActivity;
import com.example.jit.plain.Adapter.AdpNewListView;
import com.example.jit.plain.Adapter.AdpSearchListView;
import com.example.jit.plain.Adapter.AdpViewPagerItem;
import com.example.jit.plain.PApplication;
import com.example.jit.plain.R;
import com.example.jit.plain.Utils.ChildViewPager;
import com.example.jit.plain.Utils.DateUtils;
import com.example.jit.plain.Utils.XUtils;
import com.example.jit.plain.db.NewTable;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 最新
 * Created by Max on 2016/6/29.
 */

@ContentView(R.layout.fragment_main_new)
public class NewFragment extends Fragment implements ViewPager.OnPageChangeListener{
    private Context context;
    private static String TAG = null;
    private PApplication pApplication;
    private AdpNewListView adpNewListView;
    private AdpViewPagerItem adpViewPagerItem;
    private List<ArticlesValueBean> mainListViewItemModels = new ArrayList<ArticlesValueBean>();
    private ArrayList<CarouselValueBean> listImgURL = new ArrayList<CarouselValueBean>();
    private Handler handler;
    private XUtils xUtils;
    //private DbManager db;

    private String last = "0";
    private String since = "0";
    private Boolean isLoad = false;
    private Boolean hasCache = false;
    int width = 0;
    int height = 0;


    @ViewInject(R.id.lv_fragment_main_new)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.iv_fragment_main_new)
    private ImageView imageView;
    private ChildViewPager homePager;
    private ListView listView;
    private Object cache;
    private CirclePageIndicator mIndicator;
    private TextView tv_title;
    private ArrayList<String> listtitle;
    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        context = inflater.getContext();
        TAG = context.getClass().getSimpleName();
        view = x.view().inject(this, inflater, container);
        initData();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        pApplication = (PApplication) getActivity().getApplication();
        initRefreshListView();
        initHeaderView();
        //initDB();
        getDataFromServer("0", "0", "0");
        listView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        //handler = new Handler();
        //handler.postDelayed(LOAD_DATA, 0);

    }

    /**
     * 初始化PullToRefreshListView
     **/
    private void initRefreshListView() {
        // 1.初始化refreshListView，并且给它设置刷新监听
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);// 设置两边都可以啦
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isLoad = false;
                getDataFromServer("0",last,"0");
                new FinishRefresh().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                isLoad = true;
                getDataFromServer("0","0",since);
                new FinishRefresh().execute();
            }
        });

        listView = pullToRefreshListView.getRefreshableView();
        listView.setDividerHeight(0);// 隐藏listview默认的divider
        listView.setSelector(android.R.color.transparent);// 隐藏listview默认的selector

    }


    private void initHeaderView(){
        View headerView = View.inflate(getActivity(), R.layout.fragment_main_viewpager, null);
        homePager = (ChildViewPager) headerView.findViewById(R.id.headviewpager);
        mIndicator = (CirclePageIndicator) headerView.findViewById(R.id.mindicator);
        tv_title = (TextView)headerView.findViewById(R.id.tv_title);
        //1.计算出ViewPager的宽度
        width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        //2.根据图片的宽高比，计算出对应的高度，宽高比是2.65
        height = (int) (width/2f);
        //3.将计算出来的高度重新赋值给VIewPager的高度
        //LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) homePager.getLayoutParams();
        //layoutParams.height = (int) height;//重新给布局参数设置
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) homePager.getLayoutParams();
        layoutParams.height = (int) height;//重新给布局参数设置
        homePager.setLayoutParams(layoutParams);
        // homePager.requestLayout();//也可以这样写
        listView.addHeaderView(headerView);
    }

    /**
     * 图片的点击事件,点击重新获取数据
     *
     * @param view
     */
    @Event(R.id.iv_fragment_main_new)
    private void onclickIV(View view) {
        getDataFromServer("0", "0", "0");
    }

    /**
     * listview Item 点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Event(value = R.id.lv_fragment_main_new, type = AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //////////////////////////////////////////////////////////////
        if (position > 1) {
            Intent intent = new Intent(getActivity(), AllDetailActivity.class);
            intent.putExtra("id", adpNewListView.getId(position - 2));
            intent.putExtra("title",adpNewListView.getTitle(position - 2 ));
            intent.putExtra("type", "3");
            startActivity(intent);
        }
    }


    /*public void initDB(){
        DbManager.DaoConfig daoConfig = xUtils.getDaoConfig();
        db = x.getDb(daoConfig);
    }*/

    public void initTable(List<ArticlesValueBean> data){

            List<NewTable> list = new ArrayList<NewTable>();
            for (int i = 0; i < data.size(); i++) {
                NewTable newTable = new NewTable();
                newTable.id = data.get(i).getId();
                newTable.content = data.get(i).getContent();
                newTable.title = data.get(i).getTitle();
                newTable.image = data.get(i).getImage();
                newTable.uploadtime = data.get(i).getUploadtime();

                list.add(newTable);
            }
//            db.save(list);//保存实体类或实体类的List到数据库
          //db.saveOrUpdate(list);//保存或更新实体类或实体类的List到数据库, 根据id对应的数据是否存在
//          db.saveBindingId(list);保存实体类或实体类的List到数据库,如果该类型的id是自动生成的, 则保存完后会给id赋值

    }

   /* private Runnable LOAD_DATA = new Runnable() {
        @Override
        public void run() {
            //在这里数据内容加载到Fragment上

            try {
               *//* List<NewTable> findAll = db.findAll(NewTable.class);//返回当前表里面的所有数据
                if (findAll != null) {
                    hasCache = true;
                    for (int i = findAll.size() - 1; i >= 0; i--) {
                        ArticlesValueBean articlesValueBean = new ArticlesValueBean();
                        articlesValueBean.setId(findAll.get(i).id);
                        articlesValueBean.setTitle(findAll.get(i).title);
                        articlesValueBean.setImage(findAll.get(i).image);
                        articlesValueBean.setUploadtime(findAll.get(i).uploadtime);
                        mainListViewItemModels.add(0, articlesValueBean);
                    }
                    adpNewListView = new AdpNewListView(context, mainListViewItemModels);
                    adpNewListView.notifyDataSetChanged();

                    listView.setAdapter(adpNewListView);*//*
                }
            } catch (DbException e) {
                e.printStackTrace();
            }


            getDataFromServer("0", "0", "0");


        }
    };*/

    private void updateArticles(List<ArticlesValueBean> data) {

        for (int i = data.size()-1; i >= 0; i--) {

            mainListViewItemModels.add(0,data.get(i));
        }

        adpNewListView = new AdpNewListView(context, mainListViewItemModels);
        adpNewListView.notifyDataSetChanged();

        listView.setAdapter(adpNewListView);

        last = mainListViewItemModels.get(0).getUploadtime();
        since = mainListViewItemModels.get(mainListViewItemModels.size()-1).getUploadtime();
    }
    private void loadArticles(List<ArticlesValueBean> data) {

        for (int i = 0; i < data.size(); i++) {

            mainListViewItemModels.add(mainListViewItemModels.size(),data.get(i));
        }

        adpNewListView = new AdpNewListView(context, mainListViewItemModels);
        adpNewListView.notifyDataSetChanged();
        listView.setAdapter(adpNewListView);
        listView.setSelection(mainListViewItemModels.size()-1-data.size());
        last = mainListViewItemModels.get(0).getUploadtime();
        since = mainListViewItemModels.get(mainListViewItemModels.size()-1).getUploadtime();
    }
    private void getDataFromServer(String tag, String lastupload, String sinceupload) {

        //加载更多的时候不获取轮播图

            pApplication.getAppAction().getCarousel(tag, new ActionCallbackListener<List<CarouselValueBean>>() {
                @Override
                public void onSuccess(List<CarouselValueBean> data) {
                    listView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    System.out.println("data..."+data);
                    updateCarousel(data);

                }

                @Override
                public void onFailure(String errorEvent, String message) {
//                    Toast.makeText(context, "连接服务器失败", Toast.LENGTH_SHORT).show();
                }
            });

        pApplication.getAppAction().getArticles(tag, lastupload, sinceupload, new ActionCallbackListener<List<ArticlesValueBean>>() {
            @Override
            public void onSuccess(List<ArticlesValueBean> data) {
                listView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                if (isLoad){
                    loadArticles(data);
                }else {
                    updateArticles(data);
                }
               initTable(data);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                if (!hasCache) {
                    listView.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                }
//                Toast.makeText(context, "连接服务器失败", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void updateCarousel(final List<CarouselValueBean> data) {

        List<ImageView> listtemp = new ArrayList<ImageView>();
        listtitle = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            ImageView img = new ImageView(context);
            img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 200));
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            data.get(i).setImage(HttpEngine.SERVER_URL + data.get(i).getImage());
            Picasso.with(context).load(data.get(i).getImage()).error(R.mipmap.image_placeholder).resize(width,height).into(img);
            listtemp.add(img);
            listtitle.add(data.get(i).getTitle());
        }
        adpViewPagerItem = new AdpViewPagerItem(context, listtemp);
        homePager.setAdapter(adpViewPagerItem);
        homePager.addOnPageChangeListener(this);
        mIndicator.setViewPager(homePager);
        mIndicator.setSnap(true);// 支持快照显示
        mIndicator.onPageSelected(0);// 让指示器重新定位到第一个点
        tv_title.setText(listtitle.get(0));
        adpViewPagerItem.notifyDataSetChanged();
//        Log.e("addHeaderView","true");

        /** viewpager的点击监听事件 **/
        homePager.setOnSingleTouchListener(new ChildViewPager.OnSingleTouchListener() {
            @Override
            public void onSingleTouch() {
                int position = homePager.getCurrentItem();
                Intent intent = new Intent(getActivity(), AllDetailActivity.class);
                intent.putExtra("id", data.get(position).getId());
                intent.putExtra("type", data.get(position).getType());
                startActivity(intent);
            }
        });
        autoPlay();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tv_title.setText(listtitle.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            //          adapter.notifyDataSetChanged();
            pullToRefreshListView.onRefreshComplete();
        }
    }

    private void autoPlay() {
        if (mHandler == null) {
            mHandler = new Handler() {
                public void handleMessage(android.os.Message msg) {

                    int currentItem = homePager.getCurrentItem();
                    if (currentItem < 1) {
                        currentItem++;
                    } else {
                        currentItem = 0;
                    }
                    homePager.setCurrentItem(currentItem);// 切换到下一个页面
                    mHandler.sendEmptyMessageDelayed(0, 3000);// 继续延时3秒发消息,
                    // 形成循环
                }
            };

            mHandler.sendEmptyMessageDelayed(0, 3000);// 延时3秒后发消息
        }
    }

}
