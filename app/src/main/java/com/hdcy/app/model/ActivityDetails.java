package com.hdcy.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-09-03.
 */

public class ActivityDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private String image;

    private String type;

    private String sortType;

    private String actType;

    private Date startTime;

    private Date endTime;

    private int hot;

    private String address;

    private String desc;

    private String comment;

    private boolean finish;

    private boolean enable;

    private int sponsorId;

    private String sponsorName;

    private String sponsorImage;

    private int browseval;

    private int fansval;

    private boolean top;

    private int topIndex;

    private boolean recommend;


    private int weighting;

    private List<String> images ;

    private String contactPhone;

    private String contactWeixin;

    private int peopleLimit;

    private double price;

    private int customerServiceId;

    private String waiterName;

    private String waiterPhone;

    private String waiterImage;

    private Date signStartTime;

    private Date signEndTime;

    private String province;

    private String city;

    private WaiterInfo waiterInfo;



    private boolean signFinish;

    public int getSignCount() {
        return signCount;
    }

    public void setSignCount(int signCount) {
        this.signCount = signCount;
    }

    private int signCount;


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
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return this.image;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setSortType(String sortType){
        this.sortType = sortType;
    }
    public String getSortType(){
        return this.sortType;
    }
    public void setActType(String actType){
        this.actType = actType;
    }
    public String getActType(){
        return this.actType;
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
    public void setHot(int hot){
        this.hot = hot;
    }
    public int getHot(){
        return this.hot;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
    public String getComment(){
        return this.comment;
    }
    public void setFinish(boolean finish){
        this.finish = finish;
    }
    public boolean getFinish(){
        return this.finish;
    }
    public void setEnable(boolean enable){
        this.enable = enable;
    }
    public boolean getEnable(){
        return this.enable;
    }
    public void setSponsorId(int sponsorId){
        this.sponsorId = sponsorId;
    }
    public int getSponsorId(){
        return this.sponsorId;
    }

    public void setBrowseval(int browseval){
        this.browseval = browseval;
    }
    public int getBrowseval(){
        return this.browseval;
    }
    public void setFansval(int fansval){
        this.fansval = fansval;
    }
    public int getFansval(){
        return this.fansval;
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
    public void setRecommend(boolean recommend){
        this.recommend = recommend;
    }
    public boolean getRecommend(){
        return this.recommend;
    }

    public void setWeighting(int weighting){
        this.weighting = weighting;
    }
    public int getWeighting(){
        return this.weighting;
    }
    public void setImages(List<String> images){
        this.images = images;
    }
    public List<String> getImages(){
        return this.images;
    }
    public void setContactPhone(String contactPhone){
        this.contactPhone = contactPhone;
    }
    public String getContactPhone(){
        return this.contactPhone;
    }
    public void setContactWeixin(String contactWeixin){
        this.contactWeixin = contactWeixin;
    }
    public String getContactWeixin(){
        return this.contactWeixin;
    }
    public void setPeopleLimit(int peopleLimit){
        this.peopleLimit = peopleLimit;
    }
    public int getPeopleLimit(){
        return this.peopleLimit;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return this.price;
    }
    public void setCustomerServiceId(int customerServiceId){
        this.customerServiceId = customerServiceId;
    }
    public int getCustomerServiceId(){
        return this.customerServiceId;
    }
    public void setWaiterName(String waiterName){
        this.waiterName = waiterName;
    }
    public String getWaiterName(){
        return this.waiterName;
    }
    public void setWaiterPhone(String waiterPhone){
        this.waiterPhone = waiterPhone;
    }
    public String getWaiterPhone(){
        return this.waiterPhone;
    }
    public void setWaiterImage(String waiterImage){
        this.waiterImage = waiterImage;
    }
    public String getWaiterImage(){
        return this.waiterImage;
    }
    public void setSignStartTime(Date signStartTime){
        this.signStartTime = signStartTime;
    }
    public Date getSignStartTime(){
        return this.signStartTime;
    }
    public void setSignEndTime(Date signEndTime){
        this.signEndTime = signEndTime;
    }
    public Date getSignEndTime(){
        return this.signEndTime;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public WaiterInfo getWaiterInfo() {
        return waiterInfo;
    }

    public void setWaiterInfo(WaiterInfo waiterInfo) {
        this.waiterInfo = waiterInfo;
    }

    public boolean isSignFinish() {
        return signFinish;
    }

    public void setSignFinish(boolean signFinish) {
        this.signFinish = signFinish;
    }


}

