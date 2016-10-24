package com.hdcy.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hdcy.app.R;
import com.hdcy.base.BaseInfo;
import com.squareup.picasso.Picasso;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by WeiYanGeorge on 2016-09-03.
 */

public class ImageListViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;

    public ImageListViewAdapter(Context context, List<String> data) {
        super();
        this.context = context;
        this.data = data;
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_imgs_activity, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setTag(position);
        setView(position, holder);
        return convertView;
    }

    private void setView(final int position,final ViewHolder holder){
        String url = data.get(position);
        Picasso.with(context).load(url)
                .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                .error(BaseInfo.PICASSO_ERROR)
                .tag(position)
                .into(holder.iv_imgs_actvity);

    }


    class ViewHolder {


        private Object tag;

        ImageView iv_imgs_actvity;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            iv_imgs_actvity = (ImageView) view.findViewById(R.id.iv_imgs_activity);
        }

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }
    }

}
