package com.hdcy.app.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.adapter.GiftFragmentAdapter;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.model.GiftContent;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-10-28.
 */

public class MineGiftFragment extends BaseBackFragment {

    private Toolbar mToolbar;
    private TextView title;
    private GiftFragmentAdapter mAdapter;

    private ListView mListView;

    private List<GiftContent> giftContentList = new ArrayList<>();
    private RootListInfo rootListInfo = new RootListInfo();

    private boolean isLast;

    private int pagecount = 0 ;
    public static MineGiftFragment newInstance(){
        Bundle args = new Bundle();
        MineGiftFragment fragment = new MineGiftFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_gift, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView) view.findViewById(R.id.toolbar_title);
        title.setText("礼品兑换");
        initToolbarNav(mToolbar);
        mListView  =(ListView) view.findViewById(R.id.lv_mine_gift);


    }

    private void initData(){
        GetMineGiftList();
    }

    private void setData(){
        mAdapter = new GiftFragmentAdapter(getContext(),giftContentList);
        mListView.setAdapter(mAdapter);
    }

    public void GetMineGiftList(){
        NetHelper.getInstance().GetMineGiftList(pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                if(giftContentList.isEmpty()){
                    List<GiftContent> temp = responseInfo.getGiftContent();
                    giftContentList.addAll(temp);
                }
                Log.e("imageinfo",giftContentList.get(0).getImages().get(0)+"");
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
