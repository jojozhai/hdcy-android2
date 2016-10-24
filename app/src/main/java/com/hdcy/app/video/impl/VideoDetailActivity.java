package com.hdcy.app.video.impl;

import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseFragment;
import com.hdcy.app.model.VideoBasicInfo;
import com.hdcy.app.view.MyPlayView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCMediaPlayerListener;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by WeiYanGeorge on 2016-10-23.
 */

public class VideoDetailActivity extends SupportActivity {
    private static final String TAG = "VideoDetailActivity";

    private TabLayout mTab;
    private ViewPager mViewPager;
    private List<BaseFragment> mFragments = new ArrayList<>();

    private VideoBasicInfo mBean;

    private MyPlayView jcVideoPlayerStandard;

    public static void getInstance(Context context, VideoBasicInfo  bean){
        Intent intent=new Intent();
//		intent.setAction("com.hdcy.app.uvod.impl.Activity4VedioDetail");
        intent.setClass(context,VideoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", bean.getName());
        if(TextUtils.isEmpty(bean.getUrl2())){// 这里暂时用  一个默认地址
            bundle.putString("videoPath", "http://mediademo.ufile.ucloud.com.cn/ucloud_promo_140s.mp4");
        }else{
            bundle.putString("videoPath", bean.getUrl2());
        }
        bundle.putSerializable("bean",bean);

        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate( Bundle bundles) {
        super.onCreate(bundles);
        setContentView(R.layout.activity_video_detail);
        mBean = (VideoBasicInfo) getIntent().getSerializableExtra("bean");
        String intentAction = getIntent().getAction();

        IntentFilter filter = new IntentFilter();
        filter.setPriority(1000);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStateListener, filter);
    }

    private void initView(){
        jcVideoPlayerStandard = (MyPlayView) findViewById(R.id.custom_videoplayer_standard);

        String urlForVideo = "http://mediademo.ufile.ucloud.com.cn/ucloud_promo_140s.mp4";
        if(!TextUtils.isEmpty(mBean.getUrl2())){
            urlForVideo = mBean.getUrl2();
        }

        jcVideoPlayerStandard.setUp(urlForVideo
                , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST,mBean.getName());
        ImageLoader.getInstance().displayImage(mBean.getImage(),
                jcVideoPlayerStandard.thumbImageView);
        jcVideoPlayerStandard.backButton.setVisibility(View.VISIBLE);
        jcVideoPlayerStandard.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoDetailActivity.this.finish();
            }
        });
        jcVideoPlayerStandard.tinyBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoDetailActivity.this.finish();
            }
        });
        jcVideoPlayerStandard.view4Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoDetailActivity.this.finish();
            }
        });

        JCVideoPlayerManager.setListener(new JCMediaPlayerListener() {
            @Override
            public void onPrepared() {

            }

            @Override
            public void onCompletion() {

            }

            @Override
            public void onAutoCompletion() {

            }

            @Override
            public void onBufferingUpdate(int percent) {

            }

            @Override
            public void onSeekComplete() {

            }

            @Override
            public void onError(int what, int extra) {

            }

            @Override
            public void onInfo(int what, int extra) {

            }

            @Override
            public void onVideoSizeChanged() {

            }

            @Override
            public void goBackThisListener() {

            }

            @Override
            public boolean goToOtherListener() {



                return false;
            }

            @Override
            public void autoFullscreenLeft() {

            }

            @Override
            public void autoFullscreenRight() {

            }

            @Override
            public void autoQuitFullscreen() {

            }
        });

    }
    private BroadcastReceiver mNetworkStateListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeInfo = manager.getActiveNetworkInfo();
                if (activeInfo == null) {
                    Toast.makeText(context, getString(R.string.error_current_network_disconnected), Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    private void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }


}
