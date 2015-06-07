package com.lb.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-6-7
 * Time: 下午8:52
 * To change this template use File | Settings | File Templates.
 */
public class MyStringUtils {
    public static int getLeftNumber(String hour) {
        int hourTmp = Integer.parseInt(hour);
        int cutNum = 0;
        switch (hourTmp) {
            case 10:
                cutNum = 0;
                break;
            case 11:
                cutNum = 1;
                break;
            case 12:
                cutNum = 2;
                break;
            case 13:
                cutNum = 3;
                break;

            case 14:
                cutNum = 4;
                break;
            case 15:
                cutNum = 5;
                break;
            case 16:
                cutNum = 6;
                break;
            case 17:
                cutNum = 7;
                break;
            case 18:
                cutNum = 8;
                break;
            case 19:
                cutNum = 9;
                break;
            case 20:
                cutNum = 10;
                break;
            case 21:
                cutNum = 11;
                break;
        }
        return cutNum;
    }

    public static int getRightNumber(String hour) {
        int hourTmp = Integer.parseInt(hour);
        int cutNum = 0;
        switch (hourTmp) {
            case 10:
                cutNum = 11;
                break;
            case 11:
                cutNum = 10;
                break;
            case 12:
                cutNum = 9;
                break;
            case 13:
                cutNum = 8;
                break;

            case 14:
                cutNum = 7;
                break;
            case 15:
                cutNum = 6;
                break;
            case 16:
                cutNum = 5;
                break;
            case 17:
                cutNum = 4;
                break;
            case 18:
                cutNum = 3;
                break;
            case 19:
                cutNum = 2;
                break;
            case 20:
                cutNum = 1;
                break;
            case 21:
                cutNum = 0;
                break;
        }
        return cutNum;
    }
}
