package com.hdcy.base.utils.net;

import android.util.Log;

import com.hdcy.app.model.RootListInfo;
import com.hdcy.base.BaseData;
import com.hdcy.base.BaseInfo;
import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.logger.LogF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;


/**
 * Created by WeiYanGeorge on 2016-08-10.
 */

public class NetRequest implements BaseData {

    private RequestParams params;
    private String url;
    private NetRequestInfo netRequestInfo;
    private NetResponseInfo netResponseInfo;

    /**
     * 初始化请求
     * @param url 地址
     *
     */
    public NetRequest(String url) {
            this.url = URL_BASE+url;
        params = new RequestParams(this.url);
        netRequestInfo = new NetRequestInfo();
        netResponseInfo = new NetResponseInfo();
        netRequestInfo.setUrl(this.url+"?");
        //addHeader("Authorization","Basic MToxMjM0NTY=");

        //addHeader("Authorization","Basic MTU2MDA3NTY1NjY6MTIzNDU2Nzg=");
        if(!BaseUtils.isEmptyString(BaseInfo.getPp_token())){
            Log.e("pptoken",BaseInfo.getPp_token());
        }
        if(!BaseUtils.isEmptyString(BaseInfo.pp_token)) {
            addHeader("Authorization", BaseInfo.pp_token);
            addHeader("Content-Type", "application/json;charset=UTF-8");
        }else {
           // addHeader("Authorization", null);
        }

    }


    public void addHeader(String key, String value) {
        params.addHeader(key, value);
    }


    public void addParam(String key, String value) {
        params.addBodyParameter(key, value);
        Log.e(key + " = " + value,"");
    }

    public void addParamjson( String value) {
        params.setBodyContent(value);
    }

    public void addParam(String key, int value) {
        addParam(key, value + "");
    }

    public void addParam(String key, double value) {
        addParam(key, value + "");
    }

    public void addParam(String key, File value) {
        params.setMultipart(true);
        params.addBodyParameter(key, value);
        netRequestInfo.setUrl(netRequestInfo.getUrl() + key + "=" + value + "&");
    }

    public void setTimeOut(int time) {
        params.setConnectTimeout(time);
    }

    /**
     * 请求/上传数据
     * get 单一object
     * @param callBack 回调
     */
    public Callback.Cancelable postobject(final NetRequestCallBack callBack) {
        String str = netRequestInfo.getUrl();
        netRequestInfo.setUrl(str.substring(0, str.length() - 1));
        Log.e("testinfo",str);
        return x.http().get(params, new Callback.ProgressCallback<String>() {

            @Override
            public void onStarted() {
                LogUtil.e("onStart");
                if (callBack != null) {
                    callBack.onStart();
                }
            }

            @Override
            public void onWaiting() {
                LogUtil.e("onWaiting");
                callBack.onWaiting();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled：" + cex.getMessage());
                cex.printStackTrace();
                callBack.onCancelled();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                LogUtil.e("onLoading：" + current + " - " + total + " == " + (int) ((current * 1.0f / total) * 100) + "%");
                if (callBack != null) {
                    callBack.onLoading(total, current, current * 1.0f / total, isDownloading);
                }
            }

            @Override
            public void onSuccess(String result) {
                netResponseInfo.setResult(result);
                try {
                    Log.e("Successmsg",result);
                    JSONObject  object = new JSONObject(result);
                    Log.e("Successmsg1",object.toString());
                    Log.e("onSuccess1：" + netRequestInfo.getUrl(),"");
                    Log.e("onSuccess1：" + netResponseInfo.getResult(),"");
                    netResponseInfo.setDataObj(object);
                    if (callBack != null) {
                        callBack.onSuccess(netRequestInfo, netResponseInfo);
                    }
                } catch (JSONException e) {
                    LogUtil.e("onError：" + netRequestInfo.getUrl());
                    LogUtil.e("onError：" + netResponseInfo.getMessage());
                    LogUtil.e("onError：" + netResponseInfo.getResult());
                    if (callBack != null) {
                        callBack.onError(netRequestInfo, netResponseInfo);
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onFailure：" + netRequestInfo.getUrl());
                LogUtil.e("onFailure：" + ex.getMessage());
                Log.e("onFailure",netRequestInfo.getUrl());
                Log.e("onFailure",ex.getLocalizedMessage());
                netResponseInfo.setMessage(ex.getMessage());
                if (callBack != null) {
                    callBack.onFailure(netRequestInfo, netResponseInfo);
                }
                ex.printStackTrace();
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished");
                if (callBack != null) {
                    callBack.onFinished();
                }
            }
        });
    }

    /**
     * get请求
     * 单一对象
     * @param callBack
     * @return
     */
    public Callback.Cancelable getObj(final NetRequestCallBack callBack) {
        String str = netRequestInfo.getUrl();
        netRequestInfo.setUrl(str.substring(0, str.length() - 1));
        Log.e("testinfo",str);
        return x.http().get(params, new Callback.ProgressCallback<String>() {

            @Override
            public void onStarted() {
                LogUtil.e("onStart");
                if (callBack != null) {
                    callBack.onStart();
                }
            }

            @Override
            public void onWaiting() {
                LogUtil.e("onWaiting");
                callBack.onWaiting();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled：" + cex.getMessage());
                cex.printStackTrace();
                callBack.onCancelled();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                LogUtil.e("onLoading：" + current + " - " + total + " == " + (int) ((current * 1.0f / total) * 100) + "%");
                if (callBack != null) {
                    callBack.onLoading(total, current, current * 1.0f / total, isDownloading);
                }
            }

            @Override
            public void onSuccess(String result) {
                netResponseInfo.setResult(result);
                try {
//                    Log.e("Successmsg",result);
                    JSONObject  obj = new JSONObject(result);
//                    Log.e("Successmsg1",obj.toString());
//                    Log.e("onSuccess1：" + netRequestInfo.getUrl(),"");
//                    Log.e("onSuccess1：" + netResponseInfo.getResult(),"");

                    LogF.t(netRequestInfo.getUrl()).json(netResponseInfo.getResult());
                    netResponseInfo.setDataObj(obj);
                    netResponseInfo.rootListInfo=new RootListInfo();
                    netResponseInfo.rootListInfo.setLast(obj.optBoolean("last"));
                    netResponseInfo.rootListInfo.setFirst(obj.optBoolean("first"));

                        if (callBack != null) {
                            callBack.onSuccess(netRequestInfo, netResponseInfo);
                    }
                } catch (JSONException e) {
                    LogUtil.e("onError：" + netRequestInfo.getUrl());
                    LogUtil.e("onError：" + netResponseInfo.getMessage());
                    LogUtil.e("onError：" + netResponseInfo.getResult());
                    if (callBack != null) {
                        callBack.onError(netRequestInfo, netResponseInfo);
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                LogUtil.e("onFailure：" + netRequestInfo.getUrl());
                Log.e("onFailure",netRequestInfo.getUrl());
                LogUtil.e("onFailure：" + ex.getMessage());
//                Log.e("onFailure",ex.getLocalizedMessage());
                netResponseInfo.setMessage(ex.getMessage());
                if (callBack != null) {
                    callBack.onFailure(netRequestInfo, netResponseInfo);
                }
                ex.printStackTrace();
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished");
                if (callBack != null) {
                    callBack.onFinished();
                }
            }
        });
    }

    /**
     * get object
     */

    public Callback.Cancelable getobject(final NetRequestCallBack callBack) {
        String str = netRequestInfo.getUrl();
        netRequestInfo.setUrl(str.substring(0, str.length() - 1));
        Log.e("testinfo",str);
        return x.http().get(params, new Callback.ProgressCallback<String>() {

            @Override
            public void onStarted() {
                LogUtil.e("onStart");
                if (callBack != null) {
                    callBack.onStart();
                }
            }

            @Override
            public void onWaiting() {
                LogUtil.e("onWaiting");
                callBack.onWaiting();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled：" + cex.getMessage());
                cex.printStackTrace();
                callBack.onCancelled();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                LogUtil.e("onLoading：" + current + " - " + total + " == " + (int) ((current * 1.0f / total) * 100) + "%");
                if (callBack != null) {
                    callBack.onLoading(total, current, current * 1.0f / total, isDownloading);
                }
            }

            @Override
            public void onSuccess(String result) {
                netResponseInfo.setResult(result);
                    Log.e("Successmsg",result);
                    Log.e("onSuccess1：" + netRequestInfo.getUrl(),"");
                    Log.e("onSuccess1：" + netResponseInfo.getResult(),"");
                    if (callBack != null) {
                        callBack.onSuccess(netRequestInfo, netResponseInfo);
                    }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onFailure：" + netRequestInfo.getUrl());
                LogUtil.e("onFailure：" + ex.getMessage());
                Log.e("onFailure",netRequestInfo.getUrl());
                Log.e("onFailure",ex.getLocalizedMessage());
                netResponseInfo.setMessage(ex.getMessage());
                if (callBack != null) {
                    callBack.onFailure(netRequestInfo, netResponseInfo);
                }
                ex.printStackTrace();
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished");
                if (callBack != null) {
                    callBack.onFinished();
                }
            }
        });
    }
    /**
     * get请求
     * 单一数组
     * @param callBack
     * @return
     */
    public Callback.Cancelable getarray(final NetRequestCallBack callBack) {
        String str = netRequestInfo.getUrl();
        netRequestInfo.setUrl(str.substring(0, str.length() - 1));
        Log.e("testinfo",str);
        return x.http().get(params, new Callback.ProgressCallback<String>() {

            @Override
            public void onStarted() {
                LogUtil.e("onStart");
                if (callBack != null) {
                    callBack.onStart();
                }
            }

            @Override
            public void onWaiting() {
                LogUtil.e("onWaiting");
                callBack.onWaiting();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled：" + cex.getMessage());
                cex.printStackTrace();
                callBack.onCancelled();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                LogUtil.e("onLoading：" + current + " - " + total + " == " + (int) ((current * 1.0f / total) * 100) + "%");
                if (callBack != null) {
                    callBack.onLoading(total, current, current * 1.0f / total, isDownloading);
                }
            }

            @Override
            public void onSuccess(String result) {
                netResponseInfo.setResult(result);
                try {
                    Log.e("Successmsg",result);
                    JSONArray  objarry = new JSONArray(result);
                    Log.e("Successmsg1",objarry.toString());
                    Log.e("onSuccess1：" + netRequestInfo.getUrl(),"");
                    Log.e("onSuccess1：" + netResponseInfo.getResult(),"");
                    netResponseInfo.setDataArr(objarry);
                        if (callBack != null) {
                            callBack.onSuccess(netRequestInfo, netResponseInfo);
                    }
                } catch (JSONException e) {
                    LogUtil.e("onError：" + netRequestInfo.getUrl());
                    LogUtil.e("onError：" + netResponseInfo.getMessage());
                    LogUtil.e("onError：" + netResponseInfo.getResult());
                    if (callBack != null) {
                        callBack.onError(netRequestInfo, netResponseInfo);
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onFailure：" + netRequestInfo.getUrl());
                LogUtil.e("onFailure：" + ex.getMessage());
                Log.e("onFailure",netRequestInfo.getUrl());
                Log.e("onFailure",ex.getLocalizedMessage());
                netResponseInfo.setMessage(ex.getMessage());
                if (callBack != null) {
                    callBack.onFailure(netRequestInfo, netResponseInfo);
                }
                ex.printStackTrace();
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished");
                if (callBack != null) {
                    callBack.onFinished();
                }
            }
        });
    }

    /**
     * 请求数据
     * get请求
     * @param callBack 回调
     */
    public Callback.Cancelable postarray(final NetRequestCallBack callBack) {
        String str = netRequestInfo.getUrl();
        netRequestInfo.setUrl(str.substring(0, str.length() - 1));
        Log.e("testinfo",str);
        return x.http().get(params, new Callback.ProgressCallback<String>() {

            @Override
            public void onStarted() {
                LogUtil.e("onStart");
                if (callBack != null) {
                    callBack.onStart();
                }
            }

            @Override
            public void onWaiting() {
                LogUtil.e("onWaiting");
                callBack.onWaiting();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled：" + cex.getMessage());
                cex.printStackTrace();
                callBack.onCancelled();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                LogUtil.e("onLoading：" + current + " - " + total + " == " + (int) ((current * 1.0f / total) * 100) + "%");
                if (callBack != null) {
                    callBack.onLoading(total, current, current * 1.0f / total, isDownloading);
                }
            }

            @Override
            public void onSuccess(String result) {
                netResponseInfo.setResult(result);
                try {
                    Log.e("Successmsg",result);
                    JSONObject object = new JSONObject(result);
                    Log.e("Successmsg1",object.toString());
                    Log.e("onSuccess2：" + netRequestInfo.getUrl(),"");
                    Log.e("onSuccess2：" + netResponseInfo.getResult(),"");
                    netResponseInfo.setDataObj(object);
                    //netResponseInfo.setDataObj(object.optJSONObject("content"));
                    netResponseInfo.setDataArr(object.optJSONArray("content"));
                    if (callBack != null) {
                        callBack.onSuccess(netRequestInfo, netResponseInfo);
                    }
                } catch (JSONException e) {
                    LogUtil.e("onError：" + netRequestInfo.getUrl());
                    LogUtil.e("onError：" + netResponseInfo.getMessage());
                    LogUtil.e("onError：" + netResponseInfo.getResult());
                    if (callBack != null) {
                        callBack.onError(netRequestInfo, netResponseInfo);
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onFailure：" + netRequestInfo.getUrl());
                LogUtil.e("onFailure：" + ex.getMessage());
                Log.e("onFailure",netRequestInfo.getUrl());
//                Log.e("onFailure",ex.getLocalizedMessage());
                netResponseInfo.setMessage(ex.getMessage());
                if (callBack != null) {
                    callBack.onFailure(netRequestInfo, netResponseInfo);
                }
                ex.printStackTrace();
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished");
                if (callBack != null) {
                    callBack.onFinished();
                }
            }
        });
    }

    public Callback.Cancelable getpraisearray(final NetRequestCallBack callBack) {
        String str = netRequestInfo.getUrl();
        netRequestInfo.setUrl(str.substring(0, str.length() - 1));
        Log.e("testinfo",str);
        return x.http().get(params, new Callback.ProgressCallback<String>() {

            @Override
            public void onStarted() {
                LogUtil.e("onStart");
                if (callBack != null) {
                    callBack.onStart();
                }
            }

            @Override
            public void onWaiting() {
                LogUtil.e("onWaiting");
                callBack.onWaiting();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled：" + cex.getMessage());
                cex.printStackTrace();
                callBack.onCancelled();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                LogUtil.e("onLoading：" + current + " - " + total + " == " + (int) ((current * 1.0f / total) * 100) + "%");
                if (callBack != null) {
                    callBack.onLoading(total, current, current * 1.0f / total, isDownloading);
                }
            }

            @Override
            public void onSuccess(String result) {
                netResponseInfo.setResult(result);
                try {
                    Log.e("Successmsg",result);
                    JSONArray object = new JSONArray(result);
                    Log.e("Successmsg1",object.toString());
                    Log.e("onSuccess2：" + netRequestInfo.getUrl(),"");
                    Log.e("onSuccess2：" + netResponseInfo.getResult(),"");
                    netResponseInfo.setDataArr(object);
                    if (callBack != null) {
                        callBack.onSuccess(netRequestInfo, netResponseInfo);
                    }
                } catch (JSONException e) {
                    LogUtil.e("onError：" + netRequestInfo.getUrl());
                    LogUtil.e("onError：" + netResponseInfo.getMessage());
                    LogUtil.e("onError：" + netResponseInfo.getResult());
                    if (callBack != null) {
                        callBack.onError(netRequestInfo, netResponseInfo);
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onFailure：" + netRequestInfo.getUrl());
                LogUtil.e("onFailure：" + ex.getMessage());
                Log.e("onFailure",netRequestInfo.getUrl());
//                Log.e("onFailure",ex.getLocalizedMessage());
                netResponseInfo.setMessage(ex.getMessage());
                if (callBack != null) {
                    callBack.onFailure(netRequestInfo, netResponseInfo);
                }
                ex.printStackTrace();
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished");
                if (callBack != null) {
                    callBack.onFinished();
                }
            }
        });
    }

    /**
     * 上传数据
     * post数据
     * @param callBack 回调
     */
    public Callback.Cancelable postinfo(final NetRequestCallBack callBack) {
        String str = netRequestInfo.getUrl();
        netRequestInfo.setUrl(str.substring(0, str.length() - 1));
        return x.http().post(params, new Callback.ProgressCallback<String>() {

            @Override
            public void onStarted() {
                LogUtil.e("onStart");
                if (callBack != null) {
                    callBack.onStart();
                }
            }

            @Override
            public void onWaiting() {
                LogUtil.e("onWaiting");
                callBack.onWaiting();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled：" + cex.getMessage());
                cex.printStackTrace();
                callBack.onCancelled();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                LogUtil.e("onLoading：" + current + " - " + total + " == " + (int) ((current * 1.0f / total) * 100) + "%");
                if (callBack != null) {
                    callBack.onLoading(total, current, current * 1.0f / total, isDownloading);
                }
            }

            @Override
            public void onSuccess(String result) {
                netResponseInfo.setResult(result);
                try {
                    Log.e("Successmsg",result);
                    JSONObject  object = new JSONObject(result);
                    Log.e("Successmsg1",object.toString());
                    Log.e("onSuccess1：" + netRequestInfo.getUrl(),"");
                    Log.e("onSuccess1：" + netResponseInfo.getResult(),"");
                    netResponseInfo.setDataObj(object);
                    if (callBack != null) {
                        callBack.onSuccess(netRequestInfo, netResponseInfo);
                    }
                } catch (JSONException e) {
                    LogUtil.e("onError：" + netRequestInfo.getUrl());
                    LogUtil.e("onError：" + netResponseInfo.getMessage());
                    LogUtil.e("onError：" + netResponseInfo.getResult());
                    if (callBack != null) {
                        callBack.onError(netRequestInfo, netResponseInfo);
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onFailure：" + netRequestInfo.getUrl());
                LogUtil.e("onFailure：" + ex.getMessage());
                Log.e("onFailure",netRequestInfo.getUrl());
                Log.e("onFailure",ex.getLocalizedMessage());
                netResponseInfo.setMessage(ex.getMessage());
                if (callBack != null) {
                    callBack.onFailure(netRequestInfo, netResponseInfo);
                }
                ex.printStackTrace();
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished");
                if (callBack != null) {
                    callBack.onFinished();
                }
            }
        });
    }



    /**
     * put 请求
     * @param callBack
     * @return
     */
    public Callback.Cancelable putinfo(final NetRequestCallBack callBack) {
        String str = netRequestInfo.getUrl();
        netRequestInfo.setUrl(str.substring(0, str.length() - 1));
        return x.http().request(HttpMethod.PUT, params, new Callback.ProgressCallback<String>() {

            @Override
            public void onStarted() {
                LogUtil.e("onStart");
                if (callBack != null) {
                    callBack.onStart();
                }
            }

            @Override
            public void onWaiting() {
                LogUtil.e("onWaiting");
                callBack.onWaiting();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled：" + cex.getMessage());
                cex.printStackTrace();
                callBack.onCancelled();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                LogUtil.e("onLoading：" + current + " - " + total + " == " + (int) ((current * 1.0f / total) * 100) + "%");
                if (callBack != null) {
                    callBack.onLoading(total, current, current * 1.0f / total, isDownloading);
                }
            }

            @Override
            public void onSuccess(String result) {
                netResponseInfo.setResult(result);
                try {
                    Log.e("Successmsg",result);
                    JSONObject object = new JSONObject(result);
                    Log.e("Successmsg1",object.toString());
                    Log.e("onSuccess2：" + netRequestInfo.getUrl(),"");
                    Log.e("onSuccess2：" + netResponseInfo.getResult(),"");
                    netResponseInfo.setDataObj(object.optJSONObject("content"));
                    netResponseInfo.setDataArr(object.optJSONArray("content"));
                    if (callBack != null) {
                        callBack.onSuccess(netRequestInfo, netResponseInfo);
                    }
                } catch (JSONException e) {
                    LogUtil.e("onError：" + netRequestInfo.getUrl());
                    LogUtil.e("onError：" + netResponseInfo.getMessage());
                    LogUtil.e("onError：" + netResponseInfo.getResult());
                    if (callBack != null) {
                        callBack.onError(netRequestInfo, netResponseInfo);
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onFailure：" + netRequestInfo.getUrl());
                LogUtil.e("onFailure：" + ex.getMessage());
                Log.e("onFailure",netRequestInfo.getUrl());
                Log.e("onFailure",ex.getLocalizedMessage());
                netResponseInfo.setMessage(ex.getMessage());
                if (callBack != null) {
                    callBack.onFailure(netRequestInfo, netResponseInfo);
                }
                ex.printStackTrace();
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished");
                if (callBack != null) {
                    callBack.onFinished();
                }
            }
        });
    }
    /**
     * put 请求
     * @param callBack
     * @return
     */
    public Callback.Cancelable putmineinfo(final NetRequestCallBack callBack) {
        String str = netRequestInfo.getUrl();
        netRequestInfo.setUrl(str.substring(0, str.length() - 1));
        return x.http().request(HttpMethod.PUT, params, new Callback.ProgressCallback<String>() {

            @Override
            public void onStarted() {
                LogUtil.e("onStart");
                if (callBack != null) {
                    callBack.onStart();
                }
            }

            @Override
            public void onWaiting() {
                LogUtil.e("onWaiting");
                callBack.onWaiting();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled：" + cex.getMessage());
                cex.printStackTrace();
                callBack.onCancelled();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                LogUtil.e("onLoading：" + current + " - " + total + " == " + (int) ((current * 1.0f / total) * 100) + "%");
                if (callBack != null) {
                    callBack.onLoading(total, current, current * 1.0f / total, isDownloading);
                }
            }

            @Override
            public void onSuccess(String result) {
                netResponseInfo.setResult(result);

                    Log.e("Successmsg",result);

                    //JSONObject object = new JSONObject(result);
                    //Log.e("Successmsg1",object.toString());
                    Log.e("onSuccess2：" + netRequestInfo.getUrl(),"");
                    Log.e("onSuccess2：" + netResponseInfo.getResult(),"");
                    if (callBack != null) {
                        callBack.onSuccess(netRequestInfo, netResponseInfo);
                    }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onFailure：" + netRequestInfo.getUrl());
                LogUtil.e("onFailure：" + ex.getMessage());
                Log.e("onFailure",netRequestInfo.getUrl());
                Log.e("onFailure",ex.getLocalizedMessage());
                netResponseInfo.setMessage(ex.getMessage());
                if (callBack != null) {
                    callBack.onFailure(netRequestInfo, netResponseInfo);
                }
                ex.printStackTrace();
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished");
                if (callBack != null) {
                    callBack.onFinished();
                }
            }
        });
    }
}
