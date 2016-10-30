package com.hdcy.app.fragment.third.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hdcy.app.R;
import com.hdcy.app.adapter.OfflineActivityCommentListAdapter;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.fragment.second.PublishCommentFragment;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by WeiYanGeorge on 2016-10-18.
 */

public class OfflineActivityDialogFragment extends BaseBackFragment implements BGARefreshLayout.BGARefreshLayoutDelegate{

    public static final int REQ_PUBLISH_FRAGMENT = 1;
    public static final String KEY_RESULT_ACTIVITY = "activitycomment";

    private BGARefreshLayout mRefreshLayout;
    private RootListInfo rootobjet = new RootListInfo();
    private boolean isLast;

    private Toolbar mToolbar;
    private TextView title;
    private ListView mLisview;

    private List<CommentsContent> commentsContentList = new ArrayList<>();
    private CommentsContent commentsContent =new CommentsContent();

    private int pagecount = 0;

    private String target = "activity";

    private String activityid;

    private OfflineActivityCommentListAdapter mAdapter;

    ImageView iv_activity_fl_bt;

    public static OfflineActivityDialogFragment newInstance(String ActivityId){
        OfflineActivityDialogFragment fragment = new OfflineActivityDialogFragment();
        Bundle args = new Bundle();
        args.putString("param",ActivityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline_comments, container, false);
        activityid = getArguments().getString("param");
        initView(view);
        initData();
        return view;
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if(requestCode == REQ_PUBLISH_FRAGMENT && resultCode == RESULT_OK && data != null){
            commentsContent = JSON.parseObject(data.getString(KEY_RESULT_ACTIVITY),CommentsContent.class);
            commentsContentList.add(0,commentsContent);
            Log.e("commentlistsizeafter",commentsContentList.size()+"");
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        commentsContentList.clear();
        pagecount = 0;
        initData();
        mRefreshLayout.endRefreshing();

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pagecount++;
        Log.e("isLastStatus",isLast+"");
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
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView) view.findViewById(R.id.toolbar_title);
        title.setText("全部留言");
        initToolbarNav(mToolbar);
        mToolbar.inflateMenu(R.menu.hierachy);
        mLisview = (ListView) view.findViewById(R.id.lv_offline_comment);
        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(),true));
        mAdapter = new OfflineActivityCommentListAdapter( getContext(), commentsContentList, "whole");
        mLisview.setAdapter(mAdapter);
        iv_activity_fl_bt = (ImageView) view.findViewById(R.id.iv_edt_button);
        iv_activity_fl_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForResult(PublishCommentFragment.newInstance(activityid, target),REQ_PUBLISH_FRAGMENT );
            }
        });

    }

    private void initData(){
        GetActivityDialogList();
    }

    private void setData(){
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.endLoadingMore();
    }

    private void GetActivityDialogList(){
        NetHelper.getInstance().GetCommentsList(activityid, target, pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                    List<CommentsContent> temp = responseInfo.getCommentsContentList();
                    commentsContentList.addAll(temp);
                    rootobjet = responseInfo.getRootListInfo();
                isLast =rootobjet.isLast();
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
