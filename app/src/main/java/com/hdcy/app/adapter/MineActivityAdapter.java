package com.hdcy.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.model.ActivityContent;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.SizeUtils;
import com.squareup.picasso.Picasso;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by WeiYanGeorge on 2016-10-30.
 */

public class MineActivityAdapter extends BaseAdapter {

    private List<ActivityContent> data = new ArrayList<>();
    private Context context;

    private int width;

    private int imgheight;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    //WindowManager wm = (WindowManager) context.get


    public MineActivityAdapter(Context context, List<ActivityContent> data){
        super();
        this.context = context;
        this.data = data;
        width = SizeUtils.dpToPx(110);
        imgheight = SizeUtils.dpToPx(80);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public ActivityContent getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_mine_activity, null);
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

    private void setView(final int position, final ViewHolder holder) {
        ActivityContent item = data.get(position);
        Picasso.with(context).load(item.getImage())
                .resize(width,imgheight)
                .centerCrop()
                .into(holder.iv_mine_activity_logo);
        holder.tv_mine_activity_name.setText(item.getName()+"");
        holder.tv_mine_activity_locationtime.setText(item.getAddress());
        if(item.getState().equals("FINISH")){
            holder.tv_mine_activity_status.setText("已结束");
        }else {
            holder.tv_mine_activity_status.setText("正在进行");
        }
    }

    public class ViewHolder {
        private Object tag;
        private ImageView iv_mine_activity_logo;
        private TextView tv_mine_activity_name, tv_mine_activity_locationtime, tv_mine_activity_status;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            iv_mine_activity_logo = (ImageView) view.findViewById(R.id.iv_mine_activity_logo);
            tv_mine_activity_name = (TextView) view.findViewById(R.id.tv_mine_activity_name);
            tv_mine_activity_locationtime = (TextView) view.findViewById(R.id.tv_mine_activiy_locationtime);
            tv_mine_activity_status = (TextView) view.findViewById(R.id.tv_mine_activity_status);
        }

        //@ViewInject(R.id.tv_content)

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }

    }

    public interface OnItemClickListener{
        void onItem(int position);
    }

    public void OnItemListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
