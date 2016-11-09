package com.hdcy.app.activity;

import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.fragment.BootFragment;
import com.hdcy.app.fragment.MainFragment;
import com.hdcy.base.BaseData;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.BaseUtils;
import com.umeng.socialize.PlatformConfig;

import java.io.File;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends SupportActivity implements BaseData{

    private static final String TAG = "MainActivity";

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PlatformConfig.setWeixin("wx6619f92e0cc550da","431c26c014b6ea3c4aab0b1d8016b2b9");
        if(savedInstanceState == null){
            loadRootFragment(R.id.fl_container_activity1,MainFragment.newInstance());
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
    }

    private void readData(){
        String dir = "DICM/Camera";
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
}
