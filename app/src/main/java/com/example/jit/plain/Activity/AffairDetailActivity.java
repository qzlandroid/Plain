package com.example.jit.plain.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.jit.core.ActionCallbackListener;
import com.example.jit.model.VillageDetailValueBean;
import com.example.jit.plain.Adapter.AdpAffairsExpandableList;
import com.example.jit.plain.PApplication;
import com.example.jit.plain.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_affairsdetail)
public class AffairDetailActivity extends FragmentActivity {

    private Context context = this;
    private static final String TAG = "AffairDetailActivity";
    private PApplication pApplication;

    @ViewInject(R.id.tv_titlebar_activity_affairsdetail)
    private TextView titlebar;
    @ViewInject(R.id.elv_activity_affairsdetail)
    ExpandableListView expandableListView;
    List<String> parent = null;
    Map<String, List<VillageDetailValueBean>> map = null;
    AdpAffairsExpandableList adpAffairsExpandableList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
    }


    private void init() {
        initData();
        initListener();
    }

    private void initListener() {
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                Intent intent = new Intent(AffairDetailActivity.this, AllDetailActivity.class);
//                intent.putExtra("AffairDetailActivity", "true");
                intent.putExtra("id", adpAffairsExpandableList.getId(groupPosition, childPosition));
                intent.putExtra("title",adpAffairsExpandableList.getTitle(groupPosition,childPosition));
                intent.putExtra("type","3");
                startActivity(intent);
                return true;
            }
        });
    }

    private void initData() {
        /** 初始化标题栏 **/
        Intent intent = getIntent();
        String title = intent.getStringExtra("name");
        titlebar.setText(title);

        String regionid = intent.getStringExtra("regionid");

        parent = new ArrayList<String>();
        map = new HashMap<String, List<VillageDetailValueBean>>();
        getDataFromServer(parent, map, regionid);

    }

    /**
     * 返回按钮
     */
    @Event(R.id.iv_back_activity_affairsdetail)
    private void backOnclick(View view) {
        finish();
    }

    private void getDataFromServer(final List<String> parent, final Map<String, List<VillageDetailValueBean>> map, String regionid) {


        pApplication = (PApplication) this.getApplication();
        pApplication.getAppAction().getVillageDetail(regionid, new ActionCallbackListener<List<List<VillageDetailValueBean>>>() {

            @Override
            public void onSuccess(List<List<VillageDetailValueBean>> data) {
                String[] strings = {"党务公开", "事务公开", "财务公开", "惠农资金"};
                for (int i = 0; i < data.size(); i++) {
                    parent.add(strings[i]);
                }

                /**填充child标题**/
                for (int i = 0; i < data.size(); i++) {
                    List<VillageDetailValueBean> list = new ArrayList<VillageDetailValueBean>();
                    for (int j = 0; j < data.get(i).size(); j++) {

//                        Log.e(TAG,data.get(i).get(j).getTitle());
                        list.add(data.get(i).get(j));

                    }
                    map.put(strings[i], list);
                }

                adpAffairsExpandableList = new AdpAffairsExpandableList(context, parent, map);
                expandableListView.setAdapter(adpAffairsExpandableList);
                /**设置默认展开**/
                int groupCount = expandableListView.getCount();

                for (int i = 0; i < groupCount; i++) {
                    expandableListView.expandGroup(i);
                }


            }

            @Override
            public void onFailure(String errorEvent, String message) {

            }
        });
    }

    protected void onResume() {
        super.onResume();

    }
}
