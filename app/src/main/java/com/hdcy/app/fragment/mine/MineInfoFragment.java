package com.hdcy.app.fragment.mine;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.model.UserBaseInfo;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.SizeUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

/**
 * Created by WeiYanGeorge on 2016-10-28.
 */

public class MineInfoFragment extends BaseBackFragment {

    private Toolbar mToolbar;
    private TextView title;

    private UserBaseInfo userBaseInfo;

    private ImageView iv_mine_personalinfo_avatar;
    private TextView tv_mine_personalinfo_nickname;
    private TextView tv_mine_personalinfo_name;
    private TextView tv_mine_personalinfo_gender;
    private TextView tv_mine_personalinfo_phone;
    private TextView tv_mine_personalinfo_password;
    private TextView tv_mine_personalinfo_birthday;
    private TextView tv_mine_personalinfo_address;
    private TextView tv_mine_personalinfo_cartype;
    private TextView tv_mine_personalinfo_interests;
    int avatarWidth;
    int avatarHeight;

    public static MineInfoFragment newInstance(UserBaseInfo userBaseInfo){
        Bundle args = new Bundle();
        args.putSerializable("param", userBaseInfo);
        MineInfoFragment fragment = new MineInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_info_detail,container,false);
        userBaseInfo = (UserBaseInfo) getArguments().getSerializable("param");
        Log.e("传递的个人资料",userBaseInfo.getId()+"");
        initView(view);

        avatarHeight = SizeUtils.dpToPx(50);
        avatarWidth = SizeUtils.dpToPx(50);
        return view;
    }

    private void initView(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title =(TextView) view.findViewById(R.id.toolbar_title);
        title.setText("个人资料");
        initToolbarNav(mToolbar);
        iv_mine_personalinfo_avatar = (ImageView) view.findViewById(R.id.iv_personal_info_avatar);
        tv_mine_personalinfo_nickname =(TextView) view.findViewById(R.id.tv_mine_personalinfo_nickname);
        tv_mine_personalinfo_name =(TextView) view.findViewById(R.id.tv_mine_personalinfo_name);
        tv_mine_personalinfo_gender =(TextView) view.findViewById(R.id.tv_mine_personalinfo_gender);
        tv_mine_personalinfo_phone =(TextView) view.findViewById(R.id.tv_mine_personalinfo_phone);
        tv_mine_personalinfo_password =(TextView) view.findViewById(R.id.tv_mine_personalinfo_password);
        tv_mine_personalinfo_birthday=(TextView) view.findViewById(R.id.tv_mine_personalinfo_birthday);
        tv_mine_personalinfo_address =(TextView) view.findViewById(R.id.tv_mine_personalinfo_address);
        tv_mine_personalinfo_cartype =(TextView) view.findViewById(R.id.tv_mine_personalinfo_cartype);
        tv_mine_personalinfo_interests =(TextView) view.findViewById(R.id.tv_mine_personalinfo_interests);
        setData();
    }

    private void setData(){
        if(!userBaseInfo.getHeadimgurl().isEmpty()) {
            Picasso.with(getContext()).load(userBaseInfo.getHeadimgurl())
                    .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                    .resize(50, 50)
                    .centerCrop()
                    .config(Bitmap.Config.RGB_565)
                    .into(iv_mine_personalinfo_avatar);
        }
        tv_mine_personalinfo_nickname.setText(userBaseInfo.getNickname()+"");
        if(userBaseInfo.getSex()=="1"){
            tv_mine_personalinfo_gender.setText("女");
        }else {
            tv_mine_personalinfo_gender.setText("男");
        }
        tv_mine_personalinfo_password.setText("********");
        tv_mine_personalinfo_name.setText(userBaseInfo.getRealname()+"");
        String address = userBaseInfo.getAddress();
        if(BaseUtils.isEmptyString(address)){
            tv_mine_personalinfo_address.setText("");
        }else {
            tv_mine_personalinfo_address.setText(address+"");
        }
        String secretphone = userBaseInfo.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        tv_mine_personalinfo_phone.setText(secretphone+"");

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String date = format.format(userBaseInfo.getBirthday());
        tv_mine_personalinfo_birthday.setText(date);
        tv_mine_personalinfo_cartype.setText(userBaseInfo.getCar());
        tv_mine_personalinfo_interests.setText(userBaseInfo.getTags());
    }
}
