package com.hdcy.app.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.fragment.register.RegisterFirstFragment;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by WeiYanGeorge on 2016-11-08.
 */

public class LoginActivity extends SupportActivity {
    private static final String TAG = "LoginActivity";

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(savedInstanceState == null){
            loadRootFragment(R.id.fl_container_login_activity, RegisterFirstFragment.newInstance());
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
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }


}
