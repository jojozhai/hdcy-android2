package com.hdcy.base.utils.net;

import org.xutils.common.Callback;

import java.io.File;

/**
 * Created by WeiYanGeorge on 2016-11-01.
 */

public class NetUploadHelper {

    private static class NetUploadHelperHolder{
        private static final NetUploadHelper instance = new NetUploadHelper();
    }

    public static NetUploadHelper geInstance(){
        return NetUploadHelperHolder.instance;
    }

    public NetUploadHelper(){
        super();
    }

    private NetRequest request;
    private Callback.Cancelable cancelable;

    /**
     * 上传
     *
     */
/*    public void upload(File file , final NetRequestCallBack callBack){
        request = new NetRequest("/image/upload");
        request.addParam("file",file);
        request.setTimeOut(60 * 1000 * 1000);
        cancelable = request.postinfo()
    }*/
}
