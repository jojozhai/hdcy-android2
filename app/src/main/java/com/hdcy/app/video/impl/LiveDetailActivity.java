package com.hdcy.app.video.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseFragment;
import com.hdcy.app.fragment.first.FirstTabVideoBreifFragment;
import com.hdcy.app.fragment.first.FirstTabVideoChatFragment;
import com.hdcy.app.model.VideoBasicInfo;
import com.hdcy.app.video.preference.Settings;
import com.hdcy.app.view.MyLiveView;
import com.hdcy.base.utils.SizeUtils;
import com.ucloud.common.logger.L;
import com.ucloud.player.widget.v2.UVideoView;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by WeiYanGeorge on 2016-10-25.
 */

public class LiveDetailActivity extends SupportActivity implements UVideoView.Callback {
    private static final String TAG = "LiveDetailActivity";

    private Context context;

    //private UVideoView mVideoView;
    private MyLiveView mVideoView;

    String rtmpPlayStreamUrl = "http://rtmp3.usmtd.ucloud.com.cn/live/%s.flv";
    String videopath = "http://mediademo.ufile.ucloud.com.cn/ucloud_promo_140s.mp4";

    Settings mSettings ;

    ImageView iv_icon;
    ImageView iv_icon_recover;
    ImageView iv_origin_back;
    ImageView iv_origin_recoverback;
    TextView  tv_live_title;

    private int ratioIndex = 0;

    private int ScreenWidth ;
    private int ScreenHeight ;
    private int OriginHeight;



    private List<BaseFragment> mFragments = new ArrayList<>();

    private TabLayout mTab;
    private ViewPager mViewPager;
    private VideoBasicInfo mBean;
    public static void getInstance(Context context, VideoBasicInfo bean){
        Intent intent = new Intent();
        Settings mSetting = new Settings(context);
        intent.setClass(context, LiveDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle bundles) {
        super.onCreate(bundles);
        setContentView(R.layout.activity_live_detail);
        ScreenWidth = SizeUtils.getScreenWidth();
        ScreenHeight = SizeUtils.getScreenHeight();
        OriginHeight = SizeUtils.dpToPx(200);

        mBean = (VideoBasicInfo) getIntent().getSerializableExtra("bean");
        Toast.makeText(this, ""+mBean.getStreamId(), Toast.LENGTH_SHORT).show();
        initView();
        mSettings = new Settings(this);
        mSettings.setPublishStreamId(mBean.getStreamId());

        mVideoView = (MyLiveView) findViewById(R.id.custom_liveview);
        mVideoView.setPlayType(UVideoView.PlayType.LIVE);
        mVideoView.setPlayMode(UVideoView.PlayMode.NORMAL);
        mVideoView.setRatio(UVideoView.VIDEO_RATIO_FILL_PARENT);
        mVideoView.setDecoder(UVideoView.DECODER_VOD_HW);
        mVideoView.registerCallback(this);
        //mVideoView.setVideoPath(videopath);
        mVideoView.setVideoPath(String.format(rtmpPlayStreamUrl, mSettings.getPusblishStreamId()));

        IntentFilter filter = new IntentFilter();
        filter.setPriority(1000);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStateListener, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//		记得在不需要的时候移除listener，如在activity的onDestroy()时

        if (mVideoView != null) {
            mVideoView.setVolume(0,0);
            mVideoView.stopPlayback();
            mVideoView.release(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mVideoView != null) {
            mVideoView.setVolume(0,0);
            mVideoView.stopPlayback();
            mVideoView.release(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.setVolume(0,0);
            mVideoView.stopPlayback();
            mVideoView.release(true);
        }
    }




    public void close(View view) {
        finish();
    }


    private void initView(){
        mTab = (TabLayout) this.findViewById(R.id.live_tab);
        mViewPager = (ViewPager) this.findViewById(R.id.live_viewPager);
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
        mFragments.add(FirstTabVideoBreifFragment.newInstance(mBean.getDesc()));
        mFragments.add(FirstTabVideoChatFragment.newInstance(mBean.getId()+"", "article"));
        mViewPager.setAdapter(new ViewPageFragmentAdapter(getSupportFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
        tv_live_title = (TextView) this.findViewById(R.id.tv_live_name);
        tv_live_title.setText(mBean.getName());
        iv_icon = (ImageView) this.findViewById(R.id.icon_live);
        iv_icon_recover = (ImageView) this.findViewById(R.id.icon_live_recover);
        iv_origin_back = (ImageView) this.findViewById(R.id.iv_live_originback);
        iv_origin_recoverback =(ImageView) this.findViewById(R.id.iv_live_landscapeback);
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mVideoView.getLayoutParams();
                    lp.height = ScreenWidth;
                    mVideoView.setLayoutParams(lp);
                    iv_icon_recover.setVisibility(View.VISIBLE);
                    iv_icon.setVisibility(View.GONE);
                    return;
                }

            }
        });

        iv_icon_recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mVideoView.getLayoutParams();
                    lp.height = OriginHeight;
                    mVideoView.setLayoutParams(lp);
                    iv_icon_recover.setVisibility(View.GONE);
                    iv_icon.setVisibility(View.VISIBLE);
                    return;
                }
            }
        });

        iv_origin_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                iv_origin_back.setVisibility(View.GONE);
                iv_origin_recoverback.setVisibility(View.VISIBLE);
            }
        });
        iv_origin_recoverback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_origin_back.setVisibility(View.VISIBLE);
                iv_origin_recoverback.setVisibility(View.GONE);
            }
        });
    }

    public void toggleRatio(View view) {
        mVideoView.setRatio(ratioIndex);
        switch (ratioIndex) {
/*            case UVideoView.VIDEO_RATIO_FIT_PARENT:
                Toast.makeText(getApplicationContext(), "VIDEO_RATIO_FIT_PARENT", Toast.LENGTH_SHORT).show();
                break;
            case UVideoView.VIDEO_RATIO_WRAP_CONTENT:
                Toast.makeText(getApplicationContext(), "VIDEO_RATIO_WRAP_CONTENT", Toast.LENGTH_SHORT).show();
                break;
            case UVideoView.VIDEO_RATIO_FILL_PARENT:
                Toast.makeText(getApplicationContext(), "VIDEO_RATIO_FILL_PARENT", Toast.LENGTH_SHORT).show();
                break;*/
            case UVideoView.VIDEO_RATIO_MATCH_PARENT:
                if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }

                Toast.makeText(getApplicationContext(), "VIDEO_RATIO_MATCH_PARENT", Toast.LENGTH_SHORT).show();
                break;
/*            case UVideoView.VIDEO_RATIO_16_9_FIT_PARENT:
                Toast.makeText(getApplicationContext(), "VIDEO_RATIO_16_9_FIT_PARENT", Toast.LENGTH_SHORT).show();
                break;
            case UVideoView.VIDEO_RATIO_4_3_FIT_PARENT:
                Toast.makeText(getApplicationContext(), "VIDEO_RATIO_4_3_FIT_PARENT", Toast.LENGTH_SHORT).show();
                break;*/
        }
        ratioIndex = (++ratioIndex) % 6;
    }


    public class ViewPageFragmentAdapter extends FragmentPagerAdapter{
        public ViewPageFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position ==0 ){
                return FirstTabVideoBreifFragment.newInstance(mBean.getDesc());
            }else {
                return FirstTabVideoChatFragment.newInstance(mBean.getId()+"", "article");
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0){
                return "直播介绍";
            }else {
                return "直播交流";
            }
        }
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
    @Override
    public void onEvent(int what, String message) {
        Log.d(TAG, "what:" + what + ", message:" + message);
        switch (what) {
            case UVideoView.Callback.EVENT_PLAY_START:
                break;
            case UVideoView.Callback.EVENT_PLAY_PAUSE:
                break;
            case UVideoView.Callback.EVENT_PLAY_STOP:
                break;
            case UVideoView.Callback.EVENT_PLAY_COMPLETION:
                Toast.makeText(this, "EVENT_PLAY_COMPLETION", Toast.LENGTH_SHORT).show();
                break;
            case UVideoView.Callback.EVENT_PLAY_DESTORY:
                break;
            case UVideoView.Callback.EVENT_PLAY_ERROR:
                Toast.makeText(this, "EVENT_PLAY_ERROR:" + message, Toast.LENGTH_SHORT).show();
                break;
            case UVideoView.Callback.EVENT_PLAY_RESUME:
                break;
            case UVideoView.Callback.EVENT_PLAY_INFO_BUFFERING_START:
                L.e(TAG, "network block start....");
//              Toast.makeText(VideoActivity.this, "unstable network", Toast.LENGTH_SHORT).show();
                break;
            case UVideoView.Callback.EVENT_PLAY_INFO_BUFFERING_END:
                L.e(TAG, "network block end....");
                break;
        }
    }


}
