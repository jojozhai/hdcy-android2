package com.hdcy.base.utils.net;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hdcy.app.model.ActivityContent;
import com.hdcy.app.model.ActivityDetails;
import com.hdcy.app.model.ArticleInfo;
import com.hdcy.app.model.AvatarResult;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.app.model.Content;
import com.hdcy.app.model.GiftContent;
import com.hdcy.app.model.LeaderInfo;
import com.hdcy.app.model.LoginResult;
import com.hdcy.app.model.NewsCategory;
import com.hdcy.app.model.PraiseResult;
import com.hdcy.app.model.Replys;
import com.hdcy.app.model.Result;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.app.model.UserBaseInfo;
import com.hdcy.app.model.VideoBasicInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-08-10.
 */

public class NetHelper {

    private static final String TAG = "NetHelper";

    private static class NetHelperHolder {
        private static final NetHelper instance = new NetHelper();
    }

    public static NetHelper getInstance() {
        return NetHelperHolder.instance;
    }

    private NetHelper() {
        super();
    }

    /**
     * 获取资讯分类
     *
     * @param callBack 回调
     */
    public Callback.Cancelable GetNewsCategoryList(final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/tag/child");
        Log.e("nettest","new");
        return request.getarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataArr = responseInfo.getDataArr();
                if (dataArr != null){
                    responseInfo.setNewsCategoryList(JSON.parseArray(dataArr.toString(), NewsCategory.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                callBack.onError(requestInfo, responseInfo);

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                callBack.onFailure(requestInfo,responseInfo);

            }
        });
    }

    public Callback.Cancelable GetWholeNewsArticleContent(int pagecount,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/article/");
        request.addParam("enable","true");
        request.addParam("page",pagecount);
        request.addParam("size",20);
        request.addParam("sort","createdTime,desc");
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();
                JSONObject dataObj1 = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setContentList(JSON.parseArray(dataObj.toString(), Content.class));
                    responseInfo.setRootListInfo(JSON.parseObject(dataObj1.toString(),RootListInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("article2","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("article2","onfailure");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("article2","onfailure");

            }
        });
    }

    /**
     * 获取资讯列表
     * @param pagecount 每页显示数量
     * @param tagId 资讯类别id
     * @param callBack
     * @return
     */
    public Callback.Cancelable GetNewsArticleContent(int pagecount,int tagId,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/article/");
        request.addParam("enable","true");
        request.addParam("page",pagecount);
        request.addParam("size","20");
        request.addParam("sort","createdTime,desc");
        request.addParam("tagId",tagId);
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();
                JSONObject dataObj1 = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setContentList(JSON.parseArray(dataObj.toString(), Content.class));
                    responseInfo.setRootListInfo(JSON.parseObject(dataObj1.toString(), RootListInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("article2","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("article2","onfailure");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("article2","onfailure");

            }
        });
    }

    /**
     * 对资讯文章发布评论
     * @param targetid
     * @param content
     * @param callBack
     * @return
     */
    public Callback.Cancelable PublishComments(String targetid,String content,String target,String replyid,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/comment/");
        JSONObject obj = new JSONObject();
        try {
                obj.put("replyToId",replyid);
                obj.put("target", target);
                obj.put("targetId", targetid);
                obj.put("content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.addParamjson(obj.toString());

        return request.postinfo(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONObject dataObj = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setCommentsContent(JSON.parseObject(dataObj.toString(), CommentsContent.class));
                    responseInfo.setReplys(JSON.parseObject(dataObj.toString(), Replys.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    /**
     * 点赞功能
     */
    public Callback.Cancelable  DoPraise( String targetId, final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/praise/");

        JSONObject obj = new JSONObject();
        try {
            obj.put("target", "comment");
            obj.put("targetId", targetId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.addParamjson(obj.toString());
        return request.postinfo(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONObject dataObj = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setPraiseResult(JSON.parseObject(dataObj.toString(), PraiseResult.class));
                }
                Log.e("点赞成功",dataObj.toString());
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    /**
     * 取消点赞
     */

    public Callback.Cancelable UnDoPraise( String targetId, final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/praise/");


        JSONObject obj = new JSONObject();
        try {
            obj.put("target", "comment");
            obj.put("targetId", targetId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.addParamjson(obj.toString());
        return request.putinfo(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                callBack.onSuccess(requestInfo, responseInfo);

            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }



    /**
     * 得到文章内容详情
     * @param tagId
     * @param callBack
     * @return
     */
    public Callback.Cancelable GetArticleInfo(String tagId,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/article/"+tagId);
        return request.postobject(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONObject dataObj = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setArticleInfo(JSON.parseObject(dataObj.toString(), ArticleInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    /**
     *
     * @param tagId
     * @param callBack
     * 得到资讯评论列表
     * @return
     */

    public Callback.Cancelable GetCommentsList(String tagId,String target,int pagecount,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/comments/");
        request.addParam("targetId",tagId);
        request.addParam("page",pagecount);
        request.addParam("size",20);
        request.addParam("sort","createdTime,desc");
        request.addParam("target",target);
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();
                JSONObject dataObj1 = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setCommentsContentList(JSON.parseArray(dataObj.toString(), CommentsContent.class));
                    responseInfo.setRootListInfo(JSON.parseObject(dataObj1.toString(), RootListInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("comments","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("comments","onfailure");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("comments","onfailure");

            }
        });
    }

    /**
     * 得到活动列表
     * 全部null 线上为ONLINE 线下为ACTIVTY
     * @param activityType
     * @param callBack
     * @return
     */


    public Callback.Cancelable GetActivityNew(String activityType,int pagecount,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/activity/");
        request.addParam("enable","true");
        request.addParam("signFinish","false");
        request.addParam("size","100");
        request.addParam("sort","signEndTime,asc");
        request.addParam("top","false");
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();
                JSONObject dataObj1 = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setActivityContentList(JSON.parseArray(dataObj.toString(), ActivityContent.class));
                    responseInfo.setRootListInfo(JSON.parseObject(dataObj1.toString(), RootListInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("activity","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }
        });
    }


    public Callback.Cancelable GetPaticipationList(String activityType,int pagecount,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/activity/");
        request.addParam("page",pagecount);
        request.addParam("enable","true");
        request.addParam("size","15");
        request.addParam("signFinish","true");
        request.addParam("sort","startTime");
        request.addParam("top","false");
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();
                JSONObject dataObj1 = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setActivityContentList(JSON.parseArray(dataObj.toString(), ActivityContent.class));
                    responseInfo.setRootListInfo(JSON.parseObject(dataObj1.toString(), RootListInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("activity","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }
        });
    }

    /**
     * 得到轮播图信息
     *
     * @param callBack
     * @return
     */

    public Callback.Cancelable GetActivityTopBanner( final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/activity/");
        request.addParam("size","100");
        request.addParam("sort","topIndex,asc");
        request.addParam("top","true");
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();
                if (dataObj != null){
                    responseInfo.setActivityContentList(JSON.parseArray(dataObj.toString(), ActivityContent.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("activityhot","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activityhot","onfailure");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activityhot","onfailure");

            }
        });
    }
    /**
     * 得到-- 视频直播 页面 上面 轮播图信息
     *
     * @param callBack
     * @return
     */

    public Callback.Cancelable GetVedioTopBanner( final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/video");
//        request.addParam("page",0);
        request.addParam("enable","true");
        //request.addParam("size","3");
        request.addParam("sort","createdTime,desc");
        request.addParam("top","true");
        request.addParam("liveForApp","true");
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();
                // LogF.json(dataObj.toString());//LOG
                if (dataObj != null){
                    responseInfo.setVideoBasicInfoList(JSON.parseArray(dataObj.toString(), VideoBasicInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e(TAG, "onError() called with: " + "requestInfo = [" + requestInfo + "], responseInfo = [" + responseInfo + "]");
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e(TAG, "onFailure() called with: " + "requestInfo = [" + requestInfo + "], responseInfo = [" + responseInfo + "]");

            }
        });
    }
    /**
     * 得到-- 视频直播 页面 下面的列表数据
     *
     * @param callBack
     * @return
     */

    public Callback.Cancelable getVedioListDatas( int pageIndex, final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/video");
        request.addParam("page",pageIndex);
        request.addParam("enable","true");
        request.addParam("size","10");
        request.addParam("sort","createdTime,desc");
        request.addParam("top","false");
        request.addParam("liveForApp","true");
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();
                JSONObject dataObj1 = responseInfo.getDataObj();
               // LogF.json(dataObj.toString());//LOG
                if (dataObj != null){
                    responseInfo.setVideoBasicInfoList(JSON.parseArray(dataObj.toString(), VideoBasicInfo.class));
                    responseInfo.setRootListInfo(JSON.parseObject(dataObj1.toString(),RootListInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e(TAG, "onError() called with: " + "requestInfo = [" + requestInfo + "], responseInfo = [" + responseInfo + "]");
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e(TAG, "onFailure() called with: " + "requestInfo = [" + requestInfo + "], responseInfo = [" + responseInfo + "]");
            }
        });
    }
    /**
     * 得到-- 视频直播 详情页面的信息
     *
     * @param callBack
     * @return
     */
    public Callback.Cancelable getOneVedioDetail(int vedioId,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/video/"+vedioId);

        return request.postobject(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONObject dataObj = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setVideoBasicInfo(JSON.parseObject(dataObj.toString(),VideoBasicInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e(TAG, "onError() called with: " + "requestInfo = [" + requestInfo + "], responseInfo = [" + responseInfo + "]");
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e(TAG, "onFailure() called with: " + "requestInfo = [" + requestInfo + "], responseInfo = [" + responseInfo + "]");
            }
        });
    }

    public Callback.Cancelable GetActivityRecommended( final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/participation/");
        request.addParam("page",0);
        request.addParam("enable","true");
        request.addParam("size","10");
        request.addParam("sort","createdTime,desc");
        request.addParam("recommend","true");
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();
                if (dataObj != null){
                    responseInfo.setActivityContentList(JSON.parseArray(dataObj.toString(), ActivityContent.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("activityhot","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activityhot","onfailure");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activityhot","onfailure");

            }
        });
    }

    public Callback.Cancelable GetActivityDeatil(String activityId,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/activity/"+activityId);
        return request.postobject(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONObject dataObj = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setActivityDetails(JSON.parseObject(dataObj.toString(), ActivityDetails.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("ActivityDeatils","success");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("ActivityDeatils","failed");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("ActivityDeatils","failed");
            }
        });
    }

    /**
     *
     * @param activityid
     * @param callBack
     * @return
     */

    public Callback.Cancelable GetCurrentPaticipationStatus(String activityid,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/participator/member");


        request.addParam("participationId",activityid);

        return request.postobject(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONObject dataObj = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setResultinfo(JSON.parseObject(dataObj.toString(), Result.class));
                }
                Log.e("aticipationStatus", "success");
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    /**
     * 注册活动
     * @param activityId
     * @param message
     * @param callBack
     * @return
     */

    public Callback.Cancelable RegisterOfflineActivity(String activityId,String message,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/activityParticipator/");
        JSONObject obj = new JSONObject();
        try {
            obj.put("activityId",activityId);
            obj.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.addParamjson(obj.toString());

        return request.postinfo(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("报名状态","success");
                JSONObject dataObj = responseInfo.getDataObj();
                if (dataObj != null){

                }
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    /**
     * 得到当前用户信息
     * @param callBack
     * @return
     */

    public Callback.Cancelable GetCurrentUserInfo( final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/user/current");
        return request.postobject(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONObject dataObj = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setUserBaseInfo(JSON.parseObject(dataObj.toString(), UserBaseInfo.class));
                }
                Log.e("UserBaseInfo", "success");
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    /**
     * 得到我的活动列表
     * @param activityType
     * @param pagecount
     * @param callBack
     * @return
     */

    public Callback.Cancelable GetMineActivityList(String activityType,int pagecount,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/participator/");

        request.addParam("page",pagecount);
        request.addParam("enable","true");
        request.addParam("actType",activityType);
        request.addParam("size","10");
        request.addParam("sort","createdTime,desc");
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();
                JSONObject dataObj1 = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setActivityContentList(JSON.parseArray(dataObj.toString(), ActivityContent.class));
                    responseInfo.setRootListInfo(JSON.parseObject(dataObj1.toString(), RootListInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("activity","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }
        });
    }

    public Callback.Cancelable GetLeaderInfo(final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/leader/");
       // request.addParam("top","true");
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();

                if (dataObj != null){
                    responseInfo.setLeaderInfo(JSON.parseArray(dataObj.toString(), LeaderInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("leaderinfo","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }
        });
    }

    public Callback.Cancelable GetLeaderInfoCategory(String category,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/leader/");

        request.addParam("organ",category);
        request.addParam("sort","createdTime,desc");
        request.addParam("enable","true");

        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();

                if (dataObj != null){
                    responseInfo.setLeaderInfo(JSON.parseArray(dataObj.toString(), LeaderInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("leaderinfo","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }
        });
    }

    public Callback.Cancelable GetLeaderDetailInfo(String leaderId,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/leader/"+leaderId);
        // request.addParam("top","true");
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();

                if (dataObj != null){
                    responseInfo.setLeaderInfo(JSON.parseArray(dataObj.toString(), LeaderInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("leaderinfo","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }
        });
    }


    public Callback.Cancelable GetLeaderBanner(final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/leader/");
         request.addParam("top","true");
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();

                if (dataObj != null){
                    responseInfo.setLeaderInfo(JSON.parseArray(dataObj.toString(), LeaderInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("leaderinfo","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("activity","onfailure");

            }
        });
    }

    public Callback.Cancelable GetMineGiftList(int pagecount,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/gift/");

        request.addParam("page",pagecount);
        request.addParam("size","15");
        request.addParam("sort","used,desc");
        return request.postarray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();
                JSONObject dataObj1 = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setGiftContent(JSON.parseArray(dataObj.toString(), GiftContent.class));
                    responseInfo.setRootListInfo(JSON.parseObject(dataObj1.toString(),RootListInfo.class));
                }
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("gift","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("gift","error");
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("gift","failure");
            }
        });
    }

    public Callback.Cancelable GetCommentPraiseStatus(String targetid,String targettype,int pagecount,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/comment/praise/");

        request.addParam("targetId",targetid);
        request.addParam("target",targettype);
        request.addParam("enable","true");
        request.addParam("page",pagecount);
        request.addParam("size",20);
        request.addParam("sort","createdTime,desc");
        return request.getpraisearray(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONArray dataObj = responseInfo.getDataArr();
                List<Boolean> list = new ArrayList<Boolean>();
                list =JSON.parseArray(dataObj.toString() , Boolean.class);
                //JSON.parseArray(dataObj.toString(), )
/*                if (dataObj != null){
                    responseInfo.setBooleanList(JSON.parseObject(dataObj.toString(), PraiseStatus.class));
                }*/
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("commentpraise","onfailure");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("commentpraise","onfailure");

            }
        });
    }

    public Callback.Cancelable EditMineInfomation(String typename,String content,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/user/property");

        JSONObject obj = new JSONObject();
        try {
            obj.put("name",typename);
            obj.put("value", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.addParamjson(obj.toString());

        return request.putmineinfo(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    /**
     * 上传头像
     */
    public Callback.Cancelable UploadAvatar(File file, final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/image/upload");


        request.addParam("file", file);

        return request.postinfo(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONObject dataObj = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setAvatarResult(JSON.parseObject(dataObj.toString(), AvatarResult.class));
                }
                Log.e("上传完成",dataObj.toString());
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

            }
        });
    }

    public Callback.Cancelable GetPhoneSmsCode(String phone,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/sms/code");

        request.addParam("phone",phone);
        return request.postobject(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                //JSONObject dataObj1 = responseInfo.getDataObj();
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("SmsCode","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("SmsCode","error");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("SmsCode","onfailure");

            }
        });
    }

    public Callback.Cancelable SubmitPhoneSmsCode(String phone, String smscode,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/sms/code/check");
/*        request.addHeader("Authorization","Basic MToxMjM0NTY=");
        request.addHeader("Content-Type", "application/json;charset=UTF-8");*/
        request.addParam("phone",phone);
        request.addParam("code",smscode);
        return request.getobject(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONObject dataObj1 = responseInfo.getDataObj();
                callBack.onSuccess(requestInfo, responseInfo);
                Log.e("SmsCode","sucess");
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("SmsCode","error");

            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("SmsCode","onfailure");

            }
        });
    }

    /**
     * 修改密码
     */
    public Callback.Cancelable EditPersonalPassword(String oldPassword,String newPassword,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/user/password");

        request.addParam("oldPassword", oldPassword);
        request.addParam("newPassword", newPassword);
        Log.e("requesturl", request.toString()+"");
        return request.putmineinfo(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {

                Log.e("password","success");
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("password","error");
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("password","failure");
            }
        });
    }

    /**
     * 登录
     */

    public Callback.Cancelable LogInAccount(String phone,String password,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/user/login");
        JSONObject obj = new JSONObject();
        try {
            obj.put("username",phone);
            obj.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.addParamjson(obj.toString());
        Log.e("requesturl", request.toString()+"");
        return request.postinfo(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONObject dataObj = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setLoginResult(JSON.parseObject(dataObj.toString(), LoginResult.class));
                }
                Log.e("login","success");
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("login","error");
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("login","faillure");
            }
        });
    }

    public Callback.Cancelable RegisterAccount(String phone,String password,String sex, String city,String nickname,final NetRequestCallBack callBack){
        NetRequest request = new NetRequest("/user");
        JSONObject obj = new JSONObject();
        try {
            obj.put("username",phone);
            obj.put("nickname", nickname);
            obj.put("password", password);
            obj.put("sex", sex);
            obj.put("city",city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.addParamjson(obj.toString());
        Log.e("requesturl", request.toString()+"");
        return request.postinfo(new NetRequestCallBack() {
            @Override
            public void onSuccess(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                JSONObject dataObj = responseInfo.getDataObj();
                if (dataObj != null){
                    responseInfo.setLoginResult(JSON.parseObject(dataObj.toString(), LoginResult.class));
                }
                Log.e("login","success");
                callBack.onSuccess(requestInfo, responseInfo);
            }

            @Override
            public void onError(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("login","error");
            }

            @Override
            public void onFailure(NetRequestInfo requestInfo, NetResponseInfo responseInfo) {
                Log.e("login","faillure");
            }
        });
    }








}
