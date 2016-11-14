package com.hdcy.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hdcy.app.R;
import com.hdcy.app.model.LoginResult;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by WeiYanGeorge on 2016-11-11.
 */

public class ReLoginActivity extends SupportActivity {
    EditText edt_login_phone;
    EditText edt_login_password;
    ImageView iv_back;
    Button bt_login_submit;
    String phone_content;
    String password_content;

    LoginResult loginResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        initView();
        setListener();

    }

    private boolean checkData(){
        phone_content = edt_login_phone.getText().toString();
        password_content = edt_login_password.getText().toString();
        phone_content.trim();
        password_content.trim();
        return true;
    }

    private void initView(){
        edt_login_phone = (EditText) findViewById(R.id.edt_login_phone);
        edt_login_password = (EditText) findViewById(R.id.edt_login_password);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        bt_login_submit = (Button) findViewById(R.id.bt_login_submit);
    }

    private void setListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()){
                    Log.e("passwrod and username",phone_content+","+ password_content);
                    LoginAccount();
                }
            }
        });
    }

    private void LoginAccount(){
        NetHelper.getInstance().LogInAccount(phone_content, password_content, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                loginResult = responseInfo.getLoginResult();
                BaseInfo.setPp_token(loginResult.getContent());
                setResult(RESULT_OK);
                finish();

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
