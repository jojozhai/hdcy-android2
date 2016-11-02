package com.hdcy.app.model;

import java.io.Serializable;

/**
 * Created by WeiYanGeorge on 2016-11-03.
 */

public class AvatarResult implements Serializable {
    private static final long serialVersionUID = 1L;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String result;
    private String content;
}
