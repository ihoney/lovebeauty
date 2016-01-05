package com.lb.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-26
 * Time: 上午8:24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ScheduleOperateDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void abortOrder() {
        String sql = "UPDATE `order` SET state = '取消订单' WHERE state = '未付款' AND DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i') > bookTime";
        jdbcTemplate.update(sql);
    }

    public void changeBookTime() {
        String sql = "UPDATE book_time SET today = tomorrow, tomorrow = afterTomorrow, afterTomorrow = bigDayAfterTomorrow, bigDayAfterTomorrow = '000000000000'";
        jdbcTemplate.update(sql);
    }

    public void changeBookTimeHour() {
        String sql = "UPDATE book_time bt, ( SELECT CASE HOUR (NOW()) WHEN 10 THEN 1 WHEN 11 THEN 2 WHEN 12 THEN 3 WHEN 13 THEN 4 WHEN 14 THEN 5 WHEN 15 THEN 6 WHEN 16 THEN 7 WHEN 17 THEN 8 WHEN 18 THEN 9 WHEN 19 THEN 10 WHEN 20 THEN 11 WHEN 21 THEN 12 ELSE - 1 END AS curHour ) tab SET today = LPAD( SUBSTR(today, tab.curHour + 1), 12, '1' ) WHERE tab.curHour > - 1";
        jdbcTemplate.update(sql);
    }
}
