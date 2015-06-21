package com.lb.bean;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-5-30
 * Time: 下午6:55
 * To change this template use File | Settings | File Templates.
 */
public class Employee {
    private int id;
    private int sellerId;
    private String nickName;
    private String sex;
    private BigDecimal majorScore;
    private BigDecimal comScore;
    private BigDecimal punctualScore;
    private BigDecimal avgPrice;
    private String serverScope;
    private String headImg;
    private String state;

    public Employee() {
    }

    public Employee(String nickName, String sex, String serverScope, String state) {
        this.nickName = nickName;
        this.sex = sex;
        this.serverScope = serverScope;
        this.state = state;
    }

    public Employee(int id, int sellerId, String nickName, String sex, String serverScope, String headImg, String state) {
        this.id = id;
        this.sellerId = sellerId;
        this.nickName = nickName;
        this.sex = sex;
        this.serverScope = serverScope;
        this.headImg = headImg;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public BigDecimal getMajorScore() {
        return majorScore;
    }

    public void setMajorScore(BigDecimal majorScore) {
        this.majorScore = majorScore;
    }

    public BigDecimal getComScore() {
        return comScore;
    }

    public void setComScore(BigDecimal comScore) {
        this.comScore = comScore;
    }

    public BigDecimal getPunctualScore() {
        return punctualScore;
    }

    public void setPunctualScore(BigDecimal punctualScore) {
        this.punctualScore = punctualScore;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getServerScope() {
        return serverScope;
    }

    public void setServerScope(String serverScope) {
        this.serverScope = serverScope;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
