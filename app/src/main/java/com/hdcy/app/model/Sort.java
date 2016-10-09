package com.hdcy.app.model;

import java.io.Serializable;

/**
 * Created by WeiYanGeorge on 2016-08-17.
 */

public class Sort implements Serializable {
    private static final long serialVersionUID = 1L;
    private String direction;

    private String property;

    private boolean ignoreCase;

    private String nullHandling;

    private boolean ascending;

    public void setDirection(String direction){
        this.direction = direction;
    }
    public String getDirection(){
        return this.direction;
    }
    public void setProperty(String property){
        this.property = property;
    }
    public String getProperty(){
        return this.property;
    }
    public void setIgnoreCase(boolean ignoreCase){
        this.ignoreCase = ignoreCase;
    }
    public boolean getIgnoreCase(){
        return this.ignoreCase;
    }
    public void setNullHandling(String nullHandling){
        this.nullHandling = nullHandling;
    }
    public String getNullHandling(){
        return this.nullHandling;
    }
    public void setAscending(boolean ascending){
        this.ascending = ascending;
    }
    public boolean getAscending(){
        return this.ascending;
    }


}
