package com.hdcy.app.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.hdcy.app.R;
import com.hdcy.app.adapter.InterestAdapter;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.model.InterestInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-11-03.
 */

public class ChooseInterestsFragment extends BaseBackFragment {

    GridView gv_choose_interest;
    private List<InterestInfo> mDatas = new ArrayList<>();
    InterestAdapter mAdapter;

    public static ChooseInterestsFragment newInstance(){
        Bundle args = new Bundle();
        ChooseInterestsFragment fragment = new ChooseInterestsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_interest, container, false);
        initView(view);
        initDatas();
        return view;
    }

    private void initView(View view){
        gv_choose_interest = (GridView) view.findViewById(R.id.gv_choose_interest);

    }

    private void initDatas(){
        List<InterestInfo> list = new ArrayList<>();
        List<String> namelist = Arrays.asList(getResources().getStringArray(R.array.interest_array));
        for (String item : namelist){
            InterestInfo interestInfo = new InterestInfo();
            interestInfo.setName(item);
            interestInfo.setIs_selected(false);
            list.add(interestInfo);
        }
        mDatas = list;
        Log.e("yanwei",mDatas.size()+"");
        mAdapter = new InterestAdapter(getContext(),mDatas);
        gv_choose_interest.setAdapter(mAdapter);
    }

    private void setListener(){
        mAdapter.setOnShowCustomClickListener(new InterestAdapter.onShowCustomClickListener() {
            @Override
            public void onShowCustom(int position) {
                if()
                int size = 0;
                mDatas.get(position).setIs_selected(true);
            }
        });
    }
}
