package com.hdcy.app.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.adapter.CommonsAdapter;
import com.hdcy.app.adapter.ViewHolder;
import com.hdcy.app.basefragment.BaseLazyMainFragment;
import com.hdcy.app.model.Bean4VedioBanner;
import com.hdcy.app.model.NewsCategory;
import com.hdcy.app.model.VideoBasicInfo;
import com.hdcy.app.view.ScaleInTransformer;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hdcy.base.utils.DateUtil.date2Str;

/**
 * Created by WeiYanGeorge on 2016-10-07.
 */

public class FirstFragment extends BaseLazyMainFragment{

    private static final String TAG = "FirstFragment";

    private Toolbar mToolbar;
    private TextView title;
    private ListView mListView;

    private ViewPager mViewPager;
    private PagerAdapter mAdapter4Banner;
    private CommonsAdapter mAdapters;


    private CommonsAdapter mAdapter;

    private List<VideoBasicInfo> videoBasicInfoList = new ArrayList<>();
    private List<VideoBasicInfo> videoBannerList = new ArrayList<>();

    private int pagecount = 0;

    public static FirstFragment newInstance() {

        Bundle args = new Bundle();

        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_page,container,false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view){
        mListView = (ListView) view.findViewById(R.id.lv_videolive);
        mAdapter  = new CommonsAdapter<VideoBasicInfo>(getActivity(),videoBasicInfoList,R.layout.item_second_fragment) {
            @Override
            public void convert(ViewHolder holder, VideoBasicInfo item) {
                holder.setText(R.id.tv_video_title, item.getName());
                Date date = new Date(item.getStartTime());
                String dataFormate = date2Str(date,"yyyy-MM-dd / HH:mm");
                String subtitle = "#"+item.getLiveState()+"#";
                if(item.getLiveState()=="未开始"){
                    subtitle = subtitle+ dataFormate;
                }
                holder.setText(R.id.tv_video_desc,subtitle);
                ImageView iv_video_bg = holder.getView(R.id.iv_video_background);
                Picasso.with(getActivity()).load(item.getImage())
                        .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                        .error(BaseInfo.PICASSO_ERROR)
                        .into(iv_video_bg);
            }
        };

        View headView = View.inflate(getContext(),R.layout.item_headview_first,null);
        mViewPager = (ViewPager) headView.findViewById(R.id.id_viewpager);
        initHeaderBanner();
        mListView.addHeaderView(headView);
        mListView.setAdapter(mAdapter);
    }

    private void initData(){
        getBannerDatas();
    }

    private void setData(){
        mAdapter.notifyDataSetChanged();
        mAdapter4Banner.notifyDataSetChanged();
    }

    private void initHeaderBanner(){
        mViewPager.setPageMargin(40);
        mViewPager.setOffscreenPageLimit(3);
        mAdapter4Banner = new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                VideoBasicInfo item = videoBannerList.get(position);
                View view = View.inflate(getActivity(), R.layout.item_video_banner, null);
                container.addView(view);
                TextView tv_video_title = (TextView) view.findViewById(R.id.tv_video_title);
                TextView tv_video_subtitle = (TextView) view.findViewById(R.id.tv_video_desc);
                tv_video_title.setText(item.getName()+"");
                Date date = new Date(item.getStartTime());
                String dataFormate = date2Str(date,"yyyy-MM-dd / HH:mm");
                String subtitle = "#"+item.getLiveState()+"#";
                if(item.getLiveState()=="未开始"){
                    subtitle = subtitle+ dataFormate;
                }
                tv_video_subtitle.setText(subtitle);
                ImageView iv = (ImageView) view.findViewById(R.id.iv_video_background);
                Picasso.with(getActivity()).load(item.getImage())
                        .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                        .error(BaseInfo.PICASSO_ERROR)
                        .into(iv);

                view.setTag(item);
                return view;

            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getCount() {
                return videoBannerList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }
        };
        mViewPager.setAdapter(mAdapter4Banner);
        mViewPager.setPageTransformer(true, new ScaleInTransformer());

    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }
    /** 获取轮播图的数据*/
    private void getBannerDatas(){
        NetHelper.getInstance().GetVedioTopBanner(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                List<VideoBasicInfo> tempList = responseInfo.getVideoBasicInfoList();
                videoBannerList.addAll(tempList);
                Log.e("videobannersize",videoBannerList.size()+"");

                GetVideoBasicInfo();// 初始化banner


            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    private void GetVideoBasicInfo(){
        NetHelper.getInstance().getVedioListDatas(pagecount, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                List<VideoBasicInfo> tempList = responseInfo.getVideoBasicInfoList();
                videoBasicInfoList.addAll(tempList);
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
