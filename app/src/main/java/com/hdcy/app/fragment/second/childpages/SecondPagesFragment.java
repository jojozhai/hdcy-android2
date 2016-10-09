package com.hdcy.app.fragment.second.childpages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hdcy.app.R;
import com.hdcy.app.adapter.SecondPagesAdapter;
import com.hdcy.app.basefragment.BaseFragment;
import com.hdcy.app.model.Content;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-10-08.
 */

public class SecondPagesFragment extends BaseFragment  {

    private ListView mListview;
    private SecondPagesAdapter mAdapter;

    private List<Content> contentList = new ArrayList<>();
    private RootListInfo rootListInfo = new RootListInfo();
    private  boolean isLast;
    private  boolean isFirst;

    private int tagId;

    private int pagecount = 0;

   public static SecondPagesFragment newInstance(int tagId){
       Bundle args = new Bundle();
       args.putInt("param",tagId);
       SecondPagesFragment fragment = new SecondPagesFragment();
       fragment.setArguments(args);
       return fragment;
   }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_pagers_first,container,false);
        //EventBus.getDefault().register(this);
        tagId = getArguments().getInt("param");
        initView(view);
        initData();
        return view;
    }

    private void initView(View view){
        mListview = (ListView) view.findViewById(R.id.recy);
      //  mListview.setAdapter(new SecondPagesItem1Delegate(getContext(),contentList));
    }

    private void initData(){
        if(tagId ==1011){
            getWholeNewsArticleInfo();
        }else {
            getNewsArticleInfo();
        }
    }

    private void setData(){
        mAdapter = new SecondPagesAdapter(getContext(),contentList);
        mListview.setAdapter(mAdapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getWholeNewsArticleInfo(){
        NetHelper.getInstance().GetWholeNewsArticleContent(pagecount,new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                List<Content> contentListtemp = responseInfo.getContentList();
                rootListInfo = responseInfo.getRootListInfo();
                isFirst = rootListInfo.isFirst();
                isLast = rootListInfo.isLast();
                contentList.addAll(contentListtemp);
                Log.e("Articlesize",contentList.size()+"");
                Log.e("ArticleisLast",isLast+""+tagId);
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

    private void getNewsArticleInfo(){
        NetHelper.getInstance().GetNewsArticleContent(pagecount,tagId,new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                List<Content> contentListtemp = responseInfo.getContentList();
                rootListInfo = responseInfo.getRootListInfo();
                isLast = rootListInfo.isLast();
                isFirst = rootListInfo.isFirst();
                contentList.addAll(contentListtemp);
                Log.e("ArticleisLast",isLast+""+tagId);
                Log.e("Articlesize",contentList.size()+"");
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









}
