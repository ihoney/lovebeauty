package com.lb.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-27
 * Time: 下午10:17
 * To change this template use File | Settings | File Templates.
 */
public class Customer implements Serializable {
    private int id;             //主键
    private String account;     //（手机号）账户（需校验11位）
    private String password; //密码
    private String sex; //性别
    private Date birthday; //出生日期
    private String email; //电子邮件
    private String city;    //所在城市
    private String regIp;    //注册ip
    private String loginIp;    //登录ip
    private Date regTime;     //注册时间
    private Date loginTime;  //登录时间
    private boolean forbidden;//是否被禁用 0没被禁用 1禁用
    private int jubao;    //被举报次数

    public Customer() {
    }

    public Customer(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public boolean isForbidden() {
        return forbidden;
    }

    public void setForbidden(boolean forbidden) {
        this.forbidden = forbidden;
    }

    public int getJubao() {
        return jubao;
    }

    public void setJubao(int jubao) {
        this.jubao = jubao;
    }
}
