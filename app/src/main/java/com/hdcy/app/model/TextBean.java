package com.hdcy.app.model;

/**
 * 登录页面图片标注对象
 */
public class TextBean {

    private String text;//内容
    private int fontsize;//字号
    private int fontX;//坐标   相对于图片左上角
    private int fontY;//y

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getFontsize() {
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    public int getFontX() {
        return fontX;
    }

    public void setFontX(int fontX) {
        this.fontX = fontX;
    }

    public int getFontY() {
        return fontY;
    }

    public void setFontY(int fontY) {
        this.fontY = fontY;
    }
}
