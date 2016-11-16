package com.hdcy.app.model;

import java.io.Serializable;

/**
 * Created by WeiYanGeorge on 2016-11-16.
 */

public class WaiterInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private String weixin;

    private String phone;

    private String image;

    private String qrcode;

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
    public void setWeixin(String weixin){
        this.weixin = weixin;
    }
    public String getWeixin(){
        return this.weixin;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return this.image;
    }
    public void setQrcode(String qrcode){
        this.qrcode = qrcode;
    }
    public String getQrcode(){
        return this.qrcode;
    }

}
