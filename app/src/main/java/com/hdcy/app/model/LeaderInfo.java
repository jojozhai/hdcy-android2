package com.hdcy.app.model;

import java.io.Serializable;

/**
 * Created by WeiYanGeorge on 2016-09-10.
 */

public class LeaderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int userId;

    private String nickname;

    private String headimgurl;

    private String level;

    private String tags;

    private String intro;

    private String status;

    private boolean top;

    private int topIndex;

    private String topImage;

    private int participationCount;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId(){
        return this.userId;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname(){
        return this.nickname;
    }
    public void setHeadimgurl(String headimgurl){
        this.headimgurl = headimgurl;
    }
    public String getHeadimgurl(){
        return this.headimgurl;
    }
    public void setLevel(String level){
        this.level = level;
    }
    public String getLevel(){
        return this.level;
    }
    public void setTags(String tags){
        this.tags = tags;
    }
    public String getTags(){
        return this.tags;
    }
    public void setIntro(String intro){
        this.intro = intro;
    }
    public String getIntro(){
        return this.intro;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setTop(boolean top){
        this.top = top;
    }
    public boolean getTop(){
        return this.top;
    }
    public void setTopIndex(int topIndex){
        this.topIndex = topIndex;
    }
    public int getTopIndex(){
        return this.topIndex;
    }
    public void setTopImage(String topImage){
        this.topImage = topImage;
    }
    public String getTopImage(){
        return this.topImage;
    }
    public void setParticipationCount(int participationCount){
        this.participationCount = participationCount;
    }
    public int getParticipationCount(){
        return this.participationCount;
    }
}
