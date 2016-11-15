package com.hdcy.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hdcy.app.R;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.DBHelper;

import org.jsoup.Connection;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by WeiYanGeorge on 2016-11-13.
 */

public class BootActivity extends SupportActivity {

    private static final String TAG = "BootActivity";
    LinearLayout boot_ll_layout ;
    private boolean isFirstStart = BaseInfo.getIs_First_start();
    private boolean tokenstatus;

    private final static int WAIT_TIME = 3000;// 启动页加载完成等待时间

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
        }
    };


    private int[] resId = {R.drawable.five_1, R.drawable.five_2, R.drawable.five_3,
            R.drawable.five_4, R.drawable.five_5, R.drawable.five_6,
            R.drawable.five_7, R.drawable.five_8, R.drawable.five_9,
            R.drawable.five_10, R.drawable.five_11, R.drawable.five_12,
            R.drawable.five_13, R.drawable.five_14, R.drawable.five_15,
            R.drawable.five_16, R.drawable.five_17, R.drawable.five_18};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
        initView();
    }

    private void initView(){
        boot_ll_layout = (LinearLayout) findViewById(R.id.view_page_5);
        for (int i = 0; i < ((LinearLayout) boot_ll_layout).getChildCount(); i++) {
            View lyView = ((LinearLayout) boot_ll_layout).getChildAt(i);
//                                if(lyView instanceof LinearLayout){
            for (int k = 0; k < ((LinearLayout) lyView).getChildCount(); k++) {
                ImageView imgView = (ImageView) ((LinearLayout) lyView).getChildAt(k);
                final AlphaAnimation alpha = new AlphaAnimation(0, 1f);
//                                    imgView.setImageResource(R.drawable.splash_content_player);
                alpha.setDuration(80);//设置动画持续时间
                alpha.setFillAfter(true);
                alpha.setFillBefore(false);
                alpha.setStartOffset(240 * (i + 1) + 80 * (k + 1));
                imgView.setScaleType(ImageView.ScaleType.FIT_XY);
                imgView.setImageResource((resId[3 * i + k]));
                imgView.startAnimation(alpha);
            }
        }
        if(!BaseUtils.isEmptyString(BaseInfo.getPp_token())){
            Log.e("tokenstatus","已登录");
            tokenstatus =true;
        }else {
            Log.e("tokenstatus", "未登录" );
            tokenstatus = false;
        }
        //启动SplashActvity
        if (BaseInfo.isFirstStart && !tokenstatus){
            setResult(119);
            doFinish();
        }else if(!BaseInfo.isFirstStart && !tokenstatus){
            //启动注册页面
            setResult(120);
            doFinish();
        }else if(!BaseInfo.isFirstStart && tokenstatus){
            //已登录
            setResult(121);
            doFinish();
        }else{
            doFinish();
        }

    }


    private void doJumpSpalshActivity(){
        Intent intent = new Intent();
        intent.setClass(this, SplashActivity.class);
        startActivity(intent);
    }

    private void doRegisterOrLoginActivity(){
        Intent intent = new Intent();
        intent.setClass(this, RegisterAndLoginActivity.class);
        startActivity(intent);
    }

    private void readData(){

    }

    /**
     * 关闭页面
     */
    private void doFinish() {
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, WAIT_TIME);
    }

    private void doSplashActivity(){
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                setResult(RESULT_OK);
                finish();
            }
        }, WAIT_TIME);
    }

    private void doRegisterActivity(){
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                setResult(119);
                finish();
            }
        }, WAIT_TIME);
    }
}
