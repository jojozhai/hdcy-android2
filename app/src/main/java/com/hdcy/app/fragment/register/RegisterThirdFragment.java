package com.hdcy.app.fragment.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.activity.LoginActivity;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.model.LoginResult;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.CustomCountDownTimer;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

/**
 * Created by WeiYanGeorge on 2016-11-06.
 */

public class RegisterThirdFragment extends BaseBackFragment {
    ImageView iv_back;
    Bundle bundle;
    String phone, password, sex, city, nickname;
    String smscode;
    Button bt_rg_submit;
    Button bt_resend_sms;
    LoginResult result;
    EditText edt_smscode;

    private CustomCountDownTimer countDownTimer;

    public static RegisterThirdFragment newInstance(Bundle bundle){
        Bundle args = new Bundle();
        args = bundle;
        RegisterThirdFragment fragment = new RegisterThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_third, container, false);
        bundle = getArguments();
        String content = bundle.getString("register_city")+ bundle.getString("register_phone");
        Log.e("reigisterthird", content);
        initView(view);
        setListener();
        return view;
    }

    private boolean checkData(){
        smscode = edt_smscode.getText().toString();
        setData();
        return true;
    }

    private void initView(View view){
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        bt_rg_submit = (Button) view.findViewById(R.id.bt_phone_submit);
        edt_smscode = (EditText) view.findViewById(R.id.edt_rg_smscode);
        bt_resend_sms = (Button) view.findViewById(R.id.bt_resend_sms);
        countDownTimer = new CustomCountDownTimer(60*1000,1000,bt_resend_sms);
    }

    private void setListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.finish();
            }
        });
        bt_rg_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()) {
                    CheckPhoneSms();
                }
            }
        });
        bt_resend_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.onStart();
                Log.e("点击一次","又一次");
                GetSmsMessage();
            }
        });
    }

    private void setData(){
        phone = bundle.getString("register_phone");
        password = bundle.getString("register_password");
        sex = bundle.getString("register_sex");
        city = bundle.getString("register_city");
        nickname = bundle.getString("register_nickname");
    }

    private void CheckPhoneSms(){
        NetHelper.getInstance().SubmitPhoneSmsCode(phone, smscode, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                SubmitRegisterInfo();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getActivity(),"验证码校验失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getActivity(),"验证码校验失败",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void SubmitRegisterInfo(){
        NetHelper.getInstance().RegisterAccount(phone, password, sex, city, nickname, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                result = responseInfo.getLoginResult();
                Toast.makeText(getContext(),result.getContent(),Toast.LENGTH_SHORT).show();
/*                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
                _mActivity.onBackPressed();*/
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                result = responseInfo.getLoginResult();
                Toast.makeText(getContext(),result.getContent(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                result = responseInfo.getLoginResult();
                Toast.makeText(getContext(),result.getContent(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetSmsMessage(){
        NetHelper.getInstance().GetPhoneSmsCode(phone, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }
}
