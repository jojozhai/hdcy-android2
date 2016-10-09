package com.hdcy.app.model;

import java.io.Serializable;

/**
 * 视频直播上的banner 栏 数据
 *
 * 以及下面的vedio数据 都是同一个实体
 * chiwenheng
 */

public class Bean4VedioBanner implements Serializable {

    public int id;
    public String name;
    public String url;
    public String url2;
    public boolean top;
    public boolean enable;
    public String image;
    public int viewCountPlus;
    public int viewCount;
    public boolean live;
    public String start;
    public long startTime;
    public String length;
    public int commentCount;
    public String desc;


    @Override
    public String toString() {
        return "Bean4VedioBanner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", url2='" + url2 + '\'' +
                ", top=" + top +
                ", enable=" + enable +
                ", image='" + image + '\'' +
                ", viewCountPlus=" + viewCountPlus +
                ", viewCount=" + viewCount +
                ", live=" + live +
                ", start='" + start + '\'' +
                ", startTime=" + startTime +
                ", length='" + length + '\'' +
                ", commentCount=" + commentCount +
                ", desd='" + desc + '\'' +
                '}';
    }
}
