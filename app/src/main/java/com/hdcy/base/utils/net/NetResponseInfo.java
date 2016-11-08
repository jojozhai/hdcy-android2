package com.hdcy.base.utils.net;


import com.hdcy.app.model.ActivityContent;
import com.hdcy.app.model.ActivityDetails;
import com.hdcy.app.model.ArticleInfo;
import com.hdcy.app.model.AvatarResult;
import com.hdcy.app.model.Bean4VedioBanner;
import com.hdcy.app.model.Bean4VedioDetail;
import com.hdcy.app.model.Comments;
import com.hdcy.app.model.CommentsContent;
import com.hdcy.app.model.Content;
import com.hdcy.app.model.GiftContent;
import com.hdcy.app.model.LeaderInfo;
import com.hdcy.app.model.LoginResult;
import com.hdcy.app.model.NewsArticleInfo;
import com.hdcy.app.model.NewsCategory;
import com.hdcy.app.model.PraiseResult;
import com.hdcy.app.model.PraiseStatus;
import com.hdcy.app.model.Replys;
import com.hdcy.app.model.Result;
import com.hdcy.app.model.RootListInfo;
import com.hdcy.app.model.UserBaseInfo;
import com.hdcy.app.model.VideoBasicInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-08-10.
 */

public class NetResponseInfo {
    private String code;
    private String message;
    private String result;
    private JSONObject dataObj;
    private JSONArray dataArr;
    private List<NewsCategory> newsCategoryList;
    private List<Content> contentList;
    private List<Comments> commentsList;
    public PraiseResult praiseResult;
    private List<ActivityContent> activityContentList;
    public CommentsContent commentsContent;
    private List<CommentsContent> commentsContentList;
    private List<NewsArticleInfo> newsArticleInfoList;
    public ArticleInfo articleInfo;
    public Result resultinfo;
    public ActivityDetails activityDetails;
    public Replys replys;
    public RootListInfo rootListInfo;
    public UserBaseInfo userBaseInfo;
    public List<LeaderInfo> leaderInfo;
    public AvatarResult avatarResult;

    /**  vedio banner Data */
    public JSONArray content;
    public List<Bean4VedioBanner> vedioBannerList;
    /**  视频详情页面的信息*/
    public Bean4VedioDetail mBean4VedioDetail;

    public List<VideoBasicInfo> videoBasicInfoList;


    public VideoBasicInfo videoBasicInfo;


    public List<GiftContent> giftContent;
    public PraiseStatus booleanList;

    public LoginResult loginResult;

    public PraiseStatus getBooleanList() {
        return booleanList;
    }

    public void setBooleanList(PraiseStatus booleanList) {
        this.booleanList = booleanList;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public JSONObject getDataObj() {
        return dataObj;
    }

    public void setDataObj(JSONObject dataObj) {
        this.dataObj = dataObj;
    }

    public JSONArray getDataArr() {
        return dataArr;
    }

    public void setDataArr(JSONArray dataArr) {
        this.dataArr = dataArr;
    }

    public List<NewsCategory> getNewsCategoryList() {
        return newsCategoryList;
    }

    public void setNewsCategoryList(List<NewsCategory> newsCategoryList) {
        this.newsCategoryList = newsCategoryList;
    }

    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
    }

    public PraiseResult getPraiseResult() {
        return praiseResult;
    }

    public void setPraiseResult(PraiseResult praiseResult) {
        this.praiseResult = praiseResult;
    }

    public List<ActivityContent> getActivityContentList() {
        return activityContentList;
    }

    public void setActivityContentList(List<ActivityContent> activityContentList) {
        this.activityContentList = activityContentList;
    }

    public List<CommentsContent> getCommentsContentList() {
        return commentsContentList;
    }

    public CommentsContent getCommentsContent() {
        return commentsContent;
    }

    public void setCommentsContent(CommentsContent commentsContent) {
        this.commentsContent = commentsContent;
    }

    public void setCommentsContentList(List<CommentsContent> commentsContentList) {
        this.commentsContentList = commentsContentList;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public List<NewsArticleInfo> getNewsArticleInfoList() {
        return newsArticleInfoList;
    }

    public void setNewsArticleInfoList(List<NewsArticleInfo> newsArticleInfoList) {
        this.newsArticleInfoList = newsArticleInfoList;
    }

    public void setArticleInfo(ArticleInfo articleInfo) {
        this.articleInfo = articleInfo;
    }

    public ArticleInfo getArticleInfo() {
        return articleInfo;
    }

    public Replys getReplys() {
        return replys;
    }

    public void setReplys(Replys replys) {
        this.replys = replys;
    }

    public ActivityDetails getActivityDetails() {
        return activityDetails;
    }

    public void setActivityDetails(ActivityDetails activityDetails) {
        this.activityDetails = activityDetails;
    }


    public Result getResultinfo() {
        return resultinfo;
    }

    public void setResultinfo(Result resultinfo) {
        this.resultinfo = resultinfo;
    }

    public RootListInfo getRootListInfo() {
        return rootListInfo;
    }

    public void setRootListInfo(RootListInfo rootListInfo) {
        this.rootListInfo = rootListInfo;
    }


    public UserBaseInfo getUserBaseInfo() {
        return userBaseInfo;
    }

    public void setUserBaseInfo(UserBaseInfo userBaseInfo) {
        this.userBaseInfo = userBaseInfo;
    }


    public List<LeaderInfo> getLeaderInfo() {
        return leaderInfo;
    }

    public void setLeaderInfo(List<LeaderInfo> leaderInfo) {
        this.leaderInfo = leaderInfo;
    }

    public List<GiftContent> getGiftContent() {
        return giftContent;
    }

    public void setGiftContent(List<GiftContent> giftContent) {
        this.giftContent = giftContent;
    }


    public List<VideoBasicInfo> getVideoBasicInfoList() {
        return videoBasicInfoList;
    }

    public void setVideoBasicInfoList(List<VideoBasicInfo> videoBasicInfoList) {
        this.videoBasicInfoList = videoBasicInfoList;
    }

    public VideoBasicInfo getVideoBasicInfo() {
        return videoBasicInfo;
    }

    public void setVideoBasicInfo(VideoBasicInfo videoBasicInfo) {
        this.videoBasicInfo = videoBasicInfo;
    }


    public AvatarResult getAvatarResult() {
        return avatarResult;
    }

    public void setAvatarResult(AvatarResult avatarResult) {
        this.avatarResult = avatarResult;
    }


    public LoginResult getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(LoginResult loginResult) {
        this.loginResult = loginResult;
    }





}
