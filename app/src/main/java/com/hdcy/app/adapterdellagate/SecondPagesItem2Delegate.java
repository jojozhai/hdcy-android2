package com.hdcy.app.adapterdellagate;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;

import com.hdcy.app.R;
import com.hdcy.app.model.Content;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.RelativeTimeUtils;
import com.hdcy.base.utils.SizeUtils;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

import java.util.Date;

/**
 * Created by WeiYanGeorge on 2016-10-09.
 */

public class SecondPagesItem2Delegate implements ItemViewDelegate<Content> {

    private Context context;
    private ImageView iv_info_cover;
    int width = SizeUtils.getScreenWidth();
    int height = SizeUtils.dpToPx(190);
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_second_secondpages;
    }
    @Override
    public boolean isForViewType(Content item, int position) {
        if(item.getTop()&&!item.getBusiness()&&item.getDisplayType().equals("MIX")){
            return true;
        }else {
            return false;
        }
        //return item.getTop();
    }

    @Override
    public void convert(ViewHolder holder, Content content, int position) {
        iv_info_cover = (ImageView) holder.getView(R.id.iv_article_bg);
        Date time = content.getCreatedTime();
        int Count = content.getReadCount()+content.getReadCountPlus();
        String nowdate = RelativeTimeUtils.format(time);
        //holder.setText(R.id.tv_created_time, nowdate);
        holder.setText(R.id.tv_article_title,content.getTitle());
        holder.setText(R.id.bt_tag_title,content.getTagInfos().get(0).getName());
        holder.setText(R.id.tv_info_watched,Count+"");
        Picasso.with(context).load(content.getImage())
                .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                .error(BaseInfo.PICASSO_ERROR)
                .resize(width,height)
                .into(iv_info_cover);
    }
}
