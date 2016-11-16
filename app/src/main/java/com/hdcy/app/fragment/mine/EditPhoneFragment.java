package com.hdcy.app.fragment.mine;

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
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by WeiYanGeorge on 2016-11-05.
 */

public class EditPhoneFragment extends BaseBackFragment{
    EditText edt_phone;
    Button bt_phone_submit;
    String content;
    ImageView iv_back;

    public static EditPhoneFragment newInstance(){
        Bundle args = new Bundle();
        EditPhoneFragment fragment = new EditPhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_phone,container,false);
        initView(view);
        setListener();
        return view;
    }

    public void initView(View view){
        edt_phone = (EditText) view.findViewById(R.id.edt_phone);
        bt_phone_submit = (Button) view.findViewById(R.id.bt_phone_submit);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
    }

    public void setListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
        bt_phone_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()){
                    GetSmsCode();
                    _mActivity.onBackPressed();

                }
            }
        });
    }
    private boolean checkData() {
        content = edt_phone.getText().toString();
        content.trim();
        return true;
    }

    public void GetSmsCode(){
        NetHelper.getInstance().GetPhoneSmsCode(content, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                EventBus.getDefault().post(new StartBrotherEvent(PhoneSmsConfirmFragment.newInstance(content)));
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
