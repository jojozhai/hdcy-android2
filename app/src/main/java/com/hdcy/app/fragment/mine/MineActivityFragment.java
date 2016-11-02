package com.hdcy.app.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.adapter.MineActivityAdapter;
import com.hdcy.app.adapter.ThirdPageFragmentAdapter;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.fragment.third.OfflineActivityFragment;
import com.hdcy.app.model.ActivityContent;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by WeiYanGeorge on 2016-10-30.
 */

public class MineActivityFragment extends BaseBackFragment{

    private Toolbar mToolbar;
    private TextView title;

    private BGARefreshLayout mRefreshLayout;


    private ListView mListView;

    private MineActivityAdapter mAdapter;


    private List<ActivityContent> activityContentList = new ArrayList<>();
    private RootListInfo rootListInfo = new RootListInfo();

    private boolean isLast;

    private int pagecount = 0;

    int bgimgWidth;
    int bgimgHeight;


    public static MineActivityFragment newInstance(){
        Bundle args  = new Bundle();
        MineActivityFragment fragment = new MineActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_activity, container,false);
        initView(view);
        initData();
        setListener();
        return view;
    }

    private void initView(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView) view.findViewById(R.id.toolbar_title);
        title.setText("我的活动");
        initToolbarNav(mToolbar);
        mListView = (ListView) view.findViewById(R.id.lv_mine_activity);
        mAdapter = new MineActivityAdapter(getContext(),activityContentList);
        mListView.setAdapter(mAdapter);

    }

    private void setListener(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ActivityId = activityContentList.get(position).getId() + "";
                EventBus.getDefault().post(new StartBrotherEvent(OfflineActivityFragment.newInstance(ActivityId)));
            }
        });
    }

    private void initData(){
        GetMineActivityList();
    }

    private void setData(){
        mAdapter.notifyDataSetChanged();
    }
    public void GetMineActivityList(){
        NetHelper.getInstance().GetMineActivityList("ACTIVITY", pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                List<ActivityContent> temp = responseInfo.getActivityContentList();
                activityContentList.addAll(temp);
                rootListInfo = responseInfo.getRootListInfo();
                isLast = rootListInfo.isLast();
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
