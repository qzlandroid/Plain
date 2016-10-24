package com.example.jit.plain.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jit.api.HttpEngine;
import com.example.jit.core.ActionCallbackListener;
import com.example.jit.model.ArticlesValueBean;
import com.example.jit.model.CarouselValueBean;
import com.example.jit.plain.Activity.AllDetailActivity;
import com.example.jit.plain.Adapter.AdpListViewItem;
import com.example.jit.plain.Adapter.AdpViewPagerItem;
import com.example.jit.plain.PApplication;
import com.example.jit.plain.R;
import com.example.jit.plain.Utils.ChildViewPager;
import com.example.jit.plain.Utils.XUtils;
import com.example.jit.plain.db.NewTable;
import com.example.jit.plain.db.PolicyTable;
import com.example.jit.plain.db.ProjectTable;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 惠农政策
 * Created by Max on 2016/6/29.
 */
@ContentView(R.layout.fragment_main_policy)
public class PolicyFragment extends Fragment{
    private Context context;
    private static String TAG = null;
    private AdpListViewItem adpListViewItem;
    private  AdpViewPagerItem adpViewPagerItem;
    private  List<ArticlesValueBean> mainListViewItemModels = new ArrayList<ArticlesValueBean>();

    private ArrayList<CarouselValueBean> listImgURL = new ArrayList<CarouselValueBean>();
    private PApplication pApplication;
    private Handler handler;
    private XUtils xUtils;
    private DbManager db;

    private String last = "0";
    private String since = "0";
    private Boolean isLoad = false;
    private Boolean hasCache = false;

    int width = 0;
    int height = 0;

    @ViewInject(R.id.lv_fragment_main_policy)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.iv_fragment_main_policy)
    private ImageView imageView;
    private ChildViewPager homePager;
    private ListView listView;
    private ArrayList<String> listtitle;
    private TextView tv_title;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        context = inflater.getContext();
        TAG = context.getClass().getSimpleName();
        view = x.view().inject(this, inflater, container);
        initData();
        return  view;
    }


    /**
     * 初始化数据
     */
    private void initData() {
        initRefreshListView();
        initHeaderView();
        getDataFromServer("1", "0", "0");
        //initDB();
        listView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
       // handler = new Handler();
        //handler.postDelayed(LOAD_DATA, 0);

    }
   /* private Runnable LOAD_DATA = new Runnable() {
        @Override
        public void run() {
            //在这里数据内容加载到Fragment上

            try {
                List<NewTable> findAll = db.findAll(NewTable.class);//返回当前表里面的所有数据
                if (findAll != null) {
                    hasCache = true;
                    for (int i = findAll.size() - 1; i >= 0; i--) {
                        ArticlesValueBean articlesValueBean = new ArticlesValueBean();
                        articlesValueBean.setId(findAll.get(i).id);
                        articlesValueBean.setTitle(findAll.get(i).title);
                        articlesValueBean.setType(findAll.get(i).type);
                        articlesValueBean.setHtmltext(findAll.get(i).content);
                        articlesValueBean.setImage(findAll.get(i).image);
                        articlesValueBean.setUploadtime(findAll.get(i).uploadtime);
                        mainListViewItemModels.add(0, articlesValueBean);
                    }
                    adpListViewItem = new AdpListViewItem(context, mainListViewItemModels);
                    adpListViewItem.notifyDataSetChanged();

                    listView.setAdapter(adpListViewItem);
                }
            } catch (DbException e) {
                e.printStackTrace();
            }


            getDataFromServer("1", "0", "0");


        }
    };*/
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
                getDataFromServer("1",last,"0");
                new FinishRefresh().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                isLoad = true;
                getDataFromServer("1","0",since);
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

    public void initDB(){
        DbManager.DaoConfig daoConfig = xUtils.getDaoConfig();
        db = x.getDb(daoConfig);
    }

    public void initTable(List<ArticlesValueBean> data){


            List<PolicyTable> list = new ArrayList<PolicyTable>();
            for (int i = 0; i < data.size(); i++) {
                PolicyTable policyTable = new PolicyTable();
                policyTable.id = data.get(i).getId();
                policyTable.content = data.get(i).getContent();
                policyTable.title = data.get(i).getTitle();
                policyTable.image = data.get(i).getImage();
                policyTable.uploadtime = data.get(i).getUploadtime();
                policyTable.type = data.get(i).getType();

                list.add(policyTable);
            }

    }


    /**
     * 图片的点击事件,点击重新获取数据
     * @param view
     */
    @Event(R.id.iv_fragment_main_policy)
    private void onclickIV(View view){
        getDataFromServer("1","0","0");
    }

    /**
     * listview Item 点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Event(value = R.id.lv_fragment_main_policy,type = AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position > 1) {
            Intent intent = new Intent(getActivity(), AllDetailActivity.class);
            intent.putExtra("id", adpListViewItem.getId(position - 2));
            intent.putExtra("type", adpListViewItem.getType(position - 2));
            intent.putExtra("title",adpListViewItem.getTitle(position - 2));
            startActivity(intent);
        }
    }


    private void updateArticles(List<ArticlesValueBean> data) {

        for (int i = data.size()-1; i >= 0; i--) {
            data.get(i).setHtmltext(data.get(i).getContent());
            data.get(i).setImage(HttpEngine.SERVER_URL + data.get(i).getImage());
            mainListViewItemModels.add(0,data.get(i));
        }

        adpListViewItem = new AdpListViewItem(context, mainListViewItemModels);
        adpListViewItem.notifyDataSetChanged();

        listView.setAdapter(adpListViewItem);

        last = mainListViewItemModels.get(0).getUploadtime();
        since = mainListViewItemModels.get(mainListViewItemModels.size()-1).getUploadtime();
    }
    private void loadArticles(List<ArticlesValueBean> data) {

        for (int i = 0; i < data.size(); i++) {
            data.get(i).setHtmltext(data.get(i).getContent());
            data.get(i).setImage(HttpEngine.SERVER_URL + data.get(i).getImage());
            mainListViewItemModels.add(mainListViewItemModels.size(),data.get(i));
        }

        adpListViewItem = new AdpListViewItem(context, mainListViewItemModels);
        adpListViewItem.notifyDataSetChanged();
        listView.setAdapter(adpListViewItem);
        listView.setSelection(mainListViewItemModels.size()-1-data.size());
        last = mainListViewItemModels.get(0).getUploadtime();
        since = mainListViewItemModels.get(mainListViewItemModels.size()-1).getUploadtime();
    }
    private void getDataFromServer(String tag, String lastupload, String sinceupload) {


        pApplication = (PApplication) getActivity().getApplication();

        //加载更多的时候不获取轮播图

            pApplication.getAppAction().getCarousel(tag, new ActionCallbackListener<List<CarouselValueBean>>() {
                @Override
                public void onSuccess(List<CarouselValueBean> data) {
                    listView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    updateCarousel(data);
                }

                @Override
                public void onFailure(String errorEvent, String message) {


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

}
