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
 * Created by WeiYanGeorge on 2016-08-30.
 */

public class ThirdPageFragmentAdapter extends BaseAdapter {

    private List<ActivityContent> data = new ArrayList<>();
    private Context context;

    private int width;

    private int imgheight;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    //WindowManager wm = (WindowManager) context.get


    public ThirdPageFragmentAdapter(Context context, List<ActivityContent> data){
        super();
        this.context = context;
        this.data = data;
        width = SizeUtils.getScreenWidth();
        imgheight = SizeUtils.dpToPx(200);
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
            convertView = View.inflate(context, R.layout.item_activity_list, null);
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
        holder.tv_activity_title.setText(item.getName()+"");
        SimpleDateFormat foramt = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = item.getStartTime();
        String dateformat1 = "";
        if(!BaseUtils.isEmptyString(startTime.toString())) {
             dateformat1 = foramt.format(startTime);
        }
        String subtitle = item.getAddress() +"/"+ dateformat1;
        Log.e("activitytime",item.getStartTime().toGMTString());
        holder.tv_activity_subtitle.setText(subtitle);
        if(!BaseUtils.isEmptyString(item.getImage())) {
            String cover = item.getImage();
            Picasso.with(context).load(cover)
                    .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                    .resize(width,imgheight)
                    //.centerCrop()
                    .tag(holder.getTag())
                    .config(Bitmap.Config.RGB_565)
                    .into(holder.iv_activity_background);
        }
/*        if(!BaseUtils.isEmptyString(item.getSponsorImage())){
            String sponsor = item.getSponsorImage();
            Picasso.with(context).load(sponsor)
                    .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                    .tag(holder.getTag())
                    .config(Bitmap.Config.RGB_565)
                    .into(holder.iv_activity_sponsor);
        }*/

        if(item.getFinish()==true){
            Log.e("activitystatus",item.getFinish()+"1");
            holder.iv_activity_status.setTag(position);
            holder.iv_activity_status.setVisibility(View.VISIBLE);
        }else {
            holder.iv_activity_status.setVisibility(View.GONE);
        }
        //holder.tv_activity_persons_count.setText(item.getHot()+"");

        holder.fl_item_activity_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag= (int) holder.getTag();
                if(tag == position){
                    if(onItemClickListener !=null){
                        onItemClickListener.onItem(position);
                    }
                }
            }
        });
    }

    public class ViewHolder {
        private Object tag;


        private TextView tv_activity_title, tv_activity_subtitle,tv_activity_persons_count;
        private ImageView iv_activity_background,iv_activity_status,iv_activity_sponsor;
        private FrameLayout fl_item_activity_list;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);

            tv_activity_title = (TextView) view.findViewById(R.id.tv_activity_title);
            tv_activity_subtitle = (TextView) view.findViewById(R.id.tv_activity_subtitle);
            iv_activity_background = (ImageView) view.findViewById(R.id.iv_activity_background);
            iv_activity_status =(ImageView) view.findViewById(R.id.iv_activity_status);
            //tv_activity_persons_count =(TextView) view.findViewById(R.id.tv_activity_persons_count);
            fl_item_activity_list =(FrameLayout) view.findViewById(R.id.fl_item_activity_list);
            //iv_activity_sponsor = (ImageView) view.findViewById(R.id.iv_activity_sponsor);
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

