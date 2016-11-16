package com.hdcy.base.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

public class CustomCountDownTimer extends CountDownTimer {

    private TextView tv;
    private String str;

    private boolean isCountDown = false;

    public CustomCountDownTimer(long millisInFuture, long countDownInterval, TextView tv) {
        super(millisInFuture, countDownInterval);
        this.tv = tv;
        this.str = tv.getText().toString();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        isCountDown = true;
        tv.setText((millisUntilFinished / 1000) + "s");
    }

    @Override
    public void onFinish() {
        tv.setEnabled(true);
        tv.setText(str);
        isCountDown = false;
    }

    /**
     * 开始倒计时
     */
    public void onStart() {
        super.start();
        tv.setEnabled(false);
        isCountDown = true;
    }

    /**
     * 取消倒计时
     */
    public void onCancel() {
        super.cancel();
        onFinish();
    }

    /**
     * 是否正在倒计时
     *
     * @return
     */
    public boolean isCountDown() {
        return isCountDown;
    }

}
