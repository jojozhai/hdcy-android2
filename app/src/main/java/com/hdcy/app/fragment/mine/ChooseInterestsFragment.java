package com.hdcy.app.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.adapter.InterestAdapter;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.model.InterestInfo;
import com.hdcy.app.model.SelectedinterestInfo;

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
    ImageView iv_interest_close;

    private List<SelectedinterestInfo> sElectedSize = new ArrayList<>();

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
        setListener();
        return view;
    }

    private void initView(View view){
        gv_choose_interest = (GridView) view.findViewById(R.id.gv_choose_interest);
        iv_interest_close = (ImageView) view.findViewById(R.id.iv_interest_close);

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
        iv_interest_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sElectedSize.isEmpty()){
                    _mActivity.onBackPressed();
                }else {
                    String content = "";
                   for(int i= 0 ; i < sElectedSize.size();i++ ){
                       content += sElectedSize.get(i).getName();
                       content +=",";
                   }
                    Bundle bundle = new Bundle();
                    bundle.putString(MineInfoFragment.KEY_RESULT_CHOOSE_INTEREST,content);
                    setFramgentResult(RESULT_OK,bundle);
                    _mActivity.onBackPressed();
                }
            }
        });
        mAdapter.setOnShowCustomClickListener(new InterestAdapter.onShowCustomClickListener() {
            @Override
            public void onShowCustom(int position) {
                if(sElectedSize.size()< 4 ) {
                    if(!mDatas.get(position).is_selected()){
                        mDatas.get(position).setIs_selected(true);
                        SelectedinterestInfo item = new SelectedinterestInfo();
                        item.setName(mDatas.get(position).getName());
                        item.setIs_selected(true);
                        item.setPosition(position);
                        sElectedSize.add(item);
                        Log.e("selectedsize",sElectedSize.size()+"");
                        mAdapter.notifyDataSetChanged();
                    }else {
                        mDatas.get(position).setIs_selected(false);
                        for(int i = 0 ;i<sElectedSize.size(); i++){
                            if(sElectedSize.get(i).getPosition() == position){
                                sElectedSize.remove(i);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        Log.e("selectedsize",sElectedSize.size()+"");
                    }

                }else {
                    if( mDatas.get(position).is_selected()){
                        mDatas.get(position).setIs_selected(false);
                        for(int i = 0 ;i<sElectedSize.size(); i++){
                            if(sElectedSize.get(i).getPosition() == position){
                                sElectedSize.remove(i);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        Log.e("selectedsize",sElectedSize.size()+"");
                    }else {
                        Toast.makeText(getContext(), "兴趣最多只能选择四个", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

            }
        });
    }
}
