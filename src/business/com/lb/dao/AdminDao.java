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
public class AdminDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findAdmin(String account, String password) {
        String sql = "select id,account,password from manager where account = ? and password= ?";
        return jdbcTemplate.queryForList(sql, account, password);
    }

    public int getCityCount() {
        String sql = "select count(1) from city";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getCityByPage(int pageIndex, int pageSize) {
        String sql = "select * from city order by state desc limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void addCity(String cityName) {
        String sql = "insert into city (name,opentime,state) values (?,?,?)";
        jdbcTemplate.update(sql, new Object[]{cityName, DateUtil.cruTimeStr(), 1});
    }

    public void changeCityState(String cityId, String state) {
        String sql;
        if ("0".equals(state)) {
            sql = "update city set state = 0, opentime='',stoptime = '" + DateUtil.cruTimeStr() + "' where id=" + cityId;
        } else {
            sql = "update city set state = 1,opentime = '" + DateUtil.cruTimeStr() + "', stoptime='' where id=" + cityId;
        }
        jdbcTemplate.update(sql);
    }
}
