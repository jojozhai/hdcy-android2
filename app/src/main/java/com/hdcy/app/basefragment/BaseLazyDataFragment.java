package com.hdcy.app.basefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by WeiYanGeorge on 16/11/20.
 */

public abstract class BaseLazyDataFragment extends BaseFragment{

    protected boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Log.d("TAG", "fragment->setUserVisibleHint");
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            // onInvisible();
        }
    }

    protected void lazyLoad() {
        Log.e("Lazystatus",isPrepared +","+ isVisible +","+ isFirst);
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        Log.d("TAG", getClass().getName() + "->initData()");
        initLazyData();
        isFirst = false;
    }

    public abstract void initLazyData();
}
