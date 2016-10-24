package com.hdcy.app.adapter;

import android.content.Context;

import com.hdcy.app.adapterdellagate.FourthPagesItem2Delegate;
import com.hdcy.app.adapterdellagate.FourthPagesItem1Delegate;
import com.hdcy.app.model.LeaderInfo;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-10-19.
 */

public class FourthPagesAdapter extends MultiItemTypeAdapter<LeaderInfo> {

    public FourthPagesAdapter(Context context, List<LeaderInfo> data){
        super(context, data);
        addItemViewDelegate(new FourthPagesItem2Delegate());
        addItemViewDelegate(new FourthPagesItem1Delegate());
    }
}
