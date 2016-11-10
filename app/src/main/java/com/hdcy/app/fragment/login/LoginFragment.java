package com.hdcy.app.fragment.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.model.LoginResult;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

/**
 * Created by WeiYanGeorge on 2016-11-07.
 */

public class LoginFragment extends BaseBackFragment {
    EditText edt_login_phone;
    EditText edt_login_password;
    ImageView iv_back;
    Button bt_login_submit;
    String phone_content;
    String password_content;

    LoginResult loginResult;

    public static LoginFragment newInstance(){
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        initView(view);
        setListener();
        return view;
    }

    private void initView(View view){
        edt_login_phone = (EditText) view.findViewById(R.id.edt_login_phone);
        edt_login_password = (EditText) view.findViewById(R.id.edt_login_password);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        bt_login_submit = (Button) view.findViewById(R.id.bt_login_submit);

    }

    private boolean checkData(){
        phone_content = edt_login_phone.getText().toString();
        password_content = edt_login_password.getText().toString();
        phone_content.trim();
        password_content.trim();
        return true;
    }

    private void setListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
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
                _mActivity.onBackPressed();

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
