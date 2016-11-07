package com.hdcy.app.fragment.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.event.StartBrotherEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by WeiYanGeorge on 2016-11-06.
 */

public class RegisterSecondFragment extends BaseBackFragment {
    ImageView iv_back;
    Button bt_rg2_submit;

    public static RegisterSecondFragment newInstance(){
        Bundle args = new Bundle();
        RegisterSecondFragment fragment = new RegisterSecondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_second,container,false);
        initView(view);
        setListener();
        return view;
    }

    private void initView(View view){
        bt_rg2_submit = (Button) view.findViewById(R.id.bt_rg2_submit);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);

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
                EventBus.getDefault().post(new StartBrotherEvent(RegisterThirdFragment.newInstance()));
            }
        });
    }
}
