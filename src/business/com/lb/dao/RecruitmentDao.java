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
                " recruitment where applySellerId is null order by releaseTime desc limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void applyRecruitment(String recruitmentId, String sellerId) {
        String sql = "update recruitment set applySellerId = ?,applyTime = ?  where id = " + recruitmentId;
        jdbcTemplate.update(sql, new Object[]{sellerId, DateUtil.cruTimeStr()});
    }

    public int getRecruitmentCountAdmin() {
        String sql = "select count(*) from recruitment where checked = 0 and applySellerId is not null";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getRecruitmentByPageAdmin(int pageIndex, int pageSize) {
        String sql = "SELECT r.*, svi.`name` as sellerName,svi.telephone AS sellerTel FROM recruitment r LEFT JOIN seller_validate_info svi ON svi.sellerid = r.applySellerId WHERE r.checked = 0 AND r.applySellerId IS NOT NULL ORDER BY r.applyTime limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void checkPassed(String recruitmentId) {
        String sql = "update recruitment set checked  = 1 where id = " + recruitmentId;
        jdbcTemplate.update(sql);
    }

    public void checkRefuse(String recruitmentId) {
        String sql = "update recruitment set applySellerId = null, applyTime = null where id = " + recruitmentId;
        jdbcTemplate.update(sql);
    }

    public List<Map<String, Object>> onChecking(String sellerId) {
        String sql = "select * from recruitment where checked = 0 and applySellerId = " + sellerId;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> hasChecked(String sellerId) {
        String sql = "select * from recruitment where checked = 1 and applySellerId = " + sellerId;
        return jdbcTemplate.queryForList(sql);
    }
}
