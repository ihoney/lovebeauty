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

    public static String toHexString(String key) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            char[] md = key.toCharArray();
            byte[] arr = new byte[md.length];
            for (int i = 0; i < md.length; i++)
                arr[i] = (byte) md[i];
            byte[] bs = md5.digest(arr);
            StringBuilder sb = new StringBuilder();
            int i;
            char c;
            byte b;
            int max = 0;
            for (i = 0; i < bs.length; i++) {
                max++;
                if (max >= Integer.MAX_VALUE) {
                    break;
                }
                b = bs[i];
                c = Character.forDigit((b >>> 4) & 0x0F, 16);
                sb.append(c);
                c = Character.forDigit(b & 0x0F, 16);
                sb.append(c);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}
