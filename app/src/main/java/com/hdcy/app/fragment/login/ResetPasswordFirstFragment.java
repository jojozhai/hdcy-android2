package com.hdcy.app.fragment.login;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.fragment.Message.MessageFragment;
import com.hdcy.app.fragment.mine.EditPhoneFragment;
import com.hdcy.app.fragment.register.RegisterSecondFragment;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.CustomCountDownTimer;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by WeiYanGeorge on 2016-11-14.
 */

public class ResetPasswordFirstFragment extends BaseBackFragment{

    EditText edt_reset_phone;
    EditText edt_reset_smscode;
    String phone_content;
    String smscode_content;
    ImageView iv_back;
    Button bt_reset_smscode;
    Button bt_reset_submit;
    private CustomCountDownTimer countDownTimer;

    public static ResetPasswordFirstFragment newInstance(){
        Bundle args = new Bundle();
        ResetPasswordFirstFragment fragment = new ResetPasswordFirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        initView(view);
        setListener();
        return view;
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode ==7001&& resultCode == RESULT_OK){
            _mActivity.onBackPressed();
        }
    }

    private void initView(View view){
        edt_reset_phone = (EditText) view.findViewById(R.id.edt_rest_phone);
        edt_reset_smscode = (EditText) view.findViewById(R.id.edt_reset_smscode);
        bt_reset_smscode = (Button) view.findViewById(R.id.bt_reset_smscode);
        bt_reset_submit = (Button) view.findViewById(R.id.bt_reset_submit);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        countDownTimer = new CustomCountDownTimer(60*1000,1000,bt_reset_smscode);
    }

    public void setListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
        bt_reset_smscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPhone()) {
                    countDownTimer.start();
                    GetSmsMessage();
                }
            }
        });
        bt_reset_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()){
                    CheckPhoneSms();
                }
            }
        });
    }

    private boolean checkPhone(){
        phone_content = edt_reset_phone.getText().toString();
        if(phone_content.length()<11){
            Toast.makeText(getContext(),"手机格式不对",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkData(){
        phone_content = edt_reset_phone.getText().toString();
        phone_content.trim();
        smscode_content = edt_reset_smscode.getText().toString();
        smscode_content.trim();
        if (phone_content.length()<11&& BaseUtils.isEmptyString(smscode_content)){
            Toast.makeText(getContext(),"您输入的数据有误",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void GetSmsMessage(){
        NetHelper.getInstance().GetPhoneSmsCode(phone_content, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(),"验证码发送成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(),"验证码发送失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(),"验证码发送失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CheckPhoneSms(){
        NetHelper.getInstance().SubmitPhoneSmsCode(phone_content, smscode_content, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(),"手机验证成功",Toast.LENGTH_SHORT).show();
                startForResult(ResetPasswordSecondFragment.newInstance(phone_content),7001);
                //_mActivity.onBackPressed();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(),"手机验证失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(),"手机验证失败",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
