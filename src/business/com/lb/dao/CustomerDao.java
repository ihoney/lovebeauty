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
public class CustomerDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findCustomer(String account, String password) {
        String sql = "select * from customer where account = ? and password= ?";
        return jdbcTemplate.queryForList(sql, new Object[]{account, password});
    }

    public void register(String account, String password, String regIp) {
        String sql = "insert into customer (account,password,regip,regtime) values (?,?,?,?) ";
        try {
            jdbcTemplate.update(sql, new Object[]{account, password, regIp, DateUtil.cruTimeStr()});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getCustomerCount() {
        String sql = "select count(1) from customer";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getCustomerByPage(int pageIndex, int pageSize) {
        String sql = "SELECT c.* " +
                " FROM " +
                " customer AS c limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void deleteCustomer(String customerId) {
        String sql = "delete from customer where id = " + customerId;
        jdbcTemplate.update(sql);
    }

    public void forbiddenCustomer(String customerId) {
        String sql = "update customer set forbidden = '是' where id = " + customerId;
        jdbcTemplate.update(sql);
    }

    public void jbCustomer(String sellerId, String customerId, String reason) {
        String sql = "insert into report_info (reportid,reportedid,reporttype,reason) values (?,?,?,?)";
        String updateSql = "update customer set jubao = jubao + 1";
        jdbcTemplate.update(sql, new Object[]{sellerId, customerId, 1, reason});
        jdbcTemplate.update(updateSql);
    }

    public void loginInfo(String account, String loginIp) {
        String sql = "update customer set loginip = '" + loginIp + "', logintime = '" + DateUtil.cruTimeStr() + "' where account ='" + account + "'";
        jdbcTemplate.update(sql);
    }

    public void reUseCustomer(String customerId) {
        String sql = "update customer set forbidden = '否' where id = " + customerId;
        jdbcTemplate.update(sql);
    }

    public void changeHeadImgMobile(String userId, String fileEName) {
        String sql = "update customer set headImg = '" + fileEName + "' where id = " + userId;
        jdbcTemplate.update(sql);
    }

    public void changeNickNameMobile(String userId, String nickName) {
        String sql = "update customer set nickName = '" + nickName + "' where id = " + userId;
        jdbcTemplate.update(sql);
    }

    public boolean changePasswordMobile(String userId, String oldPassword, String newPassword) {
        String sql = "select count(*) from customer where id = " + userId + " and password = '" + oldPassword + "'";
        int num = jdbcTemplate.queryForInt(sql);
        if (num == 0) {
            return false;
        }
        sql = "update customer set password = '" + newPassword + "' where id = " + userId;
        jdbcTemplate.update(sql);
        return true;
    }
}
