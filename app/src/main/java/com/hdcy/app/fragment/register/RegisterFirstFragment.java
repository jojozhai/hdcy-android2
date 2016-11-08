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
import com.hdcy.app.fragment.MainFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.zip.Inflater;

/**
 * Created by WeiYanGeorge on 2016-11-06.
 */

public class RegisterFirstFragment extends BaseBackFragment {
    ImageView iv_back;
    Button bt_rg_submit;

    public static RegisterFirstFragment newInstance(){
        Bundle args = new Bundle();
        RegisterFirstFragment fragment = new RegisterFirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_first, container, false);
        initView(view);
        setListener();
        return view;
    }

    private void initView(View view){
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        bt_rg_submit = (Button) view.findViewById(R.id.bt_rg_submit);

    }

    private void setListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
        bt_rg_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(RegisterSecondFragment.newInstance()));
            }
        });
    }
}
