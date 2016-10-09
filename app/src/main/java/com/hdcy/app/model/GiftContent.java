package com.hdcy.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-09-19.
 */

public class GiftContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private List<String> images ;

    private int point;

    private int stock;

    private int used;

    private String desc;

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
    public void setImages(List<String> images){
        this.images = images;
    }
    public List<String> getImages(){
        return this.images;
    }
    public void setPoint(int point){
        this.point = point;
    }
    public int getPoint(){
        return this.point;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
    public int getStock(){
        return this.stock;
    }
    public void setUsed(int used){
        this.used = used;
    }
    public int getUsed(){
        return this.used;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }


}
