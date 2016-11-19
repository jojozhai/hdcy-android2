package com.hdcy.app.fragment.second.childpages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.adapter.SecondPagesAdapter;
import com.hdcy.app.basefragment.BaseFragment;
import com.hdcy.app.basefragment.BaseLazyDataFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.fragment.OutLinkWebFragment;
import com.hdcy.app.fragment.second.ArticleInfoDeatailFragment;
import com.hdcy.app.fragment.third.ThirdFragment;
import com.hdcy.app.model.Content;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by WeiYanGeorge on 2016-10-08.
 */

public class SecondPagesFragment extends BaseLazyDataFragment implements BGARefreshLayout.BGARefreshLayoutDelegate{

    private ListView mListview;
    private SecondPagesAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;

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
        return view;
    }

    @Override
    public void initLazyData() {
        initData();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        contentList.clear();
        pagecount = 0;
        initData();
        mRefreshLayout.endRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pagecount++;
        Log.e("isLastStatus",isLast+"");
        Log.e("loadTagid", tagId+"");
        if(isLast){
            mRefreshLayout.endLoadingMore();
            Toast.makeText(getActivity(), "没有更多的数据了", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            initData();
            return true;
        }
    }

    private void initView(View view){
        mListview = (ListView) view.findViewById(R.id.recy);
        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(),true));
        mAdapter = new SecondPagesAdapter(getContext(),contentList);
        mListview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                Toast.makeText(getActivity(), "Click:" + position , Toast.LENGTH_SHORT).show();
                Content temp = contentList.get(position);
                if(temp.isLinkOut()){
                    Toast.makeText(getActivity(), "Click:" + temp.getOutLink() , Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new StartBrotherEvent(OutLinkWebFragment.newInstance(temp.getOutLink()+"","")));
                }else {
                    EventBus.getDefault().post(new StartBrotherEvent(ArticleInfoDeatailFragment.newInstance(contentList.get(position).getId()+"")));
                }

            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void initData(){
        if(tagId ==1011){
            getWholeNewsArticleInfo();
        }else {
            getNewsArticleInfo();
        }
    }

    private void setData(){
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.endLoadingMore();
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
                Log.e("ArticleisLast",pagecount+"");
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
