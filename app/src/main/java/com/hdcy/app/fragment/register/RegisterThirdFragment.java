package com.hdcy.app.fragment.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;

/**
 * Created by WeiYanGeorge on 2016-11-06.
 */

public class RegisterThirdFragment extends BaseBackFragment {
    ImageView iv_back;

    public static RegisterThirdFragment newInstance(){
        Bundle args = new Bundle();
        RegisterThirdFragment fragment = new RegisterThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_third, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
    }

    private void setListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
    }
}
