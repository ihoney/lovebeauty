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
public class RecruitmentDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void addRecruitmentMobile(String city, String name, String telephone, String workYear, String openShop, String hopeSalary, String comFocus) {
        String sql = "INSERT INTO recruitment ( city, NAME, telephone, workYear, openShop, hopeSalary, comFocus,releaseTime ) VALUES (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{city, name, telephone, workYear, openShop, hopeSalary, comFocus, DateUtil.cruTimeStr()});
    }

    public int getRecruitmentCount() {
        String sql = "select count(*) from recruitment";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getRecruitmentByPage(int pageIndex, int pageSize) {
        String sql = "SELECT * " +
                " FROM " +
                " recruitment order by releaseTime desc limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }
}
