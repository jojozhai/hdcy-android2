package com.hdcy.app.model;

import java.io.Serializable;

/**
 * Created by WeiYanGeorge on 2016-09-04.
 */

public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    private String result;

    private boolean content;

    public void setResult(String result){
        this.result = result;
    }
    public String getResult(){
        return this.result;
    }
    public void setContent(boolean content){
        this.content = content;
    }
    public boolean getContent(){
        return this.content;
    }


}

