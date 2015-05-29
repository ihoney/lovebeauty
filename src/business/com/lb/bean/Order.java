package com.lb.bean;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-4-22
 * Time: 下午11:43
 * To change this template use File | Settings | File Templates.
 */

import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 */
public class Order implements Serializable {
    private int id;
    private String userId;
    private String sellerId;
    private Date state;
    private Date demoId;
    private String orderTime;
    private String payTime;

    public Order() {
    }

    public Order(int id, String userId, String sellerId, Date state, Date demoId, String orderTime, String payTime) {
        this.id = id;
        this.userId = userId;
        this.sellerId = sellerId;
        this.state = state;
        this.demoId = demoId;
        this.orderTime = orderTime;
        this.payTime = payTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Date getState() {
        return state;
    }

    public void setState(Date state) {
        this.state = state;
    }

    public Date getDemoId() {
        return demoId;
    }

    public void setDemoId(Date demoId) {
        this.demoId = demoId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
}
