package com.hdcy.app.video.impl;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hdcy.app.R;
import com.hdcy.app.adapter.VideoCommentListAdapter;
import com.hdcy.app.basefragment.BaseFragment;
import com.hdcy.app.fragment.first.FirstTabVideoChatFragment;
import com.hdcy.app.fragment.first.FirstVideoCommentFragment;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.app.model.VideoBasicInfo;
import com.hdcy.app.view.MyPlayView;
import com.hdcy.app.view.NoScrollListView;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import fm.jiecao.jcvideoplayer_lib.JCMediaPlayerListener;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
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
    private TextView tv_video_desc;
    private TextView tv_video_comment_count;
    private ListView mListView;

    private BGARefreshLayout mRefreshLayout;
    private boolean isLast;

    private List<BaseFragment> mFragments = new ArrayList<>();
    private List<CommentsContent> commentsList = new ArrayList<>();

    private CommentsContent commentsContent;

    private List<Boolean> praisestatus = new ArrayList<>();

    private VideoCommentListAdapter mAdapter;

    private VideoBasicInfo mBean;

    private MyPlayView jcVideoPlayerStandard;

    private int pagecount = 0;

    private ImageView iv_edt_button;

    private AlertDialog alertDialog;
    TextView tv_comment_submit;
    TextView tv_comment_cancel;
    TextView tv_limit;
    EditText editText;
    private boolean isEdit;
    private String content;
    String replyid;
    int globalposition;
    private RootListInfo rootListInfo = new RootListInfo();


    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){

        }
    };
    public static void getInstance(Context context, VideoBasicInfo bean) {
        Intent intent = new Intent();
//		intent.setAction("com.hdcy.app.uvod.impl.Activity4VedioDetail");
        intent.setClass(context, VideoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", bean.getName());
        if (TextUtils.isEmpty(bean.getUrl2())) {// 这里暂时用  一个默认地址
            bundle.putString("videoPath", "http://mediademo.ufile.ucloud.com.cn/ucloud_promo_140s.mp4");
        } else {
            bundle.putString("videoPath", bean.getUrl2());
        }
        bundle.putSerializable("bean", bean);

        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle bundles) {
        super.onCreate(bundles);
        setContentView(R.layout.activity_video_detail);
        Context context;
        mBean = (VideoBasicInfo) getIntent().getSerializableExtra("bean");
        String intentAction = getIntent().getAction();
        init();

        IntentFilter filter = new IntentFilter();
        filter.setPriority(1000);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStateListener, filter);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        initView();
    }

    private void initView() {
        jcVideoPlayerStandard = (MyPlayView) findViewById(R.id.custom_videoplayer_standard);

        String urlForVideo = "http://mediademo.ufile.ucloud.com.cn/ucloud_promo_140s.mp4";
        if (!TextUtils.isEmpty(mBean.getUrl2())) {
            urlForVideo = mBean.getUrl2();
        }

        jcVideoPlayerStandard.setUp(urlForVideo
                , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, mBean.getName());
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

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initBottomView();
            }
        },1000);
    }
    private void initBottomView(){
/*        tv_video_desc = (TextView) findViewById(R.id.tv_video_desc);
        tv_video_comment_count = (TextView) findViewById(R.id.tv_video_comment_count);
        iv_edt_button = (ImageView) findViewById(R.id.iv_edt_button) ;
        iv_edt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInputDialog();
            }
        });
        if (mBean.getDesc() != null) {
            Document document = Jsoup.parse(mBean.getDesc());
            String htmlcontent = document.select("html").text();
            tv_video_desc.setText(htmlcontent);
        }
        tv_video_comment_count.setText("("+mBean.getCommentCount()+")");*/
        Log.e("videocount",mBean.getCommentCount()+"");
        mFragments.add(FirstVideoCommentFragment.newInstance(mBean.getId()+"","article",mBean.getCommentCount()+"",mBean.getDesc()));
        loadRootFragment(R.id.fl_container_live,mFragments.get(0));
/*        mListView = (ListView) findViewById(R.id.lv_video_dianbo);

        mAdapter = new VideoCommentListAdapter(this,commentsList);
        mListView.setAdapter(mAdapter);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        },1000);*/
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

    private void setData(){
        mAdapter.notifyDataSetChanged();
    }

    public void GetCommentSList(){
        NetHelper.getInstance().GetCommentsList(mBean.getId()+"", "video", pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                List<CommentsContent> temp = responseInfo.getCommentsContentList();
                commentsList.addAll(temp);
                rootListInfo = responseInfo.getRootListInfo();
                isLast = rootListInfo.isLast();
                GetPraiseStatus();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    public void GetPraiseStatus(){
        NetHelper.getInstance().GetCommentPraiseStatus(mBean.getId()+"", "video", pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray jsonArray = responseInfo.getDataArr();
                praisestatus = JSON.parseArray(jsonArray.toString(),Boolean.class);
                for (int i = 0; i < praisestatus.size(); i++){
                    commentsList.get(i+pagecount*10).setLike(praisestatus.get(i));
                }
                setData();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }


    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}
