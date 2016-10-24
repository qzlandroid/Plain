package com.example.jit.plain.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jit.core.ActionCallbackListener;
import com.example.jit.model.VillageValueBean;
import com.example.jit.plain.Activity.AffairDetailActivity;
import com.example.jit.plain.Adapter.AdpExpandableList;
import com.example.jit.plain.PApplication;
import com.example.jit.plain.R;
import com.example.jit.plain.Utils.Flowlayout;
import com.example.jit.plain.Utils.NoScrollGridView;
import com.example.jit.plain.Utils.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 村务公开
 * Created by Max on 2016/6/29.
 */
@ContentView(R.layout.fragment_main_affairs)
public class AffairsFragment extends Fragment {

    private Context context;
    private final static String TAG = "AffairsFragment";
    private List<String> city = null;
    private Map<String, List<String>> viliger = null;
    private ArrayList<Flowlayout> layouts = new ArrayList<>();
    private AdpExpandableList adpExpandableList;
    private PApplication pApplication;
    private ArrayList<Integer> flags = new ArrayList<>();

    @ViewInject(R.id.lv_affairs)
    private ListView listView;
    @ViewInject(R.id.iv_fragment_main_affairs)
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        context = inflater.getContext();
        view = x.view().inject(this, inflater, container);

        init();
        return view;
    }

    private void init() {
        initData();
        //initListener();
    }
    /*private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("我被点击了");
                NoScrollGridView gv = (NoScrollGridView) view.findViewById(R.id.gv_viliger);
                ImageView img = (ImageView)view.findViewById(R.id.iv_fragment_main_affairs_elvgroup);
                if (!flags.contains(position)){
                    gv.setVisibility(View.VISIBLE);
                    img.setImageResource(R.mipmap.triangle_bottom);
                    flags.add(position);
                }else {
                    gv.setVisibility(View.GONE);
                    img.setImageResource(R.mipmap.triangle_right);
                    int index = flags.indexOf(position);
                    flags.remove(index);
                }

            }
        });
    }*/
   /* private void initListener() {
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), AffairDetailActivity.class);
                String name = adpExpandableList.getChild(groupPosition,childPosition)+"";
                int index = name.indexOf(",");


                intent.putExtra("name", name.substring(0,index));
//                Log.d(TAG,adpExpandableList.getRegionid());
                intent.putExtra("regionid",name.substring(index+1));

                //System.out.println("parent:"+parent);
                //System.out.println("map:"+map);
                startActivity(intent);
                return true;

            }
        });
    }*/

    private void initData() {
        listView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        city = new ArrayList<String>();
        viliger = new HashMap<String, List<String>>();
        getDataFromServer(city, viliger);

    }

    /**
     * 从服务器获取数据
     *
     * @param city ExpandableList的 parent 内容
     * @param viliger    ExpandableList 的 child 内容
     */
    private void getDataFromServer(final List<String> city, final Map<String, List<String>> viliger) {


        pApplication = (PApplication) getActivity().getApplication();
        pApplication.getAppAction().getVillage(new ActionCallbackListener<List<VillageValueBean>>() {
            @Override
            public void onSuccess(List<VillageValueBean> data) {
//                Log.e(TAG,data.toString());
                //expandableListView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                if (data.size() != 0) {

                    for (int i = 0; i < data.size(); i++) {
                        city.add(data.get(i).getName());
                        List<String> list = new ArrayList<String>();
                        for (int j = 0; j < data.get(i).getVillage().size(); j++) {
                            list.add(data.get(i).getVillage().get(j).getName() + "," + data.get(i).getVillage().get(j).getId());
                        }
                        viliger.put(data.get(i).getName(), list);

                    }
                    listView.setAdapter(new AffairsLvAdapter());
                    //CreateViligerLayout();
                    //System.out.println("parent:"+parent);
                    //System.out.println("map:");

                    //adpExpandableList = new AdpExpandableList(context, parent, map);
                   // expandableListView.setAdapter(adpExpandableList);
                }else {
                    //expandableListView.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(x.app(), message, Toast.LENGTH_SHORT).show();
                //expandableListView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }

   /* private void CreateViligerLayout() {

        for (int i = 0; i< city.size(); i++){
            Flowlayout layout = new Flowlayout(context);
            int padding=UiUtils.dp2px(context,15);
            layout.setPadding(padding, padding, padding, padding);
            List<String> names = viliger.get(city.get(i));
            for (int j =0; j< names.size();j++){
                int index = names.get(j).indexOf(",");
                final String name = names.get(j).substring(0,index);
                final String id = names.get(j).substring(index+1);
                //System.out.println("names"+name);
                TextView textView = new TextView(context);
                textView.setText(name);
                textView.setBackgroundDrawable(createSelector());
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                //textView.setTextSize(UiUtils.dp2px(context,8));
                textView.setTextColor(Color.WHITE);
                textView.setPadding(UiUtils.dp2px(context,8),UiUtils.dp2px(context,8),UiUtils.dp2px(context,8),UiUtils.dp2px(context,8));

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AffairDetailActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("regionid",id);
                        startActivity(intent);
                    }
                });
                //textView.setLayoutParams(new ViewGroup.MarginLayoutParams());
                System.out.print(name+"  ");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2,-2);
                params.setMargins(10,10,10,10);
                layout.addView(textView,params);
            }
            System.out.println("hhahahahah"+city.get(i)+layout.getChildCount());
            layouts.add(layout);
        }
    }
*/
    /**
     * 图片的点击事件,点击重新获取数据
     *
     * @param view
     */
    @Event(R.id.iv_fragment_main_affairs)
    private void onclickIV(View view) {
        getDataFromServer(city,viliger);
    }



    class AffairsLvAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return city.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null){
                convertView = View.inflate(context,R.layout.fragment_main_affairs_elvgroup,null);
                holder = new ViewHolder();
                holder.linear = (LinearLayout)convertView.findViewById(R.id.ll_fragment_main_affairs_elvgroup);
                holder.imgArr = (ImageView)convertView.findViewById(R.id.iv_fragment_main_affairs_elvgroup);
                holder.tvname = (TextView)convertView.findViewById(R.id.tv_fragment_main_affairs_textgroup);
                holder.tvnum = (TextView)convertView.findViewById(R.id.tv_count_fragment_main_affairs_textgroup);
                holder.gridView = (NoScrollGridView) convertView.findViewById(R.id.gv_viliger);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tvname.setText(city.get(position));
            holder.tvnum.setText(viliger.get(city.get(position)).size()+"");
            holder.gridView.setAdapter(new MyGvAdapter(position));
            //holder.linearLayout.removeAllViews();
            //holder.linearLayout.addView(layouts.get(position));
            final ViewHolder finalHolder = holder;
            final ViewHolder finalHolder1 = holder;
            holder.linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!flags.contains(position)){
                        finalHolder.gridView.setVisibility(View.VISIBLE);
                        finalHolder1.imgArr.setImageResource(R.mipmap.triangle_bottom);
                        flags.add(position);
                    }else {
                        finalHolder.gridView.setVisibility(View.GONE);
                        finalHolder1.imgArr.setImageResource(R.mipmap.triangle_right);
                        int index = flags.indexOf(position);
                        flags.remove(index);
                    }
                }
            });
            return convertView;
        }
    }
    static class ViewHolder{
        public LinearLayout linear;
        public ImageView imgArr;
        public TextView tvname;
        public TextView tvnum;
        private NoScrollGridView gridView;
    }

    private GradientDrawable createShape(int color){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(UiUtils.dp2px(context,5));
        drawable.setColor(color);
        return drawable;
    }
    private StateListDrawable createSelector(){
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed},createShape(0xffcecece)); //按下时的图片
        stateListDrawable.addState(new int[]{},createShape(getResources().getColor(R.color.viligerbg))); //抬起时的图片
        return stateListDrawable;
    }


    /*List<String> names = viliger.get(city.get(i));
    for (int j =0; j< names.size();j++){
        int index = names.get(j).indexOf(",");
        final String name = names.get(j).substring(0,index);
        final String id = names.get(j).substring(index+1);
        //System.out.println("names"+name);
        TextView textView = new TextView(context);*/
    class MyGvAdapter extends BaseAdapter{
        int mcityposition;

        List<String> viligers = new ArrayList<>();
        List<String> viligernames = new ArrayList<>();
        List<String> viligerids = new ArrayList<>();

        public MyGvAdapter(int cityposition) {
            mcityposition = cityposition;
            viligers = viliger.get(city.get(mcityposition));
            for(int i = 0; i < viligers.size(); i++) {
                int index = viligers.get(i).indexOf(",");
                viligernames.add(viligers.get(i).substring(0, index));
                viligerids.add(viligers.get(i).substring(index + 1));
            }
        }

        @Override
        public int getCount() {
            return viligers.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            View view = View.inflate(context,R.layout.gv_villger_item,null);
            TextView textView = (TextView) view.findViewById(R.id.tv_item);
            final String name = viligernames.get(position);
            final String id = viligerids.get(position);
            textView.setText(name);
            textView.setBackgroundDrawable(createSelector());
            textView.setGravity(Gravity.CENTER_HORIZONTAL);

            //textView.setTextSize(UiUtils.dp2px(context,8));
            textView.setTextColor(Color.WHITE);
            textView.setPadding(UiUtils.dp2px(context,8),UiUtils.dp2px(context,8),UiUtils.dp2px(context,8),UiUtils.dp2px(context,8));

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AffairDetailActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("regionid",id);
                    startActivity(intent);
                }
            });

            return view;
        }
    }
}
