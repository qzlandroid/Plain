package com.example.jit.plain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jit.model.ArticlesValueBean;
import com.example.jit.plain.R;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Max on 2016/7/22.
 */
public class AdpSearchListView extends BaseAdapter{
    private Context context;
    private List<ArticlesValueBean> list;
    private LayoutInflater mInflater;
    private ImageOptions options;
    public ViewHolder holder;
    private String id;
    private String type;
    public AdpSearchListView(Context context, List<ArticlesValueBean> list) {
        this.context = context;
        this.list = list;
        this.mInflater= LayoutInflater.from(context);
        options = new ImageOptions.Builder().setFailureDrawableId(R.mipmap.defaultpicture)
                .setLoadingDrawableId(R.mipmap.image_placeholder).setUseMemCache(true).build();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getTitle(int position) {
        return list.get(position).getTitle();
    }

    public String getId(int position) {
        return list.get(position).getId();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType(int position) {
        return list.get(position).getType();
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder=null;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.fragment_new_listview_item, null);
            holder=new ViewHolder();
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder) convertView.getTag();
        }
        initData(position);

        return convertView;
    }

    private void initData(int position) {
        ArticlesValueBean articlesValueBean = list.get(position);
        holder.tvTitle.setText(articlesValueBean.getTitle());
        holder.tvDetail.setText(articlesValueBean.getHtmltext());
    }

    class ViewHolder{
        @ViewInject(R.id.tv_fragment_main_lvitem_title)
        private TextView tvTitle;
        @ViewInject(R.id.tv_fragment_main_lvitem_detail)
        private TextView tvDetail;
    }
}
