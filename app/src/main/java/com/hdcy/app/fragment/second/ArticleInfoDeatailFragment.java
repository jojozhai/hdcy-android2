package com.hdcy.app.fragment.second;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hdcy.app.R;
import com.hdcy.app.adapter.ArticleCommentListAdapter;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.fragment.MainFragment;
import com.hdcy.app.model.ArticleInfo;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.app.view.NoScrollListView;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.MerchantWebView;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.bean.Image;

import static com.hdcy.app.fragment.second.ArticleCommentListFragment.KEY_RESULT_COMMENT;
import static com.hdcy.base.BaseData.URL_BASE;

/**
 * Created by WeiYanGeorge on 2016-10-10.
 */

public class ArticleInfoDeatailFragment extends BaseBackFragment {

    private static final int REQ_PUBLISH_FRAGMENT = 1;

    WebView myWebView;
    TextView tv_comment_count;
    TextView title;
    TextView tv_show_more;
    ImageView iv_article_fl_bt;
    ImageView iv_nav_menu_comment;
    ImageView iv_nav_menu_share;

    private String targetId;
    private String Url = URL_BASE +"/new-articleDetails.html?id=";
    private String loadurl;
    private String shareurl = URL_BASE+"/views/articleDetail.html?id=";
    private Toolbar mToolbar;

    private String target="article";

    ArticleCommentListAdapter mAdapter;


    private ArticleInfo articleInfo = new ArticleInfo();

    private NoScrollListView lv_article_comment;

    private List<CommentsContent> commentsList = new ArrayList<>();
    private CommentsContent commentsContent = new CommentsContent();
    private List<Boolean> praisestatus = new ArrayList<>();

    private int pagecount = 0;
    private FrameLayout mProgressBar;



    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){

        }
    };


    public static ArticleInfoDeatailFragment newInstance(String id) {
        ArticleInfoDeatailFragment fragment = new ArticleInfoDeatailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("targetid",id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragnment_articledetail, container, false);
        Bundle bundle = getArguments();
        if (bundle != null){
            targetId = bundle.getString("targetid");
        }
        loadurl = Url + targetId;
        shareurl += targetId;
        initView(view);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                initWebview(view);
            }
        },2000);

        return view;
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode == REQ_PUBLISH_FRAGMENT && resultCode == RESULT_OK && data != null){
            Log.e("commentlistsize",commentsList.size()+"");
            commentsContent = JSON.parseObject(data.getString(KEY_RESULT_COMMENT),CommentsContent.class);
            commentsList.add(0,commentsContent);
            Log.e("commentlistsizeafter",commentsList.size()+"");
            mAdapter.notifyDataSetChanged();
            int i = articleInfo.getCommentCount()+1;
            tv_comment_count.setText("("+i+")");

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(getContext()).onActivityResult(requestCode, resultCode, data);
        com.umeng.socialize.utils.Log.d("result","onActivityResult");
    }


    private void initView(View view){
        mProgressBar = (FrameLayout) view.findViewById(R.id.progress);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView) view.findViewById(R.id.toolbar_title);
        iv_nav_menu_comment = (ImageView) view.findViewById(R.id.iv_nav_menu_comment);
        iv_nav_menu_comment.setVisibility(View.VISIBLE);
        iv_nav_menu_share = (ImageView) view.findViewById(R.id.iv_nav_menu_share);
        iv_nav_menu_share.setVisibility(View.VISIBLE);
        title.setText("资讯详情");
        initToolbarNav(mToolbar);
        //mToolbar.inflateMenu(R.menu.hierachy);
        iv_article_fl_bt = (ImageView) view.findViewById(R.id.iv_article_fl_bt);
        iv_article_fl_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseUtils.isEmptyString(BaseInfo.getPp_token())) {
                    Toast.makeText(getContext(),"登陆后才可以进行评论",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                startForResult(PublishCommentFragment.newInstance(targetId+"","article"),REQ_PUBLISH_FRAGMENT);
                }
            }
        });

        tv_comment_count = (TextView) view.findViewById(R.id.tv_comment_count);
        lv_article_comment = (NoScrollListView) view.findViewById(R.id.lv_article_comment);
        tv_show_more =(TextView) view.findViewById(R.id.tv_show_more);
        mAdapter = new ArticleCommentListAdapter(getContext(),commentsList,praisestatus);
        lv_article_comment.setAdapter(mAdapter);
        lv_article_comment.setFocusable(false);
        initData();
    }

    private void initWebview(View view) {
        myWebView = (WebView) view.findViewById(R.id.mywebview);
        Log.e("WebUrl", loadurl);
        final WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        Log.d("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else{
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.canGoBack();
        myWebView.loadUrl(loadurl);
        mProgressBar.setVisibility(View.GONE);

    }


    private void initData() {
        GetArticleInfo();
    }

    private void setData(){
        tv_comment_count.setText("("+articleInfo.getCommentCount()+")");
        mAdapter.notifyDataSetChanged();
        iv_nav_menu_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(ArticleCommentListFragment.newInstance(articleInfo.getId() + "", "article")));
            }
        });
        tv_show_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(ArticleCommentListFragment.newInstance(articleInfo.getId() + "", "article")));

            }
        });
        iv_nav_menu_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withTitle(articleInfo.getTitle()+"")
                        .withText("好多车友")
                        .withTargetUrl(shareurl)
                        .withMedia(new UMImage(getContext(),articleInfo.getImage()))
                        .setListenerList(umShareListener)
                        .open();
                //Toast.makeText(getActivity(),   " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        });
        if(commentsList.size() == 0){
            tv_show_more.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myWebView.clearCache(true);
        myWebView.removeAllViews();
        myWebView.goBack();
        myWebView.destroy();
        myWebView =null;
    }

    @Override
    public void onResume() {
        super.onResume();
       // myWebView.destroy();
    }

    @Override
    public void onPause() {
        myWebView.reload();
        super.onPause();
    }

    private void GetArticleInfo() {
        NetHelper.getInstance().GetArticleInfo(targetId, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                articleInfo = responseInfo.getArticleInfo();
                GetCommentsList();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    public void GetCommentsList() {
        NetHelper.getInstance().GetCommentsList(targetId,target, pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                if (commentsList.isEmpty()) {
                    List<CommentsContent> commentListFragmentListtemp = responseInfo.getCommentsContentList();
                    commentsList.addAll(commentListFragmentListtemp);
                    Log.e("CommentListsize", commentsList.size() + "");
                }
                GetPraiseStatus();
                //setData();
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
        NetHelper.getInstance().GetCommentPraiseStatus(targetId,target,pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray jsonArray = responseInfo.getDataArr();
                praisestatus = JSON.parseArray(jsonArray.toString(),Boolean.class);
                for(int i = 0; i < praisestatus.size();i++){
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
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            com.umeng.socialize.utils.Log.d("plat","platform"+platform);
            Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(),platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                com.umeng.socialize.utils.Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(),platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };














}
