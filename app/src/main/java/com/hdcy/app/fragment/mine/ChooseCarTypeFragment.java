package com.hdcy.app.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hdcy.app.R;
import com.hdcy.app.adapter.CarAdapter;
import com.hdcy.app.basefragment.BaseBackFragment;
import com.hdcy.app.model.CarEntity;
import com.hdcy.app.model.CityEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 * Created by WeiYanGeorge on 2016-11-03.
 */

public class ChooseCarTypeFragment extends BaseBackFragment {
    private List<CarEntity> mDatas = new ArrayList<>();
    private CarAdapter mAdapter;
    private FrameLayout mProgressBar;

    public static ChooseCarTypeFragment newInstance(){
        Bundle args = new Bundle();
        ChooseCarTypeFragment fragment = new ChooseCarTypeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_city, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        IndexableLayout indexableLayout = (IndexableLayout) view.findViewById(R.id.indexableLayout);
        mProgressBar = (FrameLayout) view.findViewById(R.id.progress);
        mAdapter = new CarAdapter(getContext());
        indexableLayout.setAdapter(mAdapter);
        mDatas = initDatas();
        indexableLayout.setFastCompare(true);
        mAdapter.setDatas(mDatas, new IndexableAdapter.IndexCallback<CarEntity>() {
            @Override
            public void onFinished(List<CarEntity> datas) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<CarEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, CarEntity entity) {
                Bundle bundle = new Bundle();
                bundle.putString(MineInfoFragment.KEY_RESULT_CHOOSE_CAR,entity.getName());
                setFramgentResult(RESULT_OK,bundle);
                _mActivity.onBackPressed();
            }
        });
    }

    private List<CarEntity> initDatas(){
        List<CarEntity> list = new ArrayList<>();
        List<String> carStrings = Arrays.asList(getResources().getStringArray(R.array.car_array));
        for (String item : carStrings ){
            CarEntity carEntity = new CarEntity();
            carEntity.setName(item);
            list.add(carEntity);
        }
        return list;
    }
}
