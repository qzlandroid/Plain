package com.example.jit.plain.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jit.core.ActionCallbackListener;
import com.example.jit.plain.Base.BaseActivity;
import com.example.jit.plain.PApplication;
import com.example.jit.plain.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 所有的详情内容
 * Created by Max on 2016/7/5.
 */
@ContentView(R.layout.activity_alldetail)
public class AllDetailActivity extends BaseActivity {

    @ViewInject(R.id.wv_avtivity_alldetail)
    private WebView webView;
    @ViewInject(R.id.iv_back_activity_alldetail)
    private ImageView ivBack;
    @ViewInject(R.id.tv_share_activity_alldetail)
    private TextView tvShare;

    private PApplication pApplication;
    private String url = null;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
    }

    private void init() {
        initData();
        initWebView();
    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        //支持js
        settings.setJavaScriptEnabled(true);
        //设置字符编码
        settings.setDefaultTextEncodingName("utf-8");
        // 支持缩放
        settings.setSupportZoom(true);
        // //启用内置缩放装置
        settings.setBuiltInZoomControls(true);
        // 支持自动加载图片
        settings.setLoadsImagesAutomatically(true);
        // 支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    private void initData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String type = intent.getStringExtra("type");
        title = intent.getStringExtra("title");
//        Log.e("AllDetail", "id = " + id + "type = " + type);

        if (type.equals("3")) {
            getDataFromServer2(id, type);
        } else {
            getDataFromServer(id, type);
        }
    }

    @Event(R.id.tv_share_activity_alldetail)
    private void share(View view) {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
            oks.setText(title);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

// 启动分享GUI
        oks.show(this);

    }

    @Event(R.id.iv_back_activity_alldetail)
    private void backClick(View view) {
        finish();
    }


    private void getDataFromServer2(String id, String type) {
        pApplication = (PApplication) this.getApplication();
        pApplication.getAppAction().getVillageArticlesDetail(id, type, new ActionCallbackListener<String>() {
            @Override
            public void onSuccess(String data) {
//                Log.e("getDataFromServer2", "ddddddddddd");
                url = data;
                webView.loadUrl(data);
            }

            @Override
            public void onFailure(String errorEvent, String message) {

            }
        });
    }

    private void getDataFromServer(String id, String type) {
        pApplication = (PApplication) this.getApplication();
        updataUI(id, type);
//        Log.e("url",HttpEngine.SERVER_URL);
    }

    private void updataUI(String id, String type) {
        pApplication.getAppAction().getArticlesDetail(type, id, new ActionCallbackListener<String>() {
            @Override
            public void onSuccess(String data) {
//                Log.e("url",HttpEngine.SERVER_URL + data);
                url = data;
                webView.loadUrl(data);
            }

            @Override
            public void onFailure(String errorEvent, String message) {

            }
        });
    }
}
