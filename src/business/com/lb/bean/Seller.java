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
 * 卖家
 */
public class Seller implements Serializable {
    private int id;
    private String account;    //账号
    private String password;  //密码
    private String regIp;     //注册IP
    private Date regTime;     //注册时间
    private String loginIp;  //登录IP
    private Date loginTime;  //登录时间
    private boolean checked; //是否审核
    private Date checkedTime; //审核时间
    private int jubao;        //被举报次数

    public Seller() {
    }

    public Seller(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getJubao() {
        return jubao;
    }

    public void setJubao(int jubao) {
        this.jubao = jubao;
    }

    public Date getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(Date checkedTime) {
        this.checkedTime = checkedTime;
    }
}
