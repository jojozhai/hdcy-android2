package com.hdcy.app.fragment.third.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.adapter.OfflineActivityCommentListAdapter;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-10-18.
 */

public class OfflineActivityDialogFragment extends BaseBackFragment{

    private Toolbar mToolbar;
    private TextView title;
    private ListView mLisview;

    private List<CommentsContent> commentsContentList = new ArrayList<>();

    private int pagecount = 0;

    private String target = "activity";

    private String activityid;

    private OfflineActivityCommentListAdapter mAdapter;

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

    private void initView(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView) view.findViewById(R.id.toolbar_title);
        title.setText("全部留言");
        initToolbarNav(mToolbar);
        mToolbar.inflateMenu(R.menu.hierachy);
        mLisview = (ListView) view.findViewById(R.id.lv_offline_comment);
        mAdapter = new OfflineActivityCommentListAdapter( getContext(), commentsContentList, "whole");
        mLisview.setAdapter(mAdapter);

    }

    private void initData(){
        GetActivityDialogList();
    }

    private void setData(){
        mAdapter.notifyDataSetChanged();
    }

    private void GetActivityDialogList(){
        NetHelper.getInstance().GetCommentsList(activityid, target, pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                if(commentsContentList.isEmpty()){
                    List<CommentsContent> temp = responseInfo.getCommentsContentList();
                    commentsContentList.addAll(temp);
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
}
