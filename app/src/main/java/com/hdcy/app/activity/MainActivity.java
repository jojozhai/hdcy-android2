package com.hdcy.app.activity;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.fragment.BootFragment;
import com.hdcy.app.fragment.MainFragment;
import com.hdcy.base.BaseData;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.DBHelper;
import com.umeng.socialize.PlatformConfig;

import java.io.File;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

import static javax.net.ssl.SSLEngineResult.Status.OK;

public class MainActivity extends SupportActivity {

    private static final String TAG = "MainActivity";

    private boolean isFirstStart;// 是否第一次启动首页

    private static final int REQUEST_BOOT_ACTIVITY = 301;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isFirstStart = true;
        PlatformConfig.setWeixin("wx6619f92e0cc550da","431c26c014b6ea3c4aab0b1d8016b2b9");
        if(savedInstanceState == null){
            loadRootFragment(R.id.fl_container_activity1,MainFragment.newInstance());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 119 && requestCode == REQUEST_BOOT_ACTIVITY){
            BaseInfo.isFirstStart = DBHelper.putBooleanData(DBHelper.KEY_IS_FIRST_START,false);
            Log.e("开始splashActivity","true");
            doJumpSpalshActivity();

        }else if(resultCode == 120 && requestCode == REQUEST_BOOT_ACTIVITY){
            Log.e("登录注册界面","true");
            doRegisterOrLoginActivity();
        }else {
            Log.e("已登录","true");
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isFirstStart){
            isFirstStart = false;
            Intent intent = new Intent(this,BootActivity.class);
            startActivityForResult(intent,REQUEST_BOOT_ACTIVITY);
        }
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    public  void showToast(String content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        readData();
        Log.e("isFirstStartResume",isFirstStart+"");
    }


    private void readData(){
        String dir = "DICM/Camera";
        BaseInfo.isFirstStart = DBHelper.getBooleanData(DBHelper.KEY_IS_FIRST_START,true);
        Log.e("isFirstStart",BaseInfo.isFirstStart+"");
        BaseInfo.savePath = new File(Environment.getExternalStorageDirectory(), dir);
        if (BaseInfo.savePath == null){
            BaseInfo.savePath = this.getExternalCacheDir();
        }
        if(!BaseInfo.savePath.exists() && BaseInfo.savePath.isDirectory()){
            BaseInfo.savePath.mkdirs();
        }
        if(BaseUtils.isEmptyString(BaseInfo.pp_token)){
            BaseInfo.getPp_token();
        }
    }

    private void doJumpSpalshActivity(){
        Intent intent = new Intent();
        intent.setClass(this, SplashActivity.class);
        startActivity(intent);
    }

    private void doRegisterOrLoginActivity(){
        Intent intent = new Intent();
        intent.setClass(this,RegisterAndLoginActivity.class);
        startActivity(intent);
    }
}
