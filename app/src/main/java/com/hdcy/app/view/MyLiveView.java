package com.hdcy.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hdcy.app.R;
import com.ucloud.player.widget.v2.UVideoView;

/**
 * Created by WeiYanGeorge on 2016-10-27.
 */

public  class MyLiveView extends UVideoView {
    public View view4Share;

    private Context mContext;

    public MyLiveView (Context context){
        super(context);
        mContext = context;
        init(context);
    }

    public MyLiveView(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
        init(context);
    }

    public void init (Context context){
        //View view = View.inflate(context, R.layout.part_activity_video_play_v2,this);
/*        Log.e("iconclicklive","haha");
        view4Share = this.findViewById(R.id.iv_share);
        view4Share.setVisibility(VISIBLE);
        addView(view);*/
    }


}
