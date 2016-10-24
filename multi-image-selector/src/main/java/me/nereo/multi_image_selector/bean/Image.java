package me.nereo.multi_image_selector.bean;

/**
 * 图片实体
 * Created by Nereo on 2015/4/7.
 */
public class Image {
    public String path;
    public String thumbPath;// 缩略图路径
    public String name;
    public long time;

    public Image(String path, String thumbPath, String name, long time) {
        this.path = path;
        this.thumbPath = thumbPath;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
