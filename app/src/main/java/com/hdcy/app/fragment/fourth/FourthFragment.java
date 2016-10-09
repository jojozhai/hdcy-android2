package com.hdcy.app.fragment.fourth;

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

public class FourthFragment extends BaseLazyMainFragment{


    public static FourthFragment newInstance() {

        Bundle args = new Bundle();

        FourthFragment fragment = new FourthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth_page,container,false);
        return view;
    }
}
