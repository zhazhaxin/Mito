package cn.alien95.alien95library.model.bean;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class Image {

    private String thumbUrl;
    private String smallThumbUrl;
    private String pic_url;
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getSmallThumbUrl() {
        return smallThumbUrl;
    }

    public void setSmallThumbUrl(String smallThumbUrl) {
        this.smallThumbUrl = smallThumbUrl;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
