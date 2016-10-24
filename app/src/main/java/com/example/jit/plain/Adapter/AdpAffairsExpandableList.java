package com.example.jit.plain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.jit.model.VillageDetailValueBean;
import com.example.jit.plain.R;
import com.example.jit.plain.Utils.DateUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/6/30.
 */
public class AdpAffairsExpandableList extends BaseExpandableListAdapter {
    private Context context;
    List<String> parent;
    Map<String, List<VillageDetailValueBean>> map = null;
    DateUtils dateUtils = new DateUtils();
    public ViewHolder holder;
    private String id;

    public AdpAffairsExpandableList(Context context, List<String> parent, Map<String, List<VillageDetailValueBean>> map) {
        this.context = context;
        this.parent = parent;
        this.map = map;
    }

    //得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String key = parent.get(groupPosition);
        return (map.get(key).get(childPosition));
    }

    public String getId(int groupPosition, int childPosition) {
        String key = parent.get(groupPosition);
        return (map.get(key).get(childPosition).getId())+"";
    }

    public void setId(String id) {
        this.id = id;
    }

    //得到子item的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public String getTitle(int groupPosition, int childPosition) {
        String key = parent.get(groupPosition);
        return map.get(key).get(childPosition).getTitle();
    }

    //设置子item的组件
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        holder=null;
        String key = this.parent.get(groupPosition);
        String info = map.get(key).get(childPosition).getTitle();
        String time = dateUtils.getPassedTime(new Date().getTime(),Long.parseLong(map.get(key).get(childPosition).getUploadtime()))+"前 ";
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.avtivity_affairsdetail_elvchild, null);
            holder = new ViewHolder();
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
        }
        holder.textChild.setText(info);
        holder.textTime.setText(time);
        return convertView;
    }

    //获取当前父item下的子item的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        String key = parent.get(groupPosition);
        int size = map.get(key).size();
        return size;
    }
    //获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return parent.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return parent.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    //设置父item组件
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        holder=null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_affairsdetail_elvgroup, null);
            holder = new ViewHolder();
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
        }

        holder.textGroup.setText(this.parent.get(groupPosition));
        return  convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder{
        @ViewInject(R.id.tv_time_activity_affairsdetail_textchild)
        private TextView textTime;
        @ViewInject(R.id.tv_activity_affairsdetail_textchild)
        private TextView textChild;
        @ViewInject(R.id.tv_activity_affairsdetail_textgroup)
        private TextView textGroup;
    }

}


