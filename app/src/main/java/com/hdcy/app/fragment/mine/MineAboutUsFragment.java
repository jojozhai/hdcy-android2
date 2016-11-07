package com.hdcy.app.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;

/**
 * Created by WeiYanGeorge on 2016-11-06.
 */

public class MineAboutUsFragment extends BaseBackFragment {
    private Toolbar mToolbar;
    private TextView title;

    public static MineAboutUsFragment newInstance(){
        Bundle args = new Bundle();
        MineAboutUsFragment fragment = new MineAboutUsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_about_us, container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title =(TextView) view.findViewById(R.id.toolbar_title);
        title.setText("关于我们");
        initToolbarNav(mToolbar);
    }
}
