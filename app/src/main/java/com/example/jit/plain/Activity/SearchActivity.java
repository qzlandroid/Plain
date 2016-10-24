package com.example.jit.plain.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jit.api.HttpEngine;
import com.example.jit.core.ActionCallbackListener;
import com.example.jit.model.ArticlesValueBean;
import com.example.jit.plain.Adapter.AdpSearchListView;
import com.example.jit.plain.Base.BaseActivity;
import com.example.jit.plain.PApplication;
import com.example.jit.plain.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Max on 2016/7/5.
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends BaseActivity {

    private Context context;
    private PApplication pApplication;

    @ViewInject(R.id.et_search_activity_search)
    private EditText editTextSearch;
    @ViewInject(R.id.tv_cancel_activity_search)
    private TextView textViewCancel;
    @ViewInject(R.id.lv_activity_search)
    private ListView listView;
    @ViewInject(R.id.tv_seg_left_activity_search)
    private TextView tv_left;
    @ViewInject(R.id.tv_seg_right_activity_search)
    private TextView tv_right;
    @ViewInject(R.id.iv_netwrong_activity_search)
    private ImageView iv_NetWrong;

    private String name = "";
    private int tag = 0;

    private AdpSearchListView adpSearchListView;
    private List<ArticlesValueBean> listViewItemModels = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
    }

    private void init() {
        initData();
        initViewPager();
        initListener();
    }

    private void initData() {
        context = this;
        editTextSearch.setText("");
        editTextSearch.setCursorVisible(false);
        iv_NetWrong.setVisibility(View.GONE);

    }

    private void initViewPager() {
        tv_left.setBackgroundResource(R.drawable.seg_left_on);
        tv_right.setBackgroundResource(R.drawable.seg_right);
        tv_left.setTextColor(getResources().getColor(R.color.colorWhite));
        tv_right.setTextColor(getResources().getColor(R.color.colorPrimary));

    }

    public void initListener() {
        editTextSearch.addTextChangedListener(textWatcher);
        editTextSearch.setOnKeyListener(onKeyListener);
    }

    @Event({R.id.tv_seg_right_activity_search, R.id.tv_seg_left_activity_search})
    private void clickseg(View view) {
        switch (view.getId()) {
            case R.id.tv_seg_left_activity_search:
                setUI(0);
                tag = 0;
                break;
            case R.id.tv_seg_right_activity_search:
                setUI(1);
                tag = 1;
                break;
        }
    }

    @Event(R.id.et_search_activity_search)
    private void clickEtSearch(View view) {
        editTextSearch.setCursorVisible(true);
        listView.setVisibility(View.VISIBLE);

    }

    @Event(R.id.tv_cancel_activity_search)
    private void clickCancel(View view) {
        listViewItemModels.clear();
        if (adpSearchListView != null)
            adpSearchListView.notifyDataSetChanged();
        editTextSearch.setText("");
        listView.setVisibility(View.INVISIBLE);
    }

    @Event(R.id.iv_netwrong_activity_search)
    private void clickIV(View view) {
        getDataFromServer(name);
    }

    /**
     * 更新标签的背景和字体颜色
     *
     * @param arg0 viewpager的页面
     */
    public void setUI(int arg0) {
        if (arg0 == 0) {
            tv_left.setBackgroundResource(R.drawable.seg_left_on);
            tv_right.setBackgroundResource(R.drawable.seg_right);
            tv_left.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_right.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            tv_left.setBackgroundResource(R.drawable.seg_left);
            tv_right.setBackgroundResource(R.drawable.seg_right_on);
            tv_right.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_left.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    /**
     * 输入法按下搜索的监听
     */
    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                /*隐藏软键盘*/
                listView.setVisibility(View.VISIBLE);
                iv_NetWrong.setVisibility(View.GONE);
                InputMethodManager inputMethodManager = (InputMethodManager) editTextSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }

                name = editTextSearch.getText().toString();

                if(name.equals("")) {
                    iv_NetWrong.setVisibility(View.GONE);
                    Toast.makeText(SearchActivity.this, "搜索的内容为空", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        name = URLEncoder.encode(name, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
//                System.out.println("DATADATA:"+name);

                    getDataFromServer(name);
                }
                return true;
            }
            return false;
        }
    };

    private void getDataFromServer(String name) {
        pApplication = (PApplication) this.getApplication();
        updateUI(pApplication, name, tag + "");
    }

    private void updateUI(PApplication pApplication, String name, String tag) {
        pApplication.getAppAction().getSearch(tag, name, new ActionCallbackListener<List<ArticlesValueBean>>() {
            @Override
            public void onSuccess(List<ArticlesValueBean> data) {
                listViewItemModels.clear();
                iv_NetWrong.setVisibility(View.GONE);
                if (data.size() == 0) {
                    Toast.makeText(SearchActivity.this, "无搜索结果", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setImage(HttpEngine.SERVER_URL + data.get(i).getImage());
//                    Log.e("image",data.get(i).getImage());
                        listViewItemModels.add(data.get(i));
                    }
                    adpSearchListView = new AdpSearchListView(context, listViewItemModels);
                    listView.setAdapter(adpSearchListView);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {

                iv_NetWrong.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            listViewItemModels.clear();
            if (adpSearchListView != null)
                adpSearchListView.notifyDataSetChanged();


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Event(value = R.id.lv_activity_search, type = AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, AllDetailActivity.class);
        intent.putExtra("id", adpSearchListView.getId(position));
        intent.putExtra("type", adpSearchListView.getType(position));
        intent.putExtra("title", adpSearchListView.getTitle(position));
        startActivity(intent);

    }
    public static String stringFilter(String str)throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String   regEx  =  "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p   =   Pattern.compile(regEx);
        Matcher m   =   p.matcher(str);
        return   m.replaceAll("").trim();
    }
}
