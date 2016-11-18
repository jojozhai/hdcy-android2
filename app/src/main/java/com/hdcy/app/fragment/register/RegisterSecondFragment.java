package com.hdcy.app.fragment.register;

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
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by WeiYanGeorge on 2016-11-06.
 */

public class RegisterSecondFragment extends BaseBackFragment {
    ImageView iv_back;
    Button bt_rg2_submit;
    Bundle bundle = new Bundle();
    String content_phone;
    String content_password;
    EditText edt_phone;
    EditText edt_password;

    public static RegisterSecondFragment newInstance(Bundle bundle){
        Bundle args = new Bundle();
        args = bundle;
        RegisterSecondFragment fragment = new RegisterSecondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_second,container,false);
        bundle = getArguments();
        initView(view);
        setListener();
        return view;
    }

    private boolean checkData(){
        content_phone = edt_phone.getText().toString();
        content_password = edt_password.getText().toString();
        if(content_password.length()>=8 && content_password.length() <=12 && content_phone.length()==11){
            bundle.putString("register_phone",content_phone);
            bundle.putString("register_password",content_password);
            return true;
        }else {
            Toast.makeText(getContext(), "密码或手机格式不正确",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void initView(View view){
        bt_rg2_submit = (Button) view.findViewById(R.id.bt_rg2_submit);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        edt_phone = (EditText) view.findViewById(R.id.edt_phone);
        edt_password = (EditText) view.findViewById(R.id.edt_password);

    }

    private void setListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
        bt_rg2_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()) {
                    GetSmsMessage();

                }
            }
        });
    }

    private void GetSmsMessage(){
        NetHelper.getInstance().GetPhoneSmsCode(content_phone, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                start(RegisterThirdFragment.newInstance(bundle));
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
