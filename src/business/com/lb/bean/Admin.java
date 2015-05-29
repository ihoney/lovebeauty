package com.lb.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-4-25
 * Time: 上午12:02
 * To change this template use File | Settings | File Templates.
 */
public class Admin implements Serializable {
    private int id;
    private String account;
    private String password;

    public Admin() {
    }

    public Admin(String account, String password) {
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
}
