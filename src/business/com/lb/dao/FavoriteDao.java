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
public class FavoriteDao {

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

    public List<Map<String, Object>> queryAllFavoriteMobile(String userId, String type, String page, String pageSize) {
        StringBuffer sb = new StringBuffer("");
        int pageTmp = Integer.parseInt(page);
        int pageSizeTmp = Integer.parseInt(pageSize);
        if ("0".equals(type)) {
            sb.append("SELECT entity.*, COUNT(o.id) AS demoCount FROM `order` o, ( SELECT d.id, d.`name`, d.price, d.fileEName FROM demo d WHERE d.id IN ( SELECT entityId FROM favorite WHERE type = 0 AND userId = " +
                    "" + userId + " )" +
                    ") entity WHERE o.demoid = entity.id AND o.state = '交易成功' GROUP BY entity.id ");
        } else {
            sb.append("SELECT entity.*, COUNT(o.id) AS empCount FROM `order` o, ( SELECT e.id, e.nickName, e.headImg, e.avgPrice FROM employee e WHERE e.id IN ( SELECT entityId FROM favorite WHERE type = 1 AND userId " +
                    "= " + userId + " )) entity WHERE o.state = '交易成功' and o.demoId IN ( SELECT id FROM demo WHERE employeeId = entity.id ) GROUP BY entity.id");
        }
        sb.append(" limit " + (pageTmp - 1) * pageSizeTmp + "," + pageSizeTmp);
        return jdbcTemplate.queryForList(sb.toString());
    }

    public void addFavoriteMobile(String userId, String type, String entityId) {
        String sql = "insert into favorite (type,userId,entityId) values (?,?,?)";
        jdbcTemplate.update(sql, new Object[]{type, userId, entityId});
    }

    public void deleteFavoriteMobile(String userId, String type, String entityId) {
        String sql = "delete from favorite where type=? and userId = ? and entityId=?";
        jdbcTemplate.update(sql, new Object[]{type, userId, entityId});
    }
}
