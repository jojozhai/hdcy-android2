package com.hdcy.app.adapterdellagate;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.hdcy.app.R;
import com.hdcy.app.model.LeaderInfo;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.SizeUtils;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

/**
 * Created by WeiYanGeorge on 2016-10-19.
 */

public class FourthPagesItem1Delegate implements ItemViewDelegate<LeaderInfo>{
    private Context context;
    private ImageView iv_leader_cover;
    int width = SizeUtils.getScreenWidth();
    int height = SizeUtils.dpToPx(100);

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_first_fourthpages;
    }

    @Override
    public boolean isForViewType(LeaderInfo item, int position) {
        if(position % 2 ==0){
            return true;
        }else {
            return false;
        }
/*        if(item.getTop()){
            return true;
        }else {
            return false;
        }*/
    }

    @Override
    public void convert(ViewHolder holder, LeaderInfo leaderInfo, int position) {
        iv_leader_cover = (ImageView) holder.getView(R.id.iv_leader_cover);
/*        ViewGroup.LayoutParams params = iv_leader_cover.getLayoutParams();
        params.width = width/2;
        params.height = height;
        iv_leader_cover.setLayoutParams(params);*/
        Picasso.with(context).load(leaderInfo.getImage())
                .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                .error(BaseInfo.PICASSO_ERROR)
                .resize(width/2,height)
                .into(iv_leader_cover);
        holder.setText(R.id.tv_leader_title,leaderInfo.getName());
        holder.setText(R.id.tv_leader_subtitle, leaderInfo.getSlogan());
    }
}
