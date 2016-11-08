package com.hdcy.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.base.MerchantWebView;
import com.hdcy.base.utils.LocalActivityMgr;

import static com.hdcy.base.BaseData.URL_BASE;

/**
 * Created by WeiYanGeorge on 2016-10-17.
 */

public class OutLinkWebFragment extends BaseBackFragment{

    WebView myWebView;
    private TextView title;

    private String targetId;
    private String Url = URL_BASE +"/articleDetails.html?id=";
    private String loadurl;
    private Toolbar mToolbar;
    private String  mtitle;


    public static OutLinkWebFragment newInstance(String url, String title){
        OutLinkWebFragment fragment = new OutLinkWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragnment_outlink, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            loadurl = bundle.getString("url");
            mtitle = bundle.getString("title");
        }
        initView(view);
        initWebView(view);
        return view;
    }

    private void initView(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView) view.findViewById(R.id.toolbar_title);
        title.setText(mtitle);
        initToolbarNav(mToolbar);
    }

    private void initWebView(View view){
        myWebView = (WebView) view.findViewById(R.id.mywebview);
        Log.e("WebUrl", loadurl);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setDomStorageEnabled(true);
        myWebView.canGoBack();
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.loadUrl(loadurl);
    }
}
