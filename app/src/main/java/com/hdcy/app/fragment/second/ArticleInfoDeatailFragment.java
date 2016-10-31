package com.hdcy.app.fragment.second;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
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
import com.hdcy.base.MerchantWebView;
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

    MerchantWebView myWebView;
    TextView tv_comment_count;
    TextView title;
    TextView tv_show_more;
    ImageView iv_article_fl_bt;
    ImageView iv_nav_menu_comment;
    ImageView iv_nav_menu_share;

    private String targetId;
    private String Url = URL_BASE +"/new-articleDetails.html?id=";
    private String loadurl;
    private Toolbar mToolbar;

    private String target="article";

    ArticleCommentListAdapter mAdapter;


    private ArticleInfo articleInfo = new ArticleInfo();

    private NoScrollListView lv_article_comment;

    private List<CommentsContent> commentsList = new ArrayList<>();
    private CommentsContent commentsContent = new CommentsContent();
    private List<Boolean> praisestatus = new ArrayList<>();

    private int pagecount = 0;


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
        initView(view);
        initWebview(view);
        initData();
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
                startForResult(PublishCommentFragment.newInstance(targetId+"","article"),REQ_PUBLISH_FRAGMENT);
            }
        });

        tv_comment_count = (TextView) view.findViewById(R.id.tv_comment_count);
        lv_article_comment = (NoScrollListView) view.findViewById(R.id.lv_article_comment);
        tv_show_more =(TextView) view.findViewById(R.id.tv_show_more);
        mAdapter = new ArticleCommentListAdapter(getContext(),commentsList,praisestatus);
        lv_article_comment.setAdapter(mAdapter);
        lv_article_comment.setFocusable(false);

    }

    private void initWebview(View view) {
        myWebView = (MerchantWebView) view.findViewById(R.id.mywebview);
        Log.e("WebUrl", loadurl);
        final WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setDomStorageEnabled(true);
        myWebView.canGoBack();
        myWebView.loadUrl(loadurl);
        myWebView.getContentHeight();
        myWebView.fetchHeight(600);

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
                        .withTargetUrl(loadurl+"&show=YES")
                        .withMedia(new UMImage(getContext(),articleInfo.getImage()))
                        .setListenerList(umShareListener)
                        .open();
                Toast.makeText(getActivity(),   " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
/*        myWebView.clearCache(true);
        myWebView.destroy();
        //myWebView =null;*/
    }

    @Override
    public void onResume() {
        super.onResume();
/*        myWebView.reload();
        myWebView.onResume();
        //myWebView.destroy();*/
    }

    @Override
    public void onPause() {
        super.onPause();
/*        myWebView.reload();
        myWebView.onPause();
        //myWebView.destroy();*/
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
