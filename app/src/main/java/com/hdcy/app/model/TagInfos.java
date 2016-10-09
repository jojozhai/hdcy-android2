package com.hdcy.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-08-17.
 */

public class TagInfos implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;

    private String parentId;

    private String parentName;

    private String name;

    private String image;

    private List<Children> children ;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setParentId(String parentId){
        this.parentId = parentId;
    }
    public String getParentId(){
        return this.parentId;
    }
    public void setParentName(String parentName){
        this.parentName = parentName;
    }
    public String getParentName(){
        return this.parentName;
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
    public void setChildren(List<Children> children){
        this.children = children;
    }
    public List<Children> getChildren(){
        return this.children;
    }

}
