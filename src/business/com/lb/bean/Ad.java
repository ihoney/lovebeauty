package com.lb.bean;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-5-31
 * Time: 上午12:01
 * To change this template use File | Settings | File Templates.
 */
public class Ad {
    private int id;
    private String type;
    private String picName;
    private String url;
    private String backup;
    private String state;

    public Ad() {
    }

    public Ad(String type, String url, String backup) {
        this.type = type;
        this.url = url;
        this.backup = backup;
    }

    public Ad(int id, String type, String picName, String url, String backup) {
        this.id = id;
        this.type = type;
        this.picName = picName;
        this.url = url;
        this.backup = backup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
