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
import com.hdcy.app.model.CommentsContent;
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
    private NoScrollListView mListView;

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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initBottomView();
            }
        },500);



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


    }

    private void initData(){
        GetData();
        GetCommentSList();
    }

    private void initBottomView(){
        tv_video_desc = (TextView) findViewById(R.id.tv_video_desc);
        if (mBean.getDesc() != null) {
            Document document = Jsoup.parse(mBean.getDesc());
            String htmlcontent = document.select("html").text();
            tv_video_desc.setText(htmlcontent);
        }
        mListView = (NoScrollListView) findViewById(R.id.lv_video_dianbo);
        iv_edt_button = (ImageView) findViewById(R.id.iv_edt_button) ;
        iv_edt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInputDialog();
            }
        });
        mAdapter = new VideoCommentListAdapter(this,commentsList, praisestatus);
        mListView.setAdapter(mAdapter);
        initData();
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

    private void GetData() {
        if (mBean == null) {
            return;
        }
        NetHelper.getInstance().getOneVedioDetail(mBean.getId(), new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

                Log.d(TAG, "onSuccess() called with: " + "requestInfo = [" + requestInfo + "], responseInfo = [" + responseInfo + "]");

                //showToast(responseInfo.mBean4VedioDetail.toString());

            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    public void GetCommentSList(){
        NetHelper.getInstance().GetCommentsList(mBean.getId()+"", "article", pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                if(commentsList.isEmpty()){
                    List<CommentsContent> temp = responseInfo.getCommentsContentList();
                    commentsList.addAll(temp);
                }
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
        NetHelper.getInstance().GetCommentPraiseStatus(mBean.getId()+"", "article", pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray jsonArray = responseInfo.getDataArr();
                praisestatus = JSON.parseArray(jsonArray.toString(),Boolean.class);
                for (int i = 0; i < praisestatus.size(); i++){
                    commentsList.get(i).setLike(praisestatus.get(i));
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

    public void PublishComment() {
        NetHelper.getInstance().PublishComments(mBean.getId()+"", content, "article", null, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                alertDialog.dismiss();
                commentsContent = responseInfo.getCommentsContent();
                commentsContent.setLike(false);
                commentsList.add(0, commentsContent);
                setData();
                showToast("发布成功");

            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                showToast("评论发布失败");


            }
        });
    }

    private void ShowInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_dialog, null);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEdit = s.length() > 0;
                resetViewData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        tv_limit = (TextView) view.findViewById(R.id.tv_limit);
        tv_comment_submit = (TextView) view.findViewById(R.id.tv_submit_comment);
        tv_comment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData()) {
                    PublishComment();
                }
            }
        });
        tv_comment_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_comment_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        editText = (EditText) view.findViewById(R.id.edt_comment);
        editText.addTextChangedListener(textWatcher);
        editText.requestFocus();
        builder.setView(view);
        builder.create();
        alertDialog = builder.create();
        Window windowManager = alertDialog.getWindow();
        windowManager.setGravity(Gravity.BOTTOM);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        alertDialog.show();
    }

    private boolean checkData() {
        content = editText.getText().toString();
        if (BaseUtils.isEmptyString(content)||content.trim().isEmpty()) {
            showToast("请输入你要发布的文字");
            return false;
        }
        return true;
    }

    /**
     * 刷新控件数据
     */
    private void resetViewData() {
        int fontcount = 250 - editText.length();
        tv_limit.setText(fontcount + "");
    }



    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}
