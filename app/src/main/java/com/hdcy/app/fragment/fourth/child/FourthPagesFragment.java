package com.hdcy.app.fragment.fourth.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hdcy.app.R;
import com.hdcy.app.adapter.FourthPagesAdapter;
import com.hdcy.app.basefragment.BaseFragment;
import com.hdcy.app.model.LeaderInfo;
import com.hdcy.app.view.NoScrollListView;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-10-19.
 */

public class FourthPagesFragment extends BaseFragment {

    private NoScrollListView mListView;
    private List<LeaderInfo> leaderInfoList;
    private FourthPagesAdapter mAdapter;

    public static FourthPagesFragment newInstance(){
        Bundle args = new Bundle();
        FourthPagesFragment fragment = new FourthPagesFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth_pagers, container,false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view){
        mListView = (NoScrollListView) view.findViewById(R.id.lv_leaders);
        mListView.setFocusable(false);
    }

    private void initData(){
        GetLeaderInfoList();
    }

    private void setData(){
        mAdapter = new FourthPagesAdapter(getContext(),leaderInfoList);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
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


}
