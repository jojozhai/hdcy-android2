package com.hdcy.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdcy.app.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by WeiYanGeorge on 2016-10-24.
 */

public class MyPlayView extends JCVideoPlayerStandard {

    public View view4Share;


    private Context mContext;

    public View view4Back;

    public TextView tv_video_sponsor;
    public ImageView iv_video_sponsor;


    public MyPlayView(Context context){
        super(context);
        mContext = context;
    }

    public MyPlayView(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.part_activity_video_play_v2;
    }

    @Override
    public void init(Context context) {
        super.init(context);
        Log.d(TAG,"init() called with: "+ "context = [" + context +"]" );
        view4Share = this.findViewById(R.id.iv_share);
        view4Back = this.findViewById(R.id.back);
        tv_video_sponsor = (TextView) this.findViewById(R.id.tv_video_sponsor);
        iv_video_sponsor =(ImageView) this.findViewById(R.id.iv_video_sponsor);
        view4Back.setOnClickListener(this);
        view4Share.setOnClickListener(this);
       // view4Share.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick() called with: " + "v = [" + v + "]");
        int id=v.getId();
        if(id==R.id.iv_share){

            showToast("share");
        }else if(id == R.id.back){
//			showToast("back");
            backPress();
        }else{
            super.onClick(v);
        }
    }

    public void showToast(String content){
        Toast.makeText(mContext,content,Toast.LENGTH_SHORT).show();
    }


}
