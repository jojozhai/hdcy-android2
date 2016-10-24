package com.hdcy.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hdcy.app.adapterdellagate.SecondPagesItem1Delegate;
import com.hdcy.app.adapterdellagate.SecondPagesItem2Delegate;
import com.hdcy.app.adapterdellagate.SecondPagesItem3Delegate;
import com.hdcy.app.adapterdellagate.SecondPagesItem4Delegate;
import com.hdcy.app.model.Content;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.ViewHolder;


import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-10-08.
 */

public class SecondPagesAdapter extends MultiItemTypeAdapter<Content> {


    public SecondPagesAdapter(Context context, List<Content> data){
        super(context, data);
        addItemViewDelegate(new SecondPagesItem1Delegate());
        addItemViewDelegate(new SecondPagesItem2Delegate());
        addItemViewDelegate(new SecondPagesItem3Delegate());
        addItemViewDelegate(new SecondPagesItem4Delegate());
    }

}
