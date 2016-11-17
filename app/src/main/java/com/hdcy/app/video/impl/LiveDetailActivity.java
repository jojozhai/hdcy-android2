package com.hdcy.app.video.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;
import com.ucloud.common.logger.L;
import com.ucloud.player.widget.v2.UVideoView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import me.yokeyword.fragmentation.SupportActivity;

import static com.hdcy.base.BaseData.URL_BASE;

/**
 * Created by WeiYanGeorge on 2016-10-25.
 */

public class LiveDetailActivity extends SupportActivity implements UVideoView.Callback {
    private static final String TAG = "LiveDetailActivity";


    private Context context;

    private UVideoView mVideoView;

    String rtmpPlayStreamUrl = "http://rtmp3.usmtd.ucloud.com.cn/live/%s.flv";
    String videopath = "http://mediademo.ufile.ucloud.com.cn/ucloud_promo_140s.mp4";

    Settings mSettings ;

    ImageView iv_icon;
    ImageView iv_icon_recover;
    ImageView iv_origin_back;
    ImageView iv_origin_recoverback;
    TextView  tv_live_title;

    //分享按钮
    ImageView iv_live_share;

    private int ratioIndex = 0;

    private int ScreenWidth ;
    private int ScreenHeight ;
    private int OriginHeight;



    private List<BaseFragment> mFragments = new ArrayList<>();

    private TabLayout mTab;
    private ViewPager mViewPager;
    private VideoBasicInfo mBean;
    private VideoBasicInfo DetailBean;

    private static final int MSG_INIT_PLAY = 0;

    private class UiHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  MSG_INIT_PLAY:
                    initView();
                    break;
            }
        }
    }

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){

        }
    };
    private Handler uiHandler  = new UiHandler();

    private String Url = URL_BASE +"/views/livevideo.html?id=";
    private String loadurl;

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
        DetailBean = (VideoBasicInfo) getIntent().getSerializableExtra("bean");
        mBean = (VideoBasicInfo) getIntent().getSerializableExtra("bean");
        loadurl = Url + mBean.getId();
        GetData();
        Toast.makeText(this, ""+mBean.getStreamId(), Toast.LENGTH_SHORT).show();
        //
        mSettings = new Settings(this);
        mSettings.setPublishStreamId(mBean.getStreamId());

        mVideoView = (UVideoView) findViewById(R.id.custom_liveview);
        mVideoView.setPlayType(UVideoView.PlayType.LIVE);
        mVideoView.setPlayMode(UVideoView.PlayMode.NORMAL);
        mVideoView.setRatio(UVideoView.VIDEO_RATIO_FILL_PARENT);
        mVideoView.setDecoder(UVideoView.DECODER_VOD_SW);
        mVideoView.registerCallback(this);
        //mVideoView.setVideoPath(videopath);
        mVideoView.setVideoPath(String.format(rtmpPlayStreamUrl, mSettings.getPusblishStreamId()));
        initView();
        setListener();

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
            //uiHandler.sendEmptyMessage(MSG_INIT_PLAY);
        }
    }




    public void close(View view) {
        finish();
    }


    private void initView(){
        iv_live_share = (ImageView) this.findViewById(R.id.iv_live_share);
        tv_live_title = (TextView) this.findViewById(R.id.tv_live_name);
        tv_live_title.setText(mBean.getName());
        iv_icon = (ImageView) this.findViewById(R.id.icon_live);
        iv_icon_recover = (ImageView) this.findViewById(R.id.icon_live_recover);
        iv_origin_back = (ImageView) this.findViewById(R.id.iv_live_originback);
        iv_origin_recoverback =(ImageView) this.findViewById(R.id.iv_live_landscapeback);
        mTab = (TabLayout) this.findViewById(R.id.live_tab);
        mViewPager = (ViewPager) this.findViewById(R.id.live_viewPager);
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFragments.add(FirstTabVideoBreifFragment.newInstance(mBean.getDesc()+""));
                mFragments.add(FirstTabVideoChatFragment.newInstance(mBean.getId()+"", "article"));
                mViewPager.setAdapter(new ViewPageFragmentAdapter(getSupportFragmentManager()));
                mTab.setupWithViewPager(mViewPager);
            }
        },1000);

    }

/*    private void initBottomView(){
        mTab = (TabLayout) this.findViewById(R.id.live_tab);
        mViewPager = (ViewPager) this.findViewById(R.id.live_viewPager);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mFragments.add(FirstTabVideoBreifFragment.newInstance(mBean.getDesc()));
                mFragments.add(FirstTabVideoChatFragment.newInstance(mBean.getId()+"", "article"));
                mViewPager.setAdapter(new ViewPageFragmentAdapter(getSupportFragmentManager()));
                mTab.setupWithViewPager(mViewPager);
            }
        },500);
    }*/

    private void setListener(){
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

        iv_live_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareAction();
            }
        });
    }

    private void ShareAction(){
        new ShareAction(this).setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                .withTitle(mBean.getName())
                .withText("好多车友")
                .withTargetUrl(loadurl)
                .withMedia(new UMImage(context,mBean.getSponsorImage()))
                .setListenerList(umShareListener)
                .open();
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
                return FirstTabVideoChatFragment.newInstance(mBean.getId()+"", "video");
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

    private void GetData(){
        NetHelper.getInstance().getOneVedioDetail(DetailBean.getId(), new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                mBean = responseInfo.getVideoBasicInfo();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            com.umeng.socialize.utils.Log.d("plat","platform"+platform);
            Toast.makeText(getBaseContext(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getBaseContext(),platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                com.umeng.socialize.utils.Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getBaseContext(),platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };



}
