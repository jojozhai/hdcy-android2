package com.hdcy.app.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.SizeUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by WeiYanGeorge on 2016-09-19.
 */

public class BootFragment extends BaseBackFragment {

    private  final static int WAIT_TIME = 300; //启动页加载完成等待时间

    private int screenWidth, screenHeight;

    private ImageView iv_boot_bg;


    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){

        }
    };


    public static BootFragment newInstance() {

        Bundle args = new Bundle();

        BootFragment fragment = new BootFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_boot,container,false);
        iv_boot_bg = (ImageView) view.findViewById(R.id.img_boot);
        screenWidth = SizeUtils.getScreenWidth();
        screenHeight = SizeUtils.getScreenHeight();
/*        Glide.with(getContext()).load(R.mipmap.bg_boot)
                .into(iv_boot_bg);*/
        Picasso.with(getContext()).load(R.mipmap.bg_boot)
                .placeholder(BaseInfo.PICASSO_PLACEHOLDER)
                .resize(screenWidth,screenHeight)
                .config(Bitmap.Config.RGB_565)
                .into(iv_boot_bg);

        doFinish();
        return view;
    }

    private void doFinish() {
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                BootFragment.super.onDestroy();
                BootFragment.super.onDestroyView();
            }
        }, WAIT_TIME);
    }
}
