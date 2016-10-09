package com.hdcy.app.fragment.third;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseLazyMainFragment;
import com.hdcy.app.fragment.first.FirstFragment;

/**
 * Created by WeiYanGeorge on 2016-10-07.
 */

public class ThirdFragment extends BaseLazyMainFragment {


    public static ThirdFragment newInstance() {

        Bundle args = new Bundle();

        ThirdFragment fragment = new ThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_page,container,false);

        return view;
    }
}
