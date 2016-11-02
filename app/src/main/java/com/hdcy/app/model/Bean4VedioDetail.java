package com.hdcy.app.model;

import java.io.Serializable;

/**
 * 单个视频的详情数据
 *
 * chiwenheng
 */

public class Bean4VedioDetail implements Serializable {

    public  int id;
    public  String name;
    public  String url;
    public  boolean top;
    public  boolean enable;
    public  String image;
    public  int viewCountPlus;
    public  int viewCount;
    public  boolean live;
    public  boolean start;
    public  long startTime;
    public  String length;
    public  int commentCount;




    @Override
    public String toString() {
        return "Bean4VedioDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", top=" + top +
                ", enable=" + enable +
                ", image='" + image + '\'' +
                ", viewCountPlus=" + viewCountPlus +
                ", viewCount=" + viewCount +
                ", live=" + live +
                ", start=" + start +
                ", startTime=" + startTime +
                ", length='" + length + '\'' +
                ", commentCount=" + commentCount +
                '}';
    }
}
