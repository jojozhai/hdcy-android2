package com.hdcy.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.app.model.Replys;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.RelativeTimeUtils;
import com.squareup.picasso.Picasso;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by WeiYanGeorge on 2016-10-16.
 */

public class OfflineActivityCommentListAdapter extends BaseAdapter {

    private List<CommentsContent> data = new ArrayList<>();
    private List<Replys> replysList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private String CommentType;

    private ActivityReplysAdapter replysAdapter;

    public OfflineActivityCommentListAdapter(Context context, List<CommentsContent> data, String CommentType){
        super();
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
        this.CommentType = CommentType;
    }

    @Override
    public int getCount() {
        if(CommentType =="limit") {
            if (data.size() > 5) {
                return 5;
            } else {
                return data.size();
            }
        }else {
            return data.size();
        }
    }

    @Override
    public CommentsContent getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_activity_comments,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setTag(position);
        setView(position, holder);
        return convertView;
    }

    private void setView(final int position, final ViewHolder holder){
        CommentsContent item = getItem(position);
        holder.tv_name.setText(item.getCreaterName()+"");
        Picasso.with(context).load((item.getCreaterHeadimgurl()))
                .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                .resize(50,50)
                .into(holder.iv_avatar);
        holder.tv_comment_content.setText(item.getContent());

/*        if(replysList.isEmpty()){
            holder.ly_sub_replys.setVisibility(View.GONE);
        }else {
            holder.ly_sub_replys.setVisibility(View.VISIBLE);
        }*/

        replysList = item.getReplys();
/*        if(replysList.isEmpty()){
            holder.lv_replys.setVisibility(View.GONE);
        }else {
            holder.lv_replys.setVisibility(View.VISIBLE);
        }*/

        replysAdapter = new ActivityReplysAdapter(context,replysList);
        holder.lv_replys.setTag(replysList);
        holder.lv_replys.setAdapter(replysAdapter);

    }

    public class ViewHolder {
        private ImageView iv_avatar;
        private ImageView iv_praise;
        private TextView tv_name, tv_praise_count, tv_time, tv_comment_content;
        private ListView lv_replys;
        private LinearLayout ly_sub_replys;

        private Object tag;

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }

        public ViewHolder(View itemView) {

            ButterKnife.bind(this, itemView);


            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            ly_sub_replys = (LinearLayout) itemView.findViewById(R.id.ly_sub_reply);
            lv_replys = (ListView) itemView.findViewById(R.id.lv_replys);
            tv_comment_content = (TextView) itemView.findViewById(R.id.tv_comment_content);

        }
    }

}
