package com.example.jit.plain.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jit.plain.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/6/30.
 */
public class AdpExpandableList extends BaseExpandableListAdapter {
    private Context context;
    List<String> parent;
    Map<String, List<String>> map = null;
    String name;
    public ViewHolder holder;

    public AdpExpandableList(Context context, List<String> parent, Map<String, List<String>> map) {
        this.context = context;
        this.parent = parent;
        this.map = map;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String key = parent.get(groupPosition);
        return (map.get(key).get(childPosition));
    }

    //得到子item的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //设置子item的组件
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        holder=null;
        int index;
        String key = this.parent.get(groupPosition);
        index = map.get(key).get(childPosition).indexOf(",");
        name = map.get(key).get(childPosition).substring(0,index);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_main_affairs_elvchild, null);
            holder = new ViewHolder();
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
        }
        holder.textChild.setText(name);
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
            convertView = inflater.inflate(R.layout.fragment_main_affairs_elvgroup, null);
            holder = new ViewHolder();
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
        }
        if (isExpanded)
            holder.imageView.setImageResource(
                    R.mipmap.triangle_bottom);
        else
            holder.imageView.setImageResource(
                    R.mipmap.triangle_right);
        holder.textGroup.setText(this.parent.get(groupPosition));
        holder.textCountGroup.setText(map.get(this.parent.get(groupPosition)).size()+"");
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
        @ViewInject(R.id.iv_fragment_main_affairs_elvgroup)
        private ImageView imageView;
        @ViewInject(R.id.tv_fragment_main_affairs_textchild)
        private TextView textChild;
        @ViewInject(R.id.tv_fragment_main_affairs_textgroup)
        private TextView textGroup;
        @ViewInject(R.id.tv_count_fragment_main_affairs_textgroup)
        private TextView textCountGroup;
    }

}


