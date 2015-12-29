package cn.alien95.alien95library.bean;

import cn.alien95.alien95library.config.API;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class Image {

    private int id;
    private int galleryclass;
    private String img;
    private long time;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGalleryclass() {
        return galleryclass;
    }

    public void setGalleryclass(int galleryclass) {
        this.galleryclass = galleryclass;
    }

    public String getImg() {
        return API.imageHost + img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
