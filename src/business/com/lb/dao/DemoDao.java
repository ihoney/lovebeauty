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
        String sql = "select count(*) from demo";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getDemoByPage(String sellerId, int pageIndex, int pageSize) {
        String sql = "SELECT d.*,e.nickName as empName   FROM   demo d left join employee e on e.id=d.employeeId  WHERE  d.sellerid = " + sellerId + " limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void addDemo(Demo demo) {
        String sql = "INSERT INTO demo ( " +
                " sellerid, " +
                " NAME, " +
                " employeeId, " +
                " description, " +
                " picname, " +
                " fileEName, " +
                " price, " +
                " preferentialPrice, " +
                " shopPrice, " +
                " timeConsuming, " +
                " keepTime, " +
                " booktime, " +
                " demoType " +
                ") " +
                "VALUES " +
                " (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{demo.getSellerId(), demo.getName(), demo.getEmpId(), demo.getDescription(), demo.getPicName(), demo.getFileEName(), demo.getPrice(), demo.getPreferentialPrice(),
                demo.getShopPrice(), demo.getTimeConsuming(), demo.getKeepTime(), demo.getBookTime(), demo.getDemoType()});
    }

    public void updateDemo(Demo demo) {
        StringBuffer sb = new StringBuffer("UPDATE demo SET NAME = ?,employeeId=?, description =?, price = ?, PreferentialPrice =?, shopPrice =?, timeConsuming=?,keepTime=?,booktime = ?, demoType = ? ");
        if (demo.getFileEName() != null) {
            sb.append(", picname = ?, fileEName = ? ");
        }
        sb.append(" where id = " + demo.getId());

        if (demo.getFileEName() != null) {
            jdbcTemplate.update(sb.toString(), new Object[]{demo.getName(), demo.getEmpId(), demo.getDescription(), demo.getPrice(), demo.getPreferentialPrice(), demo.getShopPrice(), demo.getTimeConsuming(),
                    demo.getKeepTime(), demo.getBookTime(), demo.getDemoType(), demo.getPicName(), demo.getFileEName()});
        } else {
            jdbcTemplate.update(sb.toString(), new Object[]{demo.getName(), demo.getEmpId(), demo.getDescription(), demo.getPrice(), demo.getPreferentialPrice(), demo.getShopPrice(), demo.getTimeConsuming(),
                    demo.getKeepTime(), demo.getBookTime(), demo.getDemoType()});
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
                " d.*,e.nickName, AVG(c.score) AS avgScore " +
                " FROM " +
                " demo d,employee e, " +
                " `comment` c " +
                " WHERE " +
                " d.id = " + demoId +
                " and e.id = d.employeeId " +
                " AND c.demoid = d.id";
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

    public List<Map<String, Object>> getEmployeesBySellerId(String sellerId) {
        String sql = "SELECT id, nickName FROM employee WHERE sellerId = " + sellerId;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> queryDemoByTypeMobile(String demoType, String orderType, String page, String pageSize) {
        int pageIndex = Integer.parseInt(page);
        int ps = Integer.parseInt(pageSize);
        StringBuffer stringBuffer = new StringBuffer("SELECT d.id, d. NAME, d.price, COUNT(o.demoid) AS count, d.fileEName FROM demo d left join `order` o on o.demoid = d.id where  d.demoType = '" + demoType + "' " +
                "GROUP BY d.id  order " +
                "by ");
        if ("1".equals(orderType)) {
            stringBuffer.append(" COUNT(o.demoid),d.price");
        } else if ("2".equals(orderType)) {
            stringBuffer.append(" COUNT(o.demoid)");
        } else if ("3".equals(orderType)) {
            stringBuffer.append(" price");
        } else if ("4".equals(orderType)) {
            stringBuffer.append(" price desc");
        }
        stringBuffer.append(" limit " + (pageIndex - 1) * ps + "," + ps);
        return jdbcTemplate.queryForList(stringBuffer.toString());
    }

    public List<Map<String, Object>> queryDemoDetailByIdMobile(String demoId) {
        String sql = "SELECT d.id,d.name,d.fileEName, d.price, d.shopPrice, d.description, COUNT(o.id) AS demoCount, d.timeConsuming, d.keepTime FROM demo d, `order` o WHERE d.id = " + demoId + " AND o.demoid = d.id AND o.state = '交易成功'";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> queryEmployeeDetailByIdMobile(String demoId) {
        String sql = "SELECT e.id, e.nickName, e.avgPrice, e.serverScope, e.headImg, e.majorScore, e.comScore, e.punctualScore, COUNT(o.id) AS empCount FROM demo d, employee e, " +
                "`order` o WHERE d.id = " + demoId + " AND e.id = d.employeeId AND o.demoid IN ( SELECT id FROM demo WHERE employeeId = e.id ) AND o.state = '交易成功'";
        return jdbcTemplate.queryForList(sql);
    }
}
