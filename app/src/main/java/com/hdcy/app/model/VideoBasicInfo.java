package com.hdcy.app.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by WeiYanGeorge on 2016-10-17.
 */

public class VideoBasicInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private String url;

    private String url2;

    private boolean top;

    private boolean enable;

    private String image;

    private String streamId;

    private int viewCountPlus;

    private int viewCount;

    private boolean live;

    private String liveLink;

    private String liveState;

    private Date startTime;

    private Date endTime;

    private String length;

    private int commentCount;

    private String desc;

    private String sponsorName;

    private String sponsorImage;

    private String sponsorId;

    private boolean liveForApp;

    private boolean liveForWeixin;

    private boolean replay;

    private String replayId;


    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setUrl2(String url2){
        this.url2 = url2;
    }
    public String getUrl2(){
        return this.url2;
    }
    public void setTop(boolean top){
        this.top = top;
    }
    public boolean getTop(){
        return this.top;
    }
    public void setEnable(boolean enable){
        this.enable = enable;
    }
    public boolean getEnable(){
        return this.enable;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return this.image;
    }
    public void setStreamId(String streamId){
        this.streamId = streamId;
    }
    public String getStreamId(){
        return this.streamId;
    }
    public void setViewCountPlus(int viewCountPlus){
        this.viewCountPlus = viewCountPlus;
    }
    public int getViewCountPlus(){
        return this.viewCountPlus;
    }
    public void setViewCount(int viewCount){
        this.viewCount = viewCount;
    }
    public int getViewCount(){
        return this.viewCount;
    }
    public void setLive(boolean live){
        this.live = live;
    }
    public boolean getLive(){
        return this.live;
    }
    public void setLiveState(String liveState){
        this.liveState = liveState;
    }
    public String getLiveState(){
        return this.liveState;
    }
    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }
    public Date getStartTime(){
        return this.startTime;
    }
    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }
    public Date getEndTime(){
        return this.endTime;
    }
    public void setLength(String length){
        this.length = length;
    }
    public String getLength(){
        return this.length;
    }
    public void setCommentCount(int commentCount){
        this.commentCount = commentCount;
    }
    public int getCommentCount(){
        return this.commentCount;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
    public boolean isTop() {
        return top;
    }

    public boolean isEnable() {
        return enable;
    }

    public boolean isLive() {
        return live;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getSponsorImage() {
        return sponsorImage;
    }

    public void setSponsorImage(String sponsorImage) {
        this.sponsorImage = sponsorImage;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public boolean isLiveForApp() {
        return liveForApp;
    }

    public void setLiveForApp(boolean liveForApp) {
        this.liveForApp = liveForApp;
    }

    public boolean isLiveForWeixin() {
        return liveForWeixin;
    }

    public void setLiveForWeixin(boolean liveForWeixin) {
        this.liveForWeixin = liveForWeixin;
    }

    public boolean isReplay() {
        return replay;
    }

    public void setReplay(boolean replay) {
        this.replay = replay;
    }

    public String getReplayId() {
        return replayId;
    }

    public void setReplayId(String replayId) {
        this.replayId = replayId;
    }


    public String getLiveLink() {
        return liveLink;
    }

    public void setLiveLink(String liveLink) {
        this.liveLink = liveLink;
    }
}
