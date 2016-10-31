package com.hdcy.app.fragment.fourth;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.model.LeaderInfo;
import com.hdcy.base.BaseInfo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import me.nereo.multi_image_selector.bean.Image;

/**
 * Created by WeiYanGeorge on 2016-10-26.
 */

public class LeaderDetailInfoFragment extends BaseBackFragment {

    private Toolbar mToolbar;
    private TextView title;

    private String LeaderId;

    private LeaderInfo leaderInfo;

    private ImageView iv_leader_avatar;
    private TextView tv_leader_name;
    private TextView tv_leader_desc;
    private TextView tv_leader_info;

    public static LeaderDetailInfoFragment newInstance(LeaderInfo leaderInfo){
        LeaderDetailInfoFragment fragment = new LeaderDetailInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable("param", leaderInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leader_detail, container, false);
        leaderInfo = (LeaderInfo) getArguments().getSerializable("param");
        Log.e("leaderinfo",leaderInfo.getImage());
        initView(view);

        return view;
    }

    private void initView(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView) view.findViewById(R.id.toolbar_title);
        title.setText("大咖详情");
        initToolbarNav(mToolbar);
        mToolbar.inflateMenu(R.menu.hierachy);
        iv_leader_avatar = (ImageView) view.findViewById(R.id.iv_leader_top_avatar);
        tv_leader_name = (TextView) view.findViewById(R.id.tv_leader_name);
        tv_leader_desc = (TextView) view.findViewById(R.id.tv_leader_desc);
        tv_leader_info = (TextView) view.findViewById(R.id.tv_leader_info);
        setData();
    }

    private void setData(){
/*        Picasso.with(getContext()).load(leaderInfo.getImage()+"")
                .into(iv_leader_avatar);*/
        if(!TextUtils.isEmpty(leaderInfo.getImage())) {
            Picasso.with(getActivity()).load((leaderInfo.getImage()))
                    .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                    .resize(50,50)
                    .into(iv_leader_avatar);
        }
        tv_leader_name.setText(leaderInfo.getName());
        tv_leader_info.setText(leaderInfo.getIntro());
        tv_leader_desc.setText(leaderInfo.getSlogan());
    }

}
