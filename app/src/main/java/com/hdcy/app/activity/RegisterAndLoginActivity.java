package com.hdcy.app.activity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.fragment.login.LoginFragment;
import com.hdcy.app.model.TextBean;
import com.hdcy.base.utils.BgImageViewHelper;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by WeiYanGeorge on 2016-11-10.
 */

public class RegisterAndLoginActivity extends SupportActivity implements SensorEventListener{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);
        context = getBaseContext();
        //背景View
        FyView = (FrameLayout) findViewById(R.id.main_image_background);

        //初始化注册 登录按钮
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);
        tv_enter_jump = (TextView) findViewById(R.id.tv_enter_jump);

        //初始化重力感应
        initAccelerometerListener();

        //标注点数据
        setPointData();

        //设置默认背景效果
        initGyroScopeSensor(arrayId, textBeanList);

        setListener();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==9001){
            Log.e("yanwei","hehe");
        }else if(requestCode==9002){
            Log.e("yanwei","heiehi");
        }
    }

    private void setListener(){
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(this,RegisterActivity.class)
                startLogin();
            }
        });
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });

        tv_enter_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startRegister(){

        Intent intent = new Intent(this,RegisterActivity.class);
        startActivityForResult(intent,9001);
        finish();
    }

    private void startLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        //startActivity(intent);
        startActivityForResult(intent,9002);
        finish();
    }

    /**
     * 设置 标注点 数据
     */
    private void setPointData() {
        //添加标注1 请根据不同的图片（arrayId）添加不同的textBeanList；
        TextBean textBean = new TextBean();
        textBean.setText("凌睿300车队"); //文字
        textBean.setFontsize(10);//字号
        textBean.setFontX(1250); //字体的坐标
        textBean.setFontY(1000); //
        textBeanList.add(textBean);
        //添加标注2
        TextBean textBean2 = new TextBean();
        textBean2.setText("上海奥迪国际赛车场");
        textBean2.setFontsize(10);
        textBean2.setFontX(2250); //字体的坐标
        textBean2.setFontY(800);
        textBeanList.add(textBean2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        parallelViewHelper.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        parallelViewHelper.stop();
        System.gc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        parallelViewHelper.stop();
        parallelViewHelper = null;
    }

    private void initAccelerometerListener() {
        mSensorManager = (SensorManager) this
                .getSystemService(Service.SENSOR_SERVICE);
        //加速度传感器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * init背景图片（陀螺仪效果）
     * @param imgId   图片id
     * @param beanList  标注list
     */
    private void initGyroScopeSensor(int imgId, List<TextBean> beanList) {

        bgImgId = imgArrayId[imgId];
        parallelViewHelper = new BgImageViewHelper(this, FyView, bgImgId, beanList);
        parallelViewHelper.start();
    }

    private void StopGyroScopeSensor() {
        if (parallelViewHelper != null) {
            parallelViewHelper.stop();
        } else {
            parallelViewHelper = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        int sensorType = sensorEvent.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = sensorEvent.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            /*正常情况下，任意轴数值最大就在9.8~10之间，只有在突然摇动手机
              的时候，瞬时加速度才会突然增大或减少。   监听任一轴的加速度大于17即可
            */
            long currentTime = System.currentTimeMillis();
            long diffTime = currentTime - mLastUpdateTime;
            if (diffTime < UPDATE_INTERVAL) {
                return;
            }
            mLastUpdateTime = currentTime;
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            float deltaX = x - mLastX;
            float deltaY = y - mLastY;
            float deltaZ = z - mLastZ;
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            float delta = (float) (Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / diffTime * 10000);
            // 当加速度的差值大于指定的阈值，认为这是一个摇晃
            if (delta > shakeThreshold) {
                if (!isStar && !isStop) {
                    isStar = true;
                }
            }
            if (delta < 200) {
                if (isStar) {
                    isStop = true;
                }
            }
            if (isStar && isStop) {
                //do something
                isStar = false;
                isStop = false;
                Log.d("yaodong:a", "--------------------- ");
                StopGyroScopeSensor();
                if (arrayId == 0) {
                    arrayId = 1;
                    initGyroScopeSensor(arrayId, textBeanList2);
                } else {
                    arrayId = 0;
                    initGyroScopeSensor(arrayId, textBeanList);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
