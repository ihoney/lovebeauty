package com.lb.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private static SimpleDateFormat mdFormat = new SimpleDateFormat("M.d");
    private static SimpleDateFormat ymdhmFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat payNum = new SimpleDateFormat("yyyyMMdd");
    public static Map<String, String> hourMap = new HashMap<String, String>();

    public static String cruDateStr() {
        return dateFormat.format(new Date());
    }

    public static String cruTimeStr() {
        return timeFormat.format(new Date());
    }

    public static String getMonthAndDateStr(Date date) {
        return mdFormat.format(date);
    }

    public static String getYmdhmFormat(Date date) {
        return ymdhmFormat.format(date);
    }

    static {
        hourMap.put("10", "0___________");
        hourMap.put("11", "_0__________");
        hourMap.put("12", "__0_________");
        hourMap.put("13", "___0________");
        hourMap.put("14", "____0_______");
        hourMap.put("15", "_____0______");
        hourMap.put("16", "______0_____");
        hourMap.put("17", "_______0____");
        hourMap.put("18", "________0___");
        hourMap.put("19", "_________0__");
        hourMap.put("20", "__________0_");
        hourMap.put("21", "___________0");
    }

    public static String getPayNum() {
        return payNum.format(new Date());
    }
}
