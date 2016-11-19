package com.hdcy.app.fragment.fourth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseLazyMainFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.fragment.first.FirstFragment;
import com.hdcy.app.fragment.fourth.child.FourthPagesFragment;
import com.hdcy.app.fragment.second.SecondFragment;
import com.hdcy.app.fragment.second.childpages.SecondPagesFragment;
import com.hdcy.app.model.LeaderContactInfo;
import com.hdcy.app.model.LeaderInfo;
import com.hdcy.app.model.NewsCategory;
import com.hdcy.app.model.TextBean;
import com.hdcy.app.view.CustomViewPager;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import me.nereo.multi_image_selector.bean.Image;

/**
 * Created by WeiYanGeorge on 2016-10-07.
 */

public class FourthFragment extends BaseLazyMainFragment{


    private ImageView iv_leader_fl_bt;
    private TabLayout mTab;
    private CustomViewPager mViewPager;
    private BGABanner leaderBanner;
    private String[] pagetitles = new String[]{"全部","个人","机构"};
    private List<LeaderInfo> leaderBannerInfo = new ArrayList<>();
    private LeaderContactInfo leaderContactEmail = new LeaderContactInfo();
    private LeaderContactInfo leaderContactPhone = new LeaderContactInfo();

    private List<String> imgurls = new ArrayList<>();
    private List<String> tips = new ArrayList<>();

    private AlertDialog alertDialogAsk;
    private AlertDialog alertDialogOrg;

    private TextView tv_leader_orga_apply;
    private TextView tv_leader_person_apply;
    private TextView tv_leader_orga_email;
    private TextView tv_leader_orga_phone;

    public static FourthFragment newInstance() {
        Bundle args = new Bundle();
        FourthFragment fragment = new FourthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        mViewPager.setAdapter(new ViewPageFragmentAdapter(getChildFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    public void initLazyData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth_page,container,false);
        initView(view);
        initData();
        setListener();
        return view;
    }

    private void initView(View view){
        mTab = (TabLayout) view.findViewById(R.id.tab);
        mViewPager = (CustomViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setFocusable(false);
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
        leaderBanner = (BGABanner) view.findViewById(R.id.leader_banners);
        leaderBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                Glide.with(banner.getContext()).load(model).placeholder(R.mipmap.icon_chat_camera).error(R.mipmap.icon_chat_camera).dontAnimate().thumbnail(0.1f).into((ImageView) view);
            }
        });
        iv_leader_fl_bt = (ImageView) view.findViewById(R.id.iv_leader_fl_bt);
        iv_leader_fl_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLeaderApplyforDialog();
            }
        });
        setData();
    }

    private void setListener(){
        leaderBanner.setOnItemClickListener(new BGABanner.OnItemClickListener() {
            @Override
            public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
                EventBus.getDefault().post(new StartBrotherEvent(LeaderDetailInfoFragment.newInstance(leaderBannerInfo.get(position))));

            }
        });
    }

    private void initData(){
        GetLeaderContactInfo("leaderContactMail");
        GetLeaderContactInfo("leaderContactPhone");
        GetLeaderBanner();
    }

    private void setData(){
        mViewPager.setAdapter(new ViewPageFragmentAdapter(getChildFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
    }

    private void ShowLeaderApplyforDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_applyleaders,null);
        tv_leader_orga_apply = (TextView) view.findViewById(R.id.tv_leader_orga_apply);
        tv_leader_orga_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowOrganizationDialog();
            }
        });
        tv_leader_person_apply = (TextView) view.findViewById(R.id.tv_leader_person_apply);
        tv_leader_person_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAsk.dismiss();
                Toast.makeText(getContext(),"您的积分暂无法申请个人大咖",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setView(view);
        builder.create();
        alertDialogAsk = builder.create();
        Window wm = alertDialogAsk.getWindow();
        wm.setGravity(Gravity.CENTER);
        alertDialogAsk.show();
    }

    private void ShowOrganizationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_leader_organization,null);
        builder.setView(view);
        builder.create();
        tv_leader_orga_email = (TextView) view.findViewById(R.id.tv_leader_orga_email);
        tv_leader_orga_phone = (TextView) view.findViewById(R.id.tv_leader_orga_phone);
        tv_leader_orga_phone.setText(leaderContactPhone.getValue()+"");
        tv_leader_orga_email.setText(leaderContactEmail.getValue()+"");
        alertDialogOrg = builder.create();
        Window wm = alertDialogOrg.getWindow();
        wm.setGravity(Gravity.CENTER);
        alertDialogOrg.show();
        alertDialogAsk.dismiss();
    }

    private void setData1(){
        leaderBanner.setData(imgurls,tips);
    }

    private void GetLeaderBanner(){
        if(leaderBannerInfo.isEmpty()) {
            NetHelper.getInstance().GetLeaderBanner(new NetRequestCallBack() {
                @Override
                public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                    leaderBannerInfo = responseInfo.getLeaderInfo();
                    for (int i = 0; i < leaderBannerInfo.size(); i++) {
                        imgurls.add(i, leaderBannerInfo.get(i).getTopImage());
                        tips.add(i, "");
                    }
                    setData1();

                }

                @Override
                public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

                }

                @Override
                public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

                }
            });
        }else {
            imgurls.clear();
            tips.clear();
            for (int i = 0; i < leaderBannerInfo.size(); i++) {
                imgurls.add(i, leaderBannerInfo.get(i).getTopImage());
                tips.add(i, "");
            }
            setData1();
        }
    }

    private void GetLeaderContactInfo(final String contacType){
        NetHelper.getInstance().GetOrganLeaderInfo(contacType, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                if(contacType == "leaderContactMail"){
                    leaderContactEmail = responseInfo.getLeaderContactInfo();
                }else {
                    leaderContactPhone = responseInfo.getLeaderContactInfo();
                }
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
        public ViewPageFragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return FourthPagesFragment.newInstance("whole");
            }else if(position ==1){
                return FourthPagesFragment.newInstance("false");
            }else if(position ==2){
                return FourthPagesFragment.newInstance("true");
            }else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pagetitles[position];
        }
    }


}
