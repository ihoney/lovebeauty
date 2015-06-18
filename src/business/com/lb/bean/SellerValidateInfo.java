package com.lb.bean;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-5-11
 * Time: 下午11:07
 * To change this template use File | Settings | File Templates.
 */

/**
 * 商家认证信息
 */
public class SellerValidateInfo {
    private int id;
    private int sellerId;     //卖家Id
    private String name;        //姓名
    private String sex;        //性别
    private String birthday;     //出生日期
    private String email;        //电子邮件
    private String headImgStr;     //头像保存名称
    private String identify;     //身份证号
    private String identifyImgStr; //证件图片名称
    private String shopName;   //店铺名称
    private String address;    //店铺地址
    private String cityId;    //所在城市
    private String payAccount; //支付宝账号
    private String alipayPublicKey; //支付宝账号
    private String alipayPrivateKey; //支付宝账号
    private String alipayPid; //支付宝账号
    private String serviceScope;//服务范围
    private String telephone;   //手机号

    public SellerValidateInfo() {
    }

    public SellerValidateInfo(int sellerId, String name, String sex, String birthday, String email, String identify, String shopName, String address, String cityId, String payAccount, String alipayPublicKey,
                              String alipayPrivateKey,
                              String alipayPid,
                              String serviceScope, String telephone) {
        this.sellerId = sellerId;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
        this.identify = identify;
        this.shopName = shopName;
        this.address = address;
        this.cityId = cityId;
        this.payAccount = payAccount;
        this.alipayPublicKey = alipayPublicKey;
        this.alipayPrivateKey = alipayPrivateKey;
        this.alipayPid = alipayPid;
        this.serviceScope = serviceScope;
        this.telephone = telephone;
    }

    public SellerValidateInfo(int id, int sellerId, String headImgStr, String identifyImgStr, String name, String sex, String birthday, String email, String identify, String shopName, String address,
                              String cityId, String payAccount,
                              String alipayPublicKey, String alipayPrivateKey,
                              String alipayPid,
                              String serviceScope, String telephone) {
        this.id = id;
        this.sellerId = sellerId;
        this.headImgStr = headImgStr;
        this.identifyImgStr = identifyImgStr;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
        this.identify = identify;
        this.shopName = shopName;
        this.address = address;
        this.cityId = cityId;
        this.payAccount = payAccount;
        this.alipayPublicKey = alipayPublicKey;
        this.alipayPrivateKey = alipayPrivateKey;
        this.alipayPid = alipayPid;
        this.serviceScope = serviceScope;
        this.telephone = telephone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadImgStr() {
        return headImgStr;
    }

    public void setHeadImgStr(String headImgStr) {
        this.headImgStr = headImgStr;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getIdentifyImgStr() {
        return identifyImgStr;
    }

    public void setIdentifyImgStr(String identifyImgStr) {
        this.identifyImgStr = identifyImgStr;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getServiceScope() {
        return serviceScope;
    }

    public void setServiceScope(String serviceScope) {
        this.serviceScope = serviceScope;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getAlipayPrivateKey() {
        return alipayPrivateKey;
    }

    public void setAlipayPrivateKey(String alipayPrivateKey) {
        this.alipayPrivateKey = alipayPrivateKey;
    }

    public String getAlipayPid() {
        return alipayPid;
    }

    public void setAlipayPid(String alipayPid) {
        this.alipayPid = alipayPid;
    }
}
