package com.hdcy.app.fragment.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.activity.LoginActivity;
import com.hdcy.app.basefragment.BaseLazyMainFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.fragment.register.RegisterFirstFragment;
import com.hdcy.app.model.UserBaseInfo;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by WeiYanGeorge on 2016-10-27.
 */

public class MineFragment extends BaseLazyMainFragment{

    private UserBaseInfo userBaseInfo;

    private LinearLayout ll_mine_info;
    private LinearLayout ll_mine_gift;
    private LinearLayout ll_mine_activity;
    private LinearLayout ll_mine_about_us;
    private LinearLayout ll_mine_exit;
    private ImageView iv_mine_avatar;
    private TextView  tv_mine_level;
    private TextView  tv_mine_credits;
    private TextView  tv_mine_douyou;
    private TextView tv_mine_nickname;

    public static MineFragment newInstance(){
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        GetUserCurrentInfo();
        setListener();
        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    private void initView(View view){
        iv_mine_avatar = (ImageView) view.findViewById(R.id.iv_mine_avatar);
        tv_mine_level = (TextView) view.findViewById(R.id.tv_mine_level);
        tv_mine_credits = (TextView) view.findViewById(R.id.tv_mine_credits);
        tv_mine_douyou = (TextView) view.findViewById(R.id.tv_mine_youdao);
        tv_mine_nickname= (TextView) view.findViewById(R.id.tv_mine_nickname);
        ll_mine_info = (LinearLayout) view.findViewById(R.id.ll_mine_info);
        ll_mine_gift = (LinearLayout) view.findViewById(R.id.ll_mine_gift);
        ll_mine_activity = (LinearLayout) view.findViewById(R.id.ll_mine_activity);
        ll_mine_about_us = (LinearLayout) view.findViewById(R.id.ll_mine_aboutus);
        ll_mine_exit = (LinearLayout) view.findViewById(R.id.ll_mine_exit);
    }

    private void setData(){
        tv_mine_level.setText(userBaseInfo.getLevel());
        tv_mine_credits.setText(userBaseInfo.getPoint()+"");
        tv_mine_douyou.setText(userBaseInfo.getBeans()+"");
        tv_mine_nickname.setText(userBaseInfo.getNickname());
        if(!userBaseInfo.getHeadimgurl().isEmpty()) {
            Picasso.with(getContext()).load(userBaseInfo.getHeadimgurl())
                    .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                    .resize(90,90)
                    .centerCrop()
                    .config(Bitmap.Config.RGB_565)
                    .into(iv_mine_avatar);
        }
    }

    private void setListener(){
        ll_mine_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(MineInfoFragment.newInstance(userBaseInfo)));
            }
        });
        ll_mine_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(MineGiftFragment.newInstance()));
            }
        });
        ll_mine_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(MineActivityFragment.newInstance()));
            }
        });
        ll_mine_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(MineAboutUsFragment.newInstance()));
            }
        });
        ll_mine_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
                //EventBus.getDefault().post(new StartBrotherEvent(RegisterFirstFragment.newInstance()));
            }
        });

    }

    public void GetUserCurrentInfo(){
        NetHelper.getInstance().GetCurrentUserInfo(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                userBaseInfo = responseInfo.getUserBaseInfo();
                Log.e("userBaseInfo",userBaseInfo.getNickname()+"");
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
