package com.hdcy.app.fragment.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdcy.app.R;
import com.hdcy.app.activity.MainActivity;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.event.StartBrotherEvent;
import com.hdcy.app.fragment.MainFragment;
import com.hdcy.app.fragment.mine.ChooseCityFragment;
import com.hdcy.base.utils.BaseUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.zip.Inflater;

/**
 * Created by WeiYanGeorge on 2016-11-06.
 */

public class RegisterFirstFragment extends BaseBackFragment {
    ImageView iv_back;
    Button bt_rg_submit;
    EditText edt_nick_name;
    LinearLayout ll_sex_choice;
    LinearLayout ll_rg_city;
    EditText edt_city;
    Button bt_rg_sex1;
    Button bt_rg_sex2;
    //false 男1 true 女2
    boolean sex_choice = false;
    String sex_string = "1";
    String city_string ;
    String nick_name;
    Bundle bundle = new Bundle();

    TextView tv_rg_city;

    private static final int REQUEST_SELECT_CITY = 1001;
    static final String KEY_RESULT_CHOOSE_CITY = "choose_city";

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

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SELECT_CITY && resultCode == RESULT_OK){
            city_string = data.getString(KEY_RESULT_CHOOSE_CITY);
            bundle.putString("register_city",city_string);
            Log.e("CITYCHOOSE",city_string);
            tv_rg_city.setText(city_string);
        }
    }

    private boolean checkData(){
        nick_name = edt_nick_name.getText().toString();
        bundle.putString("register_nickname",nick_name);
        bundle.putString("register_sex",sex_string);
        return true;
    }

    private void initView(View view){
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        bt_rg_submit = (Button) view.findViewById(R.id.bt_rg_submit);
        ll_sex_choice = (LinearLayout) view.findViewById(R.id.ll_rg_sex_button);
        ll_rg_city = (LinearLayout) view.findViewById(R.id.ll_rg_city);
        edt_nick_name = (EditText) view.findViewById(R.id.edt_rg_nickname);
        bt_rg_sex1 = (Button) view.findViewById(R.id.bt_rg_sex1);
        bt_rg_sex2 = (Button) view.findViewById(R.id.bt_rg_sex2);
        bt_rg_sex1.setClickable(false);
        bt_rg_sex2.setClickable(false);
        tv_rg_city = (TextView) view.findViewById(R.id.tv_rg_city);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setListener(){
        ll_rg_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForResult(ChooseCityFragment.newInstance(),REQUEST_SELECT_CITY);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
        bt_rg_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()&&!BaseUtils.isEmptyString(city_string)&&!BaseUtils.isEmptyString(sex_string)) {
                    start(RegisterSecondFragment.newInstance(bundle));
                }else {
                    Toast.makeText(getContext(),"请完善注册的内容",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ll_sex_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sex_choice){
                    bt_rg_sex1.setBackground(getResources().getDrawable(R.drawable.sex_pressed_button));
                    bt_rg_sex2.setBackground(getResources().getDrawable(R.drawable.sex_default_button));
                    bt_rg_sex1.setTextColor(getResources().getColor(R.color.white));
                    bt_rg_sex2.setTextColor(getResources().getColor(R.color.color_sex_pressed));
                    sex_choice = true;
                    sex_string = "2";
                }else {
                    bt_rg_sex1.setBackground(getResources().getDrawable(R.drawable.sex_default_button));
                    bt_rg_sex2.setBackground(getResources().getDrawable(R.drawable.sex_pressed_button));
                    bt_rg_sex1.setTextColor(getResources().getColor(R.color.color_sex_pressed));
                    bt_rg_sex2.setTextColor(getResources().getColor(R.color.white));
                    sex_choice = false;
                    sex_string = "1";
                }
            }
        });
    }
}
