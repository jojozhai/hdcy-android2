package com.hdcy.app.model;

import java.io.Serializable;

/**
 * Created by WeiYanGeorge on 2016-09-06.
 */

public class RootListInfo implements Serializable{
    private static final long serialVersionUID = 1L;

    private boolean last;

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    private boolean first;

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
