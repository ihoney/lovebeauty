package com.lb.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-30
 * Time: 上午10:59
 * To change this template use File | Settings | File Templates.
 */
public class MD5Util {
    public static String getMD5(String str) {
        try {
            if (str != null || !"".equals(str.trim())) {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                BASE64Encoder base64en = new BASE64Encoder();
                String tmp = base64en.encode(md5.digest(str.getBytes()));
                tmp = tmp.replaceAll("\\W", "");
                return tmp;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }
}
