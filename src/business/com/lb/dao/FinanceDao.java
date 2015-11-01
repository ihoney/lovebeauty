package com.lb.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-26
 * Time: 上午8:24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class FinanceDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getCycleFinanceByPage(String sellerId, int pageIndex, int pageSize) {
        String sql = "SELECT id, sellerId, tradeCycle, transferStatus, orderCount, orderPrice,applyTime FROM trade_record WHERE sellerId =  " + sellerId +
                " ORDER BY " +
                " transferStatus limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public int getCycleFinanceCount(String sellerId) {
        String sql = "select count(*) from trade_record where sellerId = " + sellerId;
        return jdbcTemplate.queryForInt(sql);
    }

    public void applyTransfer(String financeRecordId, String code) {
        String sql = "update trade_record set transferStatus = " + code + ",applyTime=now() where id=" + financeRecordId;
        jdbcTemplate.update(sql);
    }

    public int getCycleFinanceCountAdmin() {
        String sql = "select count(*) from trade_record where transferStatus = 1";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getCycleFinanceAdminByPage(int pageIndex, int pageSize) {
        String sql = "SELECT a.id,a.sellerId, b.account, c.payaccount, a.tradeCycle, a.orderCount, a.orderPrice, a.applyTime FROM trade_record a, seller b, seller_validate_info c WHERE a.transferStatus = 1 AND b.id = a" +
                ".sellerId AND c.sellerid = a.sellerId ORDER BY a.applyTime limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }
}
