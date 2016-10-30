package com.hdcy.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-08-17.
 */

public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private boolean business;

    private String tagId;

    private String tagName;

    private Date createdTime;

    private Date modifyTime;

    private boolean enable;

    private Date enableDate;

    private boolean top;

    private boolean linkOut;

    private String outLink;

    private String title;

    private String content;

    private String image;

    private String image2;

    private int readCount;

    private int readCountPlus;

    private int commentCount;

    private String principal;

    private List<TagInfos> tagInfos ;

    private String displayType;



    public void setId(int id){
        this.id = id;
    }
    public long getId(){
        return this.id;
    }
    public void setBusiness(boolean business){
        this.business = business;
    }
    public boolean getBusiness(){
        return this.business;
    }
    public void setTagId(String tagId){
        this.tagId = tagId;
    }
    public String getTagId(){
        return this.tagId;
    }
    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }
    public Date getCreatedTime(){
        return this.createdTime;
    }
    public void setModifyTime(Date modifyTime){
        this.modifyTime = modifyTime;
    }
    public Date getModifyTime(){
        return this.modifyTime;
    }
    public void setEnable(boolean enable){
        this.enable = enable;
    }
    public boolean getEnable(){
        return this.enable;
    }
    public void setEnableDate(Date enableDate){
        this.enableDate = enableDate;
    }
    public Date getEnableDate(){
        return this.enableDate;
    }
    public void setTop(boolean top){
        this.top = top;
    }
    public boolean getTop(){
        return this.top;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return this.image;
    }
    public void setImage2(String image2){
        this.image2 = image2;
    }
    public String getImage2(){
        return this.image2;
    }
    public void setReadCount(int readCount){
        this.readCount = readCount;
    }
    public int getReadCount(){
        return this.readCount;
    }
    public void setCommentCount(int commentCount){
        this.commentCount = commentCount;
    }
    public int getCommentCount(){
        return this.commentCount;
    }
    public void setPrincipal(String principal){
        this.principal = principal;
    }
    public String getPrincipal(){
        return this.principal;
    }
    public void setTagInfos(List<TagInfos> tagInfos){
        this.tagInfos = tagInfos;
    }
    public List<TagInfos> getTagInfos(){
        return this.tagInfos;
    }
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public boolean isLinkOut() {
        return linkOut;
    }

    public void setLinkOut(boolean linkOut) {
        this.linkOut = linkOut;
    }


    public String getOutLink() {
        return outLink;
    }

    public void setOutLink(String outLink) {
        this.outLink = outLink;
    }

    public int getReadCountPlus() {
        return readCountPlus;
    }

    public void setReadCountPlus(int readCountPlus) {
        this.readCountPlus = readCountPlus;
    }
    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }


}
