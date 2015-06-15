package com.lb.dao;

import com.lb.utils.DateUtil;
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
public class PrivateOrderDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public int getOrderCount(String sellerId) {
        String sql = "select count(1) from `privateorder` where sellerid = " + sellerId;
        return jdbcTemplate.queryForInt(sql);
    }

    public int getOrderCount() {
        String sql = "select count(1) from `privateorder` where sellerid is null and state = 0";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getOrderByPage(String sellerId, int pageIndex, int pageSize) {
        String sql = "SELECT " +
                " p.*, c.account " +
                " FROM " +
                " privateorder p, " +
                " customer c " +
                " WHERE " +
                " c.id = p.userid " +
                " AND p.sellerid = " + sellerId + " order by state limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getOrderByPage(int pageIndex, int pageSize) {
        String sql = "SELECT " +
                " p.*, c.account " +
                " FROM " +
                " privateorder p, " +
                " customer c " +
                " WHERE " +
                " c.id = p.userid and sellerid is null and state = 0 order by state " + " limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void submitOrder(String userId, String sellerId, String price, String description, String fileEName) {
        String sql = "insert into privateorder (userid,sellerid,filename,state,description,price,ordertime) values (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{userId, sellerId, fileEName, 1, description, price, DateUtil.cruTimeStr()});

    }

    public void abortOrder(String orderId) {
        String sql = "update `privateorder` set state = 0,sellerid = null  where id =" + orderId;
        jdbcTemplate.update(sql);
    }

    public void orderSure(String orderId) {
        String sql = "update `privateorder` set state = 2,successtime='" + DateUtil.cruTimeStr() + "' where id = " + orderId;
        jdbcTemplate.update(sql);
    }

    public void grabOrder(String sellerId, String orderId) {
        String sql = "update `privateorder` set state = 1,sellerid = " + sellerId + " where id = " + orderId;
        jdbcTemplate.update(sql);
    }

    public List<Map<String, Object>> getPrivateOrderById(String orderId) {
        String sql = "SELECT " +
                " p.*, c.account " +
                " FROM " +
                " privateorder p, " +
                " customer c " +
                " WHERE " +
                " c.id = p.userid and p.id= " + orderId;
        return jdbcTemplate.queryForList(sql);
    }

    public int getOrderCountAdmin() {
        String sql = "select count(1) from `privateorder`";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getOrderByPageAdmin(int pageIndex, int pageSize) {
        String sql = "SELECT " +
                " p.*, c.account as userAccount,s.account as sellerAccount " +
                " FROM " +
                " privateorder p, " +
                " customer c, " +
                " seller s " +
                " WHERE " +
                " c.id = p.userid " +
                " and s.id = p.sellerid " +
                " order by state limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void addPrivateOrderMobile(String userId, String cityId, String serverAddress, String description, String fileEName) {
        String sql = "INSERT INTO privateorder ( " +
                " userid, " +
                " cityId, " +
                " reqPicName, " +
                " serverAddress, " +
                " ordertime, " +
                " description " +
                ") " +
                "VALUES " +
                "(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{userId, cityId, fileEName, serverAddress, DateUtil.cruTimeStr(), description});
    }

    public List<Map<String, Object>> queryPrivateOrdersMobile(String userId) {
        return null;
    }
}
