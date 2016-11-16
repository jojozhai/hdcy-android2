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
import com.hdcy.app.model.LoginResult;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

/**
 * Created by WeiYanGeorge on 2016-11-06.
 */

public class EditPasswordFragment extends BaseBackFragment {
    EditText edt_old_password;
    EditText edt_new_password;
    Button bt_password_submit;
    String oldContent;
    String newContent;
    ImageView iv_back;
    private LoginResult loginResult;


    public static EditPasswordFragment newInstance(){
        Bundle args = new Bundle();
        EditPasswordFragment fragment = new EditPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_password, container, false);
        initView(view);
        setListener();
        return view;
    }

    private void initView(View view){
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        edt_old_password = (EditText) view.findViewById(R.id.edt_old_password);
        edt_new_password = (EditText) view.findViewById(R.id.edt_new_password);
        bt_password_submit = (Button) view.findViewById(R.id.bt_password_submit);
    }

    private boolean checkData(){
        oldContent = edt_old_password.getText().toString();
        newContent = edt_new_password.getText().toString();
        return true;
    }

    private void setListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });

        bt_password_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()){
                    EditPassWord();
                }
            }
        });
    }

    private void EditPassWord(){
        NetHelper.getInstance().EditPersonalPassword(oldContent, newContent, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
/*                loginResult = responseInfo.getLoginResult();
                BaseInfo.clearPp_token();
                BaseInfo.setPp_token(loginResult.getContent());*/
                Toast.makeText(getContext(),"密码修改成功",Toast.LENGTH_SHORT).show();
                _mActivity.onBackPressed();
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(),"密码修改失败,旧密码错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Toast.makeText(getContext(),"密码修改失败,旧密码错误",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
