package com.hdcy.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-08-23.
 */

public class Comments  implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CommentsContent> commentsContents ;

    public void setContent(List<CommentsContent> content){
        this.commentsContents = content;
    }
    public List<CommentsContent> getContent(){
        return this.commentsContents;
    }

}
