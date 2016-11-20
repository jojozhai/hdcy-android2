package com.hdcy.app.fragment.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseLazyMainFragment;
import com.hdcy.app.fragment.first.FirstFragment;
import com.hdcy.app.fragment.second.childpages.SecondPagesFragment;
import com.hdcy.app.fragment.third.ThirdFragment;
import com.hdcy.app.model.NewsCategory;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-10-07.
 */

public class SecondFragment extends BaseLazyMainFragment {
    private TabLayout mTab;
    private ViewPager mViewPager;
    private List<NewsCategory> newsCategoryList = new ArrayList<>();
    private ViewPageFragmentAdapter mAdapter;


    public static SecondFragment newInstance() {
        Bundle args = new Bundle();
        SecondFragment fragment = new SecondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!newsCategoryList.isEmpty()){
            setData();
        }else {
            //initData();
        }
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initLazyData() {
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_pager,container,false);
        initView(view);
        return view;
    }

    private void initData() {
        getNewsCategory();
    }

    private void setData(){
        for(int i = 0 ; i <newsCategoryList.size();i++){
            mTab.addTab(mTab.newTab());
        }
        mAdapter.notifyDataSetChanged();
        mTab.setupWithViewPager(mViewPager);
    }

    private void initView(View view){
        mTab =(TabLayout) view.findViewById(R.id.tab);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(),newsCategoryList);
        mViewPager.setAdapter(mAdapter);
    }

    private void getNewsCategory(){
        NetHelper.getInstance().GetNewsCategoryList(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                NewsCategory newsCategory = new NewsCategory();
                newsCategory.setName("全部");
                newsCategory.setId(1011);
                Log.e("全部id",newsCategory.getId()+"");
                newsCategoryList.add(newsCategory);
                List<NewsCategory> newsCategoryListTemp = responseInfo.getNewsCategoryList();
                newsCategoryList.addAll(newsCategoryListTemp);
                setData();
                //initLazyView(null);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

        });
    }

    public class ViewPageFragmentAdapter extends FragmentPagerAdapter {
        private List<NewsCategory> data;
        public ViewPageFragmentAdapter(FragmentManager fm, List<NewsCategory> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {
            return SecondPagesFragment.newInstance(data.get(position).getId());
            //return FirstPagersFragment.newInstance(data.get(position).getId());
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return data.get(position).getName();
        }


    }









}
