package com.hdcy.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * 资讯分类实体类
 * Created by WeiYanGeorge on 2016-08-15.
 */

public class NewsCategory implements Serializable {
    /**
     *
     */
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
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }



}
