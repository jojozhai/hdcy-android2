package com.hdcy.app.adapterdellagate;

import android.content.Context;
import android.widget.ImageView;

import com.hdcy.app.R;
import com.hdcy.app.model.Content;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.SizeUtils;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

/**
 * Created by WeiYanGeorge on 2016-10-08.
 */

public class SecondPagesItem1Delegate implements ItemViewDelegate<Content> {
    private Context context;
    private ImageView iv_info_cover;
    int width = SizeUtils.getScreenWidth();
    int height = SizeUtils.dpToPx(190);

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_first_secondpages;
    }

    @Override
    public boolean isForViewType(Content item, int position) {
        if(item.getTop()&&item.getBusiness()){
            return true;
        }else {
            return false;
        }
    }



    @Override
    public void convert(ViewHolder holder, Content content, int position) {
        iv_info_cover = (ImageView) holder.getView(R.id.iv_article_bg);
        holder.setText(R.id.tv_article_title,content.getTitle());
        Picasso.with(context).load(content.getImage())
                .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                .error(BaseInfo.PICASSO_ERROR)
                .resize(width,height)
                .into(iv_info_cover);
    }
}
