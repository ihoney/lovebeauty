package com.lb.dao;

import com.lb.bean.Demo;
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
public class DemoDao {

    @Resource
    private JdbcTemplate jdbcTemplate;


    public int getDemoCount() {
        String sql = "select count(1) from demo";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getDemoByPage(String sellerId, int pageIndex, int pageSize) {
        String sql = "SELECT " +
                " d.id, " +
                " d. NAME, " +
                " d.demoType, " +
                " d.picname, " +
                " d.price, " +
                " d.PreferentialPrice, " +
                " d.booktime, " +
                " d.description, " +
                " d.fileEName " +
                " FROM " +
                " demo AS d " +
                " WHERE " +
                " sellerid = " + sellerId + " limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void addDemo(Demo demo) {
        String sql = "INSERT INTO demo ( " +
                " sellerid, " +
                " NAME, " +
                " description, " +
                " picname, " +
                " fileEName, " +
                " price, " +
                " preferentialPrice, " +
                " booktime, " +
                " demoType " +
                ") " +
                "VALUES " +
                " (?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{demo.getSellerId(), demo.getName(), demo.getDescription(), demo.getPicName(), demo.getFileEName(), demo.getPrice(), demo.getPreferentialPrice(), demo.getBookTime(),
                demo.getDemoType()});
    }

    public void updateDemo(Demo demo) {
        StringBuffer sb = new StringBuffer("UPDATE demo SET NAME = ?, description =?, price = ?, PreferentialPrice =?, booktime = ?, demoType = ? ");
        if (demo.getFileEName() != null) {
            sb.append(", picname = ?, fileEName = ? ");
        }
        sb.append(" where id = " + demo.getId());

        if (demo.getFileEName() != null) {
            jdbcTemplate.update(sb.toString(), new Object[]{demo.getName(), demo.getDescription(), demo.getPrice(), demo.getPreferentialPrice(), demo.getBookTime(), demo.getDemoType(), demo.getPicName(),
                    demo.getFileEName()});
        } else {
            jdbcTemplate.update(sb.toString(), new Object[]{demo.getName(), demo.getDescription(), demo.getPrice(), demo.getPreferentialPrice(), demo.getBookTime(), demo.getDemoType()});
        }
    }

    public void deleteDemo(String demoId) {
        String sql = "delete from demo where id = " + demoId;
        jdbcTemplate.update(sql);
    }

    public List<Map<String, Object>> getDemoById(String demoId) {
        String sql = "select * from demo where id = " + demoId;
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> getSingleDemoById(String demoId) {
        String sql = "SELECT " +
                " demo.*, AVG(`comment`.score) AS avgScore " +
                " FROM " +
                " demo, " +
                " `comment` " +
                " WHERE " +
                " demo.id = " + demoId +
                " AND `comment`.demoid = demo.id";
        return jdbcTemplate.queryForMap(sql);
    }

    public List<Map<String, Object>> getDemoComment(String demoId) {
        String sql = "SELECT " +
                " b.id as customerid, " +
                " b.account, " +
                " a.score, " +
                " a. comment " +
                " FROM " +
                " `comment` a, " +
                " customer b " +
                " WHERE " +
                " b.id = a.customerid " +
                " AND a.demoid = " + demoId;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getDemoByPageAdmin(int pageIndex, int pageSize) {
        String sql = "SELECT d.*,s.name as sellerName from demo d, seller_validate_info s where d.sellerid = s.sellerid limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }
}
