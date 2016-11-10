package com.hdcy.app.fragment.Message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseLazyMainFragment;

/**
 * Created by WeiYanGeorge on 2016-11-10.
 */

public class MessageFragment extends BaseLazyMainFragment{


    public static MessageFragment newInstance(){
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){

    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }
}
