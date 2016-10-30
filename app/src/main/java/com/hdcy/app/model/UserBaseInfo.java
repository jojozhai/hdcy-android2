package com.hdcy.app.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by WeiYanGeorge on 2016-09-09.
 */

public class UserBaseInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private String username;

    private String password;

    private String nickname;

    private String realname;

    private String mobile;

    private String sex;

    private String weixin;

    private String weixinOpenId;

    private String weixinUnionId;

    private String city;

    private String country;

    private String province;

    private String address;

    private String headimgurl;

    private int point;

    private int beans;

    private int unreadMessages;

    private String level;

    private String tags;

    private Date birthday;

    private Date vipExpired;

    private boolean vipValid;

    private boolean vip;

    private String car;

    private int money;

    private int moneyPlus;

    private String job;

    private int participationCount;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname(){
        return this.nickname;
    }
    public void setRealname(String realname){
        this.realname = realname;
    }
    public String getRealname(){
        return this.realname;
    }
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public String getMobile(){
        return this.mobile;
    }
    public void setSex(String sex){
        this.sex = sex;
    }
    public String getSex(){
        return this.sex;
    }
    public void setWeixin(String weixin){
        this.weixin = weixin;
    }
    public String getWeixin(){
        return this.weixin;
    }
    public void setWeixinOpenId(String weixinOpenId){
        this.weixinOpenId = weixinOpenId;
    }
    public String getWeixinOpenId(){
        return this.weixinOpenId;
    }
    public void setWeixinUnionId(String weixinUnionId){
        this.weixinUnionId = weixinUnionId;
    }
    public String getWeixinUnionId(){
        return this.weixinUnionId;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public String getCountry(){
        return this.country;
    }
    public void setProvince(String province){
        this.province = province;
    }
    public String getProvince(){
        return this.province;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
    public void setHeadimgurl(String headimgurl){
        this.headimgurl = headimgurl;
    }
    public String getHeadimgurl(){
        return this.headimgurl;
    }
    public void setPoint(int point){
        this.point = point;
    }
    public int getPoint(){
        return this.point;
    }
    public void setBeans(int beans){
        this.beans = beans;
    }
    public int getBeans(){
        return this.beans;
    }
    public void setUnreadMessages(int unreadMessages){
        this.unreadMessages = unreadMessages;
    }
    public int getUnreadMessages(){
        return this.unreadMessages;
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
    public void setBirthday(Date birthday){
        this.birthday = birthday;
    }
    public Date getBirthday(){
        return this.birthday;
    }
    public void setVipExpired(Date vipExpired){
        this.vipExpired = vipExpired;
    }
    public Date getVipExpired(){
        return this.vipExpired;
    }
    public void setVipValid(boolean vipValid){
        this.vipValid = vipValid;
    }
    public boolean getVipValid(){
        return this.vipValid;
    }
    public void setVip(boolean vip){
        this.vip = vip;
    }
    public boolean getVip(){
        return this.vip;
    }
    public void setCar(String car){
        this.car = car;
    }
    public String getCar(){
        return this.car;
    }
    public void setMoney(int money){
        this.money = money;
    }
    public int getMoney(){
        return this.money;
    }
    public void setMoneyPlus(int moneyPlus){
        this.moneyPlus = moneyPlus;
    }
    public int getMoneyPlus(){
        return this.moneyPlus;
    }
    public void setJob(String job){
        this.job = job;
    }
    public String getJob(){
        return this.job;
    }
    public void setParticipationCount(int participationCount){
        this.participationCount = participationCount;
    }
    public int getParticipationCount(){
        return this.participationCount;
    }
}
