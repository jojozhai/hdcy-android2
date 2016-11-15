package com.hdcy.app.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by WeiYanGeorge on 2016-10-24.
 */

public class FirstTabVideoBreifFragment extends BaseFragment {
    private static final String Params_VideoStr = "Arg_params";

    private String mStr = null;

    private TextView mTvTitle;
    private TextView tv_biref_desc;

    private WebView mWebView;

    public static FirstTabVideoBreifFragment newInstance(String str){
        Bundle args = new Bundle();
        args.putString(Params_VideoStr, str);
        FirstTabVideoBreifFragment fragment = new FirstTabVideoBreifFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStr = getArguments().getString(Params_VideoStr);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_tab_video_brief,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvTitle.setText("视频简介");
        tv_biref_desc = (TextView) view.findViewById(R.id.tv_biref_desc);
        setData();
/*        mWebView= (WebView) view.findViewById(R.id.wv_vedio_desc);
        //mWebView.loadUrl("http://baidu.com");
        mWebView.loadDataWithBaseURL(null, mStr, "text/html", "utf-8", null);
        mWebView.getSettings().setJavaScriptEnabled(true);


        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        //启用支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);*/
    }

    private void setData(){
        Document document = Jsoup.parse(mStr);
        String htmlcontent = document.select("html").text();
        tv_biref_desc.setText(htmlcontent);
    }
}
