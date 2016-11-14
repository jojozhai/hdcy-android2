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
import com.hdcy.app.fragment.mine.EditPhoneFragment;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

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

    private void initView(View view){
        edt_reset_phone = (EditText) view.findViewById(R.id.edt_rest_phone);
        edt_reset_smscode = (EditText) view.findViewById(R.id.edt_reset_smscode);
        bt_reset_smscode = (Button) view.findViewById(R.id.bt_reset_smscode);
        bt_reset_submit = (Button) view.findViewById(R.id.bt_reset_submit);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
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
        return true;
    }

    private boolean checkData(){
        phone_content = edt_reset_phone.getText().toString();
        phone_content.trim();
        smscode_content = edt_reset_smscode.getText().toString();
        smscode_content.trim();
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

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    private void CheckPhoneSms(){
        NetHelper.getInstance().SubmitPhoneSmsCode(phone_content, smscode_content, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(),"手机验证成功",Toast.LENGTH_SHORT).show();
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
