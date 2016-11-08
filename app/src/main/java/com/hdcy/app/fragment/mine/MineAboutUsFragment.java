package com.hdcy.app.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.fragment.OutLinkWebFragment;

import org.greenrobot.eventbus.EventBus;

import static com.hdcy.base.BaseData.URL_BASE;

/**
 * Created by WeiYanGeorge on 2016-11-06.
 */

public class MineAboutUsFragment extends BaseBackFragment {
    private Toolbar mToolbar;
    private TextView title;
    private LinearLayout ll_about_company;
    private LinearLayout ll_about_contact;
    private LinearLayout ll_about_agreement;
    String company = URL_BASE + "/views/about/company.html";
    String contact = URL_BASE + "/views/about/contact.html";
    String agreement = URL_BASE +"/views/about/agreement.html";

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
        setListener();
        return view;
    }

    private void initView(View view){
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title =(TextView) view.findViewById(R.id.toolbar_title);
        title.setText("关于我们");
        initToolbarNav(mToolbar);
        ll_about_company = (LinearLayout) view.findViewById(R.id.ll_about_company);
        ll_about_contact = (LinearLayout) view.findViewById(R.id.ll_about_contact);
        ll_about_agreement = (LinearLayout) view.findViewById(R.id.ll_about_agreement);
    }

    private void setListener(){
        ll_about_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(OutLinkWebFragment.newInstance(company,"公司介绍")));
            }
        });
        ll_about_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(OutLinkWebFragment.newInstance(contact,"联系我们")));
            }
        });
        ll_about_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StartBrotherEvent(OutLinkWebFragment.newInstance(agreement,"用户协议")));
            }
        });
    }
}
