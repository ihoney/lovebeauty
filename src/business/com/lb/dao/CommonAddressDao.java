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
public class CommonAddressDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryAllAddressMobile(String userId) {
        String sql = "SELECT * FROM common_address WHERE userid = " + userId;
        return jdbcTemplate.queryForList(sql);
    }

    public void addAddressMobile(String userId, String address) {
        String sql = "insert into common_address (userid,address) values (?,?)";
        jdbcTemplate.update(sql, new Object[]{userId, address});
    }

    public void deleteAddressMobile(String addressId) {
        String sql = "delete from common_address where id = " + addressId;
        jdbcTemplate.update(sql);
    }
}
