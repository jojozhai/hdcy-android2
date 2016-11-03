package com.hdcy.app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.hdcy.app.R;
import com.hdcy.app.model.InterestInfo;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import me.yokeyword.indexablerv.IndexableAdapter;

/**
 * Created by WeiYanGeorge on 2016-11-03.
 */

public class InterestAdapter extends BaseAdapter {

    private Context context;
    private List<InterestInfo> data = new ArrayList<>();
    private boolean isChecked;

    private onShowCustomClickListener onShowCustomClickListener;

    public void setOnShowCustomClickListener(onShowCustomClickListener onShowCustomClickListener){
        this.onShowCustomClickListener = onShowCustomClickListener;
    }

    public InterestAdapter(Context context, List<InterestInfo> data){
        super();
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public InterestInfo getItem(int position) {
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
            convertView = View.inflate(context, R.layout.item_interest,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setTag(position);
        setView(position,holder);
        return convertView;
    }

    private void setView(final int position, final ViewHolder holder){
        final InterestInfo item = getItem(position);
        holder.tv_interest_name.setText(item.getName()+"");
        holder.tv_interest_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) holder.getTag();
                if(onShowCustomClickListener != null){
                    onShowCustomClickListener.onShowCustom(position);
                    if(item.is_selected()){
                        holder.tv_interest_name.setBackground(context.getResources().getDrawable(R.drawable.buttonshape));
                    }else {
                        holder.tv_interest_name.setBackground(context.getResources().getDrawable(R.drawable.interestshape));
                    }
                }
            }
        });

    }

    class ViewHolder{
        private Object tag;

        private Button tv_interest_name;

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }

        public ViewHolder (View item){
            ButterKnife.bind(this, item);
            tv_interest_name = (Button) item.findViewById(R.id.bt_interest_name);
        }
    }

    public interface onShowCustomClickListener {
        void onShowCustom(int position);
    }

}
