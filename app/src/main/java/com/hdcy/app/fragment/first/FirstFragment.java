package com.hdcy.app.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseLazyMainFragment;
import com.hdcy.app.model.NewsCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-10-07.
 */

public class FirstFragment extends BaseLazyMainFragment{

    public static FirstFragment newInstance() {

        Bundle args = new Bundle();

        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_page,container,false);

        return view;
    }









    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }
}
