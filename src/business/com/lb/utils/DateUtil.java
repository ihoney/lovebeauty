package com.lb.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-30
 * Time: 下午7:24
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {

    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String cruDateStr() {
        return dateFormat.format(new Date());
    }

    public static String cruTimeStr() {
        return timeFormat.format(new Date());
    }
}
