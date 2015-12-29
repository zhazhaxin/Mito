package cn.alien95.alien95library.bean;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class ImageRespond {
    private boolean status;
    private int total;
    private Image[] tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Image[] getTngou() {
        return tngou;
    }

    public void setTngou(Image[] tngou) {
        this.tngou = tngou;
    }
}
