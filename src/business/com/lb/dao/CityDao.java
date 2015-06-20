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
public class CityDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryCitiesMobile() {
        String sql = "SELECT id, NAME,serviceScope FROM city WHERE state = '开通'";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> searchByName(String name) {
        String sql = "select * from city where NAME like '%" + name + "%'";
        return jdbcTemplate.queryForList(sql);
    }
}
