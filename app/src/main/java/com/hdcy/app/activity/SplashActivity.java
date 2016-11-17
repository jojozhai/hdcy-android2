package com.hdcy.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hdcy.app.R;
import com.hdcy.app.parallaxpager.ParallaxContainer;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by WeiYanGeorge on 2016-11-10.
 */

public class SplashActivity extends Activity {
    ParallaxContainer parallaxContainer;
    private ViewGroup group;
    private ImageView[] tips;
    private ImageView imgCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //初始化点点点控件
        group = (ViewGroup) findViewById(R.id.viewGroup);
        imgCar = (ImageView) findViewById(R.id.img_car);

        parallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container);
        if (parallaxContainer != null) {
            parallaxContainer.setLooping(false);
            parallaxContainer.setImageCar(imgCar);
            parallaxContainer.setGrountView(group, this);
            float scale = getResources().getDisplayMetrics().density;
            Log.d("scale:", "scale:"+scale);
            if (scale < 2) {
                parallaxContainer.setupChildren(getLayoutInflater(),
                        R.layout.view_intro_1_h, R.layout.view_intro_2_h,
                        R.layout.view_intro_3_h, R.layout.view_intro_4,
                        R.layout.view_intro_5);
            } else {
                parallaxContainer.setupChildren(getLayoutInflater(),
                        R.layout.view_intro_1, R.layout.view_intro_2,
                        R.layout.view_intro_3, R.layout.view_intro_4,
                        R.layout.view_intro_5);
            }
        }
    }

}
