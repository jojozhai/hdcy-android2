package com.hdcy.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.model.Replys;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by WeiYanGeorge on 2016-10-30.
 */

public class ReplysVideoAdapter extends BaseAdapter {
    private Context context;
    private List<Replys> data;
    int itemCount =2 ;

    public OnItemsClickListeners onItemsClickListeners;

    public void setOnItemsClickListeners (OnItemsClickListeners onItemsClickListeners){
        this.onItemsClickListeners =onItemsClickListeners;
    }

    public ReplysVideoAdapter(Context context, List<Replys> data) {
        super();
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        if(data.size()>2){
            return itemCount;
        }else {
            return data.size();
        }
    }

    @Override
    public Replys getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_reply_video, null);
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
        Replys temp = getItem(position);
        String fromUser = temp.getCreaterName()+"";
        String toUser = temp.getReplyToName()+"";
        String content =  temp.getContent();

        holder.tv_from.setText(fromUser);
        holder.tv_to.setText(toUser);
        holder.tv_content.setText(content);

        holder.fy_item_replys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) holder.getTag();
                if (tag ==position){
                    if(onItemsClickListeners !=null){
                        onItemsClickListeners.onFrameLayout(position);
                    }
                }
            }
        });
    }

    public class ViewHolder {
        private Object tag;


        public TextView tv_content ,tv_from, tv_to;
        public LinearLayout fy_item_replys;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            fy_item_replys = (LinearLayout) view.findViewById(R.id.ry_item_reply);
            tv_content =(TextView) view.findViewById(R.id.tv_content);
            tv_from = (TextView) view.findViewById(R.id.tv_from);
            tv_to = (TextView) view.findViewById(R.id.tv_to);
        }

        //@ViewInject(R.id.tv_content)

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }

    }

    public void addItemNum(int number){
        itemCount = number;
        getCount();
    }

    public interface OnItemsClickListeners{
        void onFrameLayout(int position);
    }

    public void OnItemsClickListeners(OnItemsClickListeners onItemsClickListeners) {
        this.onItemsClickListeners = onItemsClickListeners;
    }
}
