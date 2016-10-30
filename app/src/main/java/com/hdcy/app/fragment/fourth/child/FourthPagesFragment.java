package com.hdcy.app.fragment.fourth.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.adapter.FourthPagesAdapter;
import com.hdcy.app.basefragment.BaseFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.fragment.fourth.LeaderDetailInfoFragment;
import com.hdcy.app.fragment.second.SecondFragment;
import com.hdcy.app.fragment.third.OfflineActivityFragment;
import com.hdcy.app.model.LeaderInfo;
import com.hdcy.app.view.NoScrollListView;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;



/**
 * Created by WeiYanGeorge on 2016-10-19.
 */

public class FourthPagesFragment extends BaseFragment {

    private NoScrollListView mListView;
    private List<LeaderInfo> leaderInfoList;
    private FourthPagesAdapter mAdapter;

    private String category;

    public static FourthPagesFragment newInstance(String category){
        Bundle args = new Bundle();
        FourthPagesFragment fragment = new FourthPagesFragment();
        args.putString("param",category);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth_pagers, container,false);
        category = getArguments().getString("param");
        initView(view);
        initData();
        //setListener();
        return view;
    }

    private void initView(View view){
        mListView = (NoScrollListView) view.findViewById(R.id.lv_leaders);
        mListView.setFocusable(false);
    }

    private void initData(){
        if(category == "whole") {
            GetLeaderInfoList();
        }else {
            GetLeaderInfoCategory();
        }
    }

    private void setData(){
        mAdapter = new FourthPagesAdapter(getContext(),leaderInfoList);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                Toast.makeText(getActivity(), "Click:" + position , Toast.LENGTH_SHORT).show();
                //EventBus.getDefault().post(new StartBrotherEvent(OfflineActivityFragment.newInstance("631011")));
                EventBus.getDefault().post(new StartBrotherEvent(LeaderDetailInfoFragment.newInstance(leaderInfoList.get(position))));
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void GetLeaderInfoList(){
        NetHelper.getInstance().GetLeaderInfo(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                leaderInfoList = responseInfo.getLeaderInfo();
                Log.e("leaderinfosize",leaderInfoList.size()+"");
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

    private void GetLeaderInfoCategory(){
        NetHelper.getInstance().GetLeaderInfoCategory(category,new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                leaderInfoList = responseInfo.getLeaderInfo();
                Log.e("leaderinfosize",leaderInfoList.size()+"");
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
