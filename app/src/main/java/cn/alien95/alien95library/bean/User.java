package cn.alien95.alien95library.bean;

/**
 * Created by llxal on 2015/12/21.
 */
public class User {
    private int face;
    private String name;

    public User(int face, String name) {
        this.face = face;
        this.name = name;
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
