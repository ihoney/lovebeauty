package com.lb.bean;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-4-22
 * Time: 下午11:43
 * To change this template use File | Settings | File Templates.
 */

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 作品信息
 */
public class Demo implements Serializable {
    private int id;
    private int sellerId;
    private String name;
    private String empId;
    private String description;
    private BigDecimal price;
    private BigDecimal preferentialPrice;
    private int timeConsuming;
    private int keepTime;
    private String bookTime;
    private String picName;
    private String fileEName;
    private String demoType;

    public Demo() {
    }

    public Demo(String name, String empId, String description, BigDecimal price, BigDecimal preferentialPrice, int timeConsuming, int keepTime, String bookTime, String demoType) {
        this.name = name;
        this.empId = empId;
        this.description = description;
        this.price = price;
        this.preferentialPrice = preferentialPrice;
        this.timeConsuming = timeConsuming;
        this.keepTime = keepTime;
        this.bookTime = bookTime;
        this.demoType = demoType;
    }

    public Demo(int id, int sellerId, String name, String empId, String description, BigDecimal price, BigDecimal preferentialPrice, int timeConsuming, int keepTime, String bookTime,
                String demoType) {
        this.id = id;
        this.sellerId = sellerId;
        this.name = name;
        this.empId = empId;
        this.description = description;
        this.price = price;
        this.preferentialPrice = preferentialPrice;
        this.timeConsuming = timeConsuming;
        this.keepTime = keepTime;
        this.bookTime = bookTime;
        this.demoType = demoType;
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

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(BigDecimal preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public int getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(int timeConsuming) {
        this.timeConsuming = timeConsuming;
    }

    public int getKeepTime() {
        return keepTime;
    }

    public void setKeepTime(int keepTime) {
        this.keepTime = keepTime;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getFileEName() {
        return fileEName;
    }

    public void setFileEName(String fileEName) {
        this.fileEName = fileEName;
    }

    public String getDemoType() {
        return demoType;
    }

    public void setDemoType(String demoType) {
        this.demoType = demoType;
    }
}
