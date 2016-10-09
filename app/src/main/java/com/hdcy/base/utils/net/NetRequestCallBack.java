package com.hdcy.base.utils.net;

/**
 * Created by WeiYanGeorge on 2016-08-10.
 */

public abstract class NetRequestCallBack {

    public void onStart() {
    }

    public void onWaiting() {
    }

    public void onCancelled() {
    }

    public void onLoading(long total, long current, float progress, boolean isUploading) {
    }

    public abstract void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo);

    public abstract void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo);

    public abstract void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo);

    public void onFinished() {
    }
}
