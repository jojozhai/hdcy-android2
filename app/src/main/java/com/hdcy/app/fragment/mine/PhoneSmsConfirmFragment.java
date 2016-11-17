package com.hdcy.app.fragment.mine;

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
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

/**
 * Created by WeiYanGeorge on 2016-11-05.
 */

public class PhoneSmsConfirmFragment extends BaseBackFragment{
    EditText edt_phone;
    EditText edt_sms;
    Button bt_sms_submit;
    String phone_content;
    String sms_content;
    ImageView iv_back;


    public static PhoneSmsConfirmFragment newInstance(String phone){
        Bundle args = new Bundle();
        args.putString("param", phone);
        PhoneSmsConfirmFragment fragment = new PhoneSmsConfirmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_confirm, container, false);
        phone_content = getArguments().getString("param");
        initView(view);
        setListener();
        return view;
    }

    private boolean checkData() {
        phone_content = edt_phone.getText().toString();
        sms_content = edt_sms.getText().toString();
        phone_content.trim();
        sms_content.trim();
        if(BaseUtils.isEmptyString(phone_content)||BaseUtils.isEmptyString(sms_content)){
            Toast.makeText(getContext(),"手机和验证码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private void initView(View view){
        edt_phone = (EditText) view.findViewById(R.id.edt_phone);
        edt_sms = (EditText) view.findViewById(R.id.edt_sms);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        bt_sms_submit = (Button) view.findViewById(R.id.bt_phone_submit);
        edt_phone.setText(phone_content+"");
    }

    private void setListener(){
        bt_sms_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()) {
                    SubmitSmsCode();
                    //_mActivity.onBackPressed();
                }
            }
        });
    }
    private void SubmitSmsCode(){
        NetHelper.getInstance().SubmitPhoneSmsCode(phone_content, sms_content, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(),"手机验证成功",Toast.LENGTH_SHORT).show();
                SubmitNewPhone();
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
    private void SubmitNewPhone(){
        NetHelper.getInstance().EditMineInfomation("mobile", phone_content, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getActivity(), "修改成功!", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                setFramgentResult(RESULT_OK,bundle);
                _mActivity.onBackPressed();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getActivity(), "修改失败!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getActivity(), "修改失败!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
