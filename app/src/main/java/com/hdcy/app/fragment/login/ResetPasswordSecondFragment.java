package com.hdcy.app.fragment.login;

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
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

/**
 * Created by WeiYanGeorge on 2016-11-14.
 */

public class ResetPasswordSecondFragment extends BaseBackFragment {

    EditText edt_reset_newpassword;
    Button bt_reset_completed;
    ImageView iv_back;
    String newPassword_content;

    String phone;


    public static  ResetPasswordSecondFragment newInstance(String phone){
        Bundle args = new Bundle();
        args.putString("param",phone);
        ResetPasswordSecondFragment fragment = new ResetPasswordSecondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password_second, container,false);
        initView(view);
        phone = getArguments().getString("param");
        setListener();
        return view;
    }

    private void initView(View view){
        edt_reset_newpassword = (EditText) view.findViewById(R.id.edt_reset_newpassword);
        bt_reset_completed = (Button) view.findViewById(R.id.bt_reset_completed);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
    }

    private boolean checkData(){
        newPassword_content = edt_reset_newpassword.getText().toString();
        newPassword_content.trim();
        if (newPassword_content.length()>=8){
            return true;
        }else {
            return false;
        }
    }

    private void setListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });

        bt_reset_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()){
                    ResetPassword();
                }
            }
        });
    }

    private void ResetPassword(){
        NetHelper.getInstance().ResetPassword(phone, newPassword_content, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(), "修改密码成功",Toast.LENGTH_SHORT).show();
                _mActivity.onBackPressed();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(), "修改密码失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(), "修改密码失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
