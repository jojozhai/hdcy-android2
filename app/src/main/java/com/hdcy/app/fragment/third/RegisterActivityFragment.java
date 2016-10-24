package com.hdcy.app.fragment.third;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.net.NetHelper;
import com.hdcy.base.utils.net.NetRequestCallBack;
import com.hdcy.base.utils.net.NetRequestInfo;
import com.hdcy.base.utils.net.NetResponseInfo;

/**
 * Created by WeiYanGeorge on 2016-10-21.
 */

public class RegisterActivityFragment extends BaseBackFragment {

    private Toolbar mToolbar;
    private TextView title;
    private EditText edt_comment_content;

    private TextView tv_limit;
    private Button bt_submit;

    private boolean isEdit;
    private String content;
    private String activityid;
    private boolean isRegister;

    public static RegisterActivityFragment newInstance(String activityid){
        RegisterActivityFragment fragment = new RegisterActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param",activityid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_activity, container, false);
        Bundle bundle = getArguments();
        activityid = bundle.getString("param");
        initView(view);
        return view;
    }

    private void resetViewData(){
        int fontcount = 70 - edt_comment_content.length();
        tv_limit.setText(fontcount + "");
    }

    private boolean checkData(){
        content = edt_comment_content.getText().toString();
        if (BaseUtils.isEmptyString(content)||content.trim().isEmpty()) {
            Toast.makeText(getActivity(), "请输入你要发布的文字", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private void initView(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView) view.findViewById(R.id.toolbar_title);
        title.setText("报名");
        initToolbarNav(mToolbar);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEdit = s.length() > 0;
                resetViewData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        tv_limit = (TextView) view.findViewById(R.id.tv_limit);
        edt_comment_content = (EditText) view.findViewById(R.id.edt_comment_content);
        edt_comment_content.addTextChangedListener(textWatcher);
        edt_comment_content.requestFocus();
        bt_submit = (Button) view.findViewById(R.id.bt_rg_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()) {
                    RegisterActivity();
                }
            }
        });
    }
    public void RegisterActivity(){
        NetHelper.getInstance().RegisterOfflineActivity(activityid, content, new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Bundle bundle = new Bundle();
                isRegister = true;
                bundle.putBoolean(OfflineActivityFragment.KEY_RESULT_REGISTER,isRegister);
                setFramgentResult(RESULT_OK, bundle);
                Toast.makeText(getActivity(), "报名成功!", Toast.LENGTH_LONG).show();
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
