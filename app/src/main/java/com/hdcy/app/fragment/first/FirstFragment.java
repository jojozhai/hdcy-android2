package com.hdcy.app.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hdcy.app.R;
import com.hdcy.app.adapter.CommonsAdapter;
import com.hdcy.app.adapter.ViewHolder;
import com.hdcy.app.basefragment.BaseLazyMainFragment;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.app.model.VideoBasicInfo;
import com.hdcy.app.video.impl.LiveDetailActivity;
import com.hdcy.app.video.impl.VideoDetailActivity;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static com.hdcy.base.utils.DateUtil.date2Str;

/**
 * Created by WeiYanGeorge on 2016-10-07.
 */

public class FirstFragment extends BaseLazyMainFragment implements BGARefreshLayout.BGARefreshLayoutDelegate{

    private static final String TAG = "FirstFragment";

    private ListView mListView;
    private BGARefreshLayout mRefreshLayout;

    private BGABanner mBanner;
    private PagerAdapter mAdapter4Banner;
    private CommonsAdapter mAdapters;


    private CommonsAdapter mAdapter;

    private List<VideoBasicInfo> videoBasicInfoList = new ArrayList<>();
    private List<VideoBasicInfo> videoBannerList = new ArrayList<>();
    private RootListInfo rootListInfo = new RootListInfo();
    private boolean isLast;

    private List<String> imgurls = new ArrayList<>();
    private List<String> tips = new ArrayList<>();

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
        GetBannerDatas();
        initData();
        setListener();
        return view;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        videoBasicInfoList.clear();
        pagecount = 0;
        initData();
        mRefreshLayout.endRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pagecount++;
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
        mListView = (ListView) view.findViewById(R.id.lv_videolive);
        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(),true));
        mAdapter  = new CommonsAdapter<VideoBasicInfo>(getActivity(),videoBasicInfoList,R.layout.item_video_list) {
            @Override
            public void convert(ViewHolder holder, VideoBasicInfo item) {
                holder.setText(R.id.tv_video_title_list, item.getName());
                Date date = item.getStartTime();
                String dataFormate = date2Str(date,"yyyy-MM-dd / HH:mm");
                String subtitle = "";
                if(item.getLiveState().equals("回放")) {
                     subtitle = "#" + "视频" + "#";
                }else {
                     subtitle = "#" + item.getLiveState() + "#";
                }
                if(item.getLiveState()=="未开始"){
                    subtitle = subtitle+ dataFormate;
                }
                holder.setText(R.id.tv_video_subtitle,subtitle);
                ImageView iv_video_bg = holder.getView(R.id.iv_video_background);
                Picasso.with(getActivity()).load(item.getImage())
                        .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                        .error(BaseInfo.PICASSO_ERROR)
                        .into(iv_video_bg);
            }
        };
        View headView = View.inflate(getContext(),R.layout.item_headview_first,null);
        mBanner = (BGABanner) headView.findViewById(R.id.banner);

        mListView.addHeaderView(headView);
        mBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                Glide.with(banner.getContext()).load(model).placeholder(R.mipmap.icon_chat_camera).error(R.mipmap.icon_chat_camera).dontAnimate().thumbnail(0.1f).into((ImageView) view);
            }
        });
        mListView.setAdapter(mAdapter);


    }

    private void initData(){
        GetVideoBasicInfo();
        //GetBannerDatas();
    }

    private void setData(){
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.endLoadingMore();
    }

    private void setData1(){
        mBanner.setData(imgurls,tips);
    }

    private void goToOneDetail(VideoBasicInfo bean){
/*        if (!SystemUtil.isNetworkConnected(getActivity())){
            Toast.makeText(getActivity(),"当前网络不可用",Toast.LENGTH_SHORT).show();
        }
        switch (SystemUtil.getConnectedType(getActivity())) {
            case ConnectivityManager.TYPE_MOBILE:
//				Toast.makeText(getActivity(), "当前网络: mobile", Toast.LENGTH_SHORT).show();
                break;
            case ConnectivityManager.TYPE_ETHERNET:
//				Toast.makeText(getActivity(), "当前网络: ehternet", Toast.LENGTH_SHORT).show();
                break;
            case ConnectivityManager.TYPE_WIFI:
//				Toast.makeText(getActivity(), "当前网络: wifi", Toast.LENGTH_SHORT).show();
                break;
        }*/

        if(bean !=null ){
            if(bean.getLive()){
                LiveDetailActivity.getInstance(getActivity(),bean);
                return;
            }else {
                VideoDetailActivity.getInstance(getActivity(),bean);
            }
        }
    }

    private void setListener(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int acutualposition = position -1;
                goToOneDetail(videoBasicInfoList.get(acutualposition));
                Toast.makeText(getActivity(), acutualposition+"" , Toast.LENGTH_SHORT).show();
            }
        });
        mBanner.setOnItemClickListener(new BGABanner.OnItemClickListener() {
            @Override
            public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
                //Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
                goToOneDetail(videoBannerList.get(position));
            }
        });
    }

    private void initHeaderBanner(){
/*        mViewPager.setPageMargin(40);
        mViewPager.setOffscreenPageLimit(3);*/
        mAdapter4Banner = new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                VideoBasicInfo item = videoBannerList.get(position);
                View view = View.inflate(getActivity(), R.layout.item_video_banner, null);
                container.addView(view);
                TextView tv_video_title = (TextView) view.findViewById(R.id.tv_video_title);
                TextView tv_video_subtitle = (TextView) view.findViewById(R.id.tv_video_subtitle);
                tv_video_title.setText(item.getName()+"");
                Date date = item.getStartTime();
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

    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }
    /** 获取轮播图的数据*/
    private void GetBannerDatas(){
        NetHelper.getInstance().GetVedioTopBanner(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                if(videoBannerList.isEmpty()) {
                    List<VideoBasicInfo> tempList = responseInfo.getVideoBasicInfoList();
                    videoBannerList.addAll(tempList);
                    Log.e("videobannersize", videoBannerList.size() + "");
                    for (int i = 0; i < videoBannerList.size(); i++) {
                        imgurls.add(i, videoBannerList.get(i).getImage());
                        tips.add(i, videoBannerList.get(i).getName());
                    }
                }
                Log.e("imgurlssize", imgurls.size() + "");
                Log.e("tipssize", tips.size() + "");
                setData1();

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
