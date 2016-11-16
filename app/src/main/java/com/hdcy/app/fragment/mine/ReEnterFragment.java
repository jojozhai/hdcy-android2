package com.hdcy.app.fragment.mine;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseFragment;
import com.hdcy.app.model.TextBean;
import com.hdcy.base.utils.BgImageViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-11-16.
 */

public class ReEnterFragment extends BaseFragment  {
    private BgImageViewHelper parallelViewHelper;
    private FrameLayout FyView;
    private ImageView point;
    private Context context;

    //图片背景
    private int bgImgId;
    private int arrayId = 0;

    private int[] imgArrayId = {R.drawable.bg_1, R.drawable.bg_2};
    private List<TextBean> textBeanList = new ArrayList<>();
    private List<TextBean> textBeanList2 = new ArrayList<>();

    private SensorManager mSensorManager;
    private boolean isStar = false;//是否开始摇动；
    private boolean isStop = false;//是否结束摇动；

    /**
     * 注册 登录按钮
     */
    Button bt_register;
    Button bt_login;

    /**
     * 跳过按钮
     */
    TextView tv_enter_jump;

    /**
     * 检测的时间间隔
     */
    static final int UPDATE_INTERVAL = 100;
    /**
     * 上一次检测的时间
     */
    long mLastUpdateTime;
    /**
     * 上一次检测时，加速度在x、y、z方向上的分量，用于和当前加速度比较求差。
     */
    float mLastX, mLastY, mLastZ;

    /**
     * 摇晃检测阈值，决定了对摇晃的敏感程度，越小越敏感。
     */
    public int shakeThreshold = 1500;

    public static ReEnterFragment newInstance(){
        Bundle args = new Bundle();
        ReEnterFragment fragment = new ReEnterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_register_login,container,false);

        return view;
    }
}
