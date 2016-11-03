package com.hdcy.app.model;

import java.io.Serializable;

/**
 * Created by WeiYanGeorge on 2016-11-03.
 */

public class SelectedinterestInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private boolean is_selected;

    private int position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean is_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
