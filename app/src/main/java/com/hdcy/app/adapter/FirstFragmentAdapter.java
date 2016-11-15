package com.hdcy.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.model.VideoBasicInfo;
import com.hdcy.base.utils.SizeUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by WeiYanGeorge on 2016-11-14.
 */

public class FirstFragmentAdapter extends BaseAdapter {

    private List<VideoBasicInfo> data = new ArrayList<>();
    private Context context;

    private int width;

    private int imgheight;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    //WindowManager wm = (WindowManager) context.get


    public FirstFragmentAdapter(Context context, List<VideoBasicInfo> data){
        super();
        this.context = context;
        this.data = data;
/*        width = SizeUtils.getScreenWidth();
        imgheight = SizeUtils.dpToPx(200);*/
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public VideoBasicInfo getItem(int position) {
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
            convertView = View.inflate(context, R.layout.item_video_list, null);
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
        //VideoBasicInfo item = data.get(position);
        holder.tv_video_title.setText("蛤蛤");
        holder.tv_video_subtitle.setText("烟味");
    }

    public class ViewHolder {
        private Object tag;


        private TextView tv_video_title, tv_video_subtitle;
        private ImageView iv_video_background;
        private FrameLayout fl_item_video_list;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            tv_video_title = (TextView) view.findViewById(R.id.tv_video_title_list);
            tv_video_subtitle = (TextView) view.findViewById(R.id.tv_video_subtitle);
/*            iv_video_background = (ImageView) view.findViewById(R.id.iv_video_background);
            fl_item_video_list = (FrameLayout) view.findViewById(R.id.fl_item_video_list);*/
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
