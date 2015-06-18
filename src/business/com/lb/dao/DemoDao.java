package com.lb.dao;

import com.lb.bean.Demo;
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
        StringBuffer stringBuffer = new StringBuffer("SELECT d.id, d. NAME, d.price, COUNT(o.demoid) AS demoCount, d.fileEName FROM demo d left join `order` o on o.demoid = d.id and o.state = '交易成功' where  d.demoType " +
                "" +
                "= '"
                + demoType + "' " +
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

    public List<Map<String, Object>> queryDemoDetailByIdMobile(String demoId, String userId) {
        String sql = "SELECT d.id, d. NAME, d.fileEName, d.price, d.shopPrice, d.description, COUNT(o.id) AS demoCount, d.timeConsuming, d.keepTime, count(DISTINCT f.id) AS isFavorite FROM demo d LEFT JOIN favorite f " +
                "ON f.type = 0 AND f.userId = " + userId + " AND f.entityId = d.id LEFT JOIN `order` o ON o.demoid = d.id AND o.state = '交易成功' WHERE d.id = " + demoId;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> queryEmployeeDetailByIdMobile(String demoId) {
        String sql = "SELECT " +
                " e.id, " +
                " e.nickName, " +
                " e.avgPrice, " +
                " e.serverScope, " +
                " e.headImg, " +
                " e.majorScore, " +
                " e.comScore, " +
                " e.punctualScore, " +
                " COUNT(o.id) AS empCount, " +
                " CASE " +
                "WHEN e.commentScore >= 1 && e.commentScore <= 30 THEN " +
                " 1 " +
                "WHEN e.commentScore >= 31 && e.commentScore <= 285 THEN " +
                " 2 " +
                "WHEN e.commentScore >= 286 && e.commentScore <= 1025 THEN " +
                " 3 " +
                "WHEN e.commentScore >= 1026 && e.commentScore <= 2990 THEN " +
                " 4 " +
                "WHEN e.commentScore >= 2991 && e.commentScore <= 7195 THEN " +
                " 5 " +
                "END AS picType, " +
                " CASE " +
                "WHEN e.commentScore = 1 THEN " +
                " 1 " +
                "WHEN e.commentScore >= 2 && e.commentScore <= 3 THEN " +
                " 2 " +
                "WHEN e.commentScore >= 4 && e.commentScore <= 8 THEN " +
                " 3 " +
                "WHEN e.commentScore >= 9 && e.commentScore <= 14 THEN " +
                " 4 " +
                "WHEN e.commentScore >= 15 && e.commentScore <= 30 THEN " +
                " 5 " +
                "WHEN e.commentScore >= 31 && e.commentScore <= 57 THEN " +
                " 1 " +
                "WHEN e.commentScore >= 58 && e.commentScore <= 93 THEN " +
                " 2 " +
                "WHEN e.commentScore >= 94 && e.commentScore <= 146 THEN " +
                " 3 " +
                "WHEN e.commentScore >= 147 && e.commentScore <= 210 THEN " +
                " 4 " +
                "WHEN e.commentScore >= 211 && e.commentScore <= 285 THEN " +
                " 5 " +
                "WHEN e.commentScore >= 286 && e.commentScore <= 384 THEN " +
                " 1 " +
                "WHEN e.commentScore >= 385 && e.commentScore <= 507 THEN " +
                " 2 " +
                "WHEN e.commentScore >= 508 && e.commentScore <= 655 THEN " +
                " 3 " +
                "WHEN e.commentScore >= 656 && e.commentScore <= 828 THEN " +
                " 4 " +
                "WHEN e.commentScore >= 829 && e.commentScore <= 1025 THEN " +
                " 5 " +
                "WHEN e.commentScore >= 1026 && e.commentScore <= 1306 THEN " +
                " 1 " +
                "WHEN e.commentScore >= 1307 && e.commentScore <= 1643 THEN " +
                " 2 " +
                "WHEN e.commentScore >= 1644 && e.commentScore <= 2036 THEN " +
                " 3 " +
                "WHEN e.commentScore >= 2037 && e.commentScore <= 2845 THEN " +
                " 4 " +
                "WHEN e.commentScore >= 2846 && e.commentScore <= 2990 THEN " +
                " 5 " +
                "WHEN e.commentScore >= 2991 && e.commentScore <= 3607 THEN " +
                " 1 " +
                "WHEN e.commentScore >= 3608 && e.commentScore <= 4336 THEN " +
                " 2 " +
                "WHEN e.commentScore >= 4337 && e.commentScore <= 5177 THEN " +
                " 3 " +
                "WHEN e.commentScore >= 5178 && e.commentScore <= 6130 THEN " +
                " 4 " +
                "WHEN e.commentScore >= 6131 && e.commentScore <= 7195 THEN " +
                " 5 " +
                "END AS picCount " +
                "FROM " +
                " demo d, " +
                " employee e, " +
                " `order` o " +
                "WHERE " +
                " d.id =  " + demoId +
                " AND e.id = d.employeeId " +
                "AND o.demoid IN ( " +
                " SELECT " +
                " id " +
                " FROM " +
                " demo " +
                " WHERE " +
                " employeeId = e.id " +
                ") " +
                "AND o.state = '交易成功'";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> queryDemosByTimeMobile(String cityId, String dateType, String hour, String orderType, String page, String pageSize) {
        int pageIndex = Integer.parseInt(page);
        int ps = Integer.parseInt(pageSize);
        StringBuffer sb = new StringBuffer("SELECT " +
                " d.id, " +
                " d. NAME, " +
                " d.price, " +
                " COUNT(o.demoid) AS count, " +
                " d.fileEName " +
                "FROM " +
                " demo d " +
                "LEFT JOIN `order` o ON o.demoid = d.id " +
                "AND o.state = '交易成功' " +
                "WHERE " +
                " d.employeeId IN ( " +
                "  SELECT " +
                "   e.id " +
                "  FROM " +
                "   employee e, " +
                "   book_time bt, " +
                "   seller_validate_info svi " +
                "  WHERE " +
                "   svi.sellerid = e.sellerId " +
                "  AND svi.cityId =  " + cityId +
                "  AND bt.empId = e.id " +
                "  AND bt." + dateType + " LIKE '" + DateUtil.hourMap.get(hour) + "' " +
                " ) " +
                "GROUP BY " +
                " d.id order by ");
        if ("1".equals(orderType)) {
            sb.append(" COUNT(o.demoid),d.price");
        } else if ("2".equals(orderType)) {
            sb.append(" COUNT(o.demoid)");
        } else if ("3".equals(orderType)) {
            sb.append(" price");
        } else if ("4".equals(orderType)) {
            sb.append(" price desc");
        }
        sb.append(" limit " + (pageIndex - 1) * ps + "," + ps);
        return jdbcTemplate.queryForList(sb.toString());
    }

    public List<Map<String, Object>> getAllDemos() {
        String sql = "select id,name from demo ";
        return jdbcTemplate.queryForList(sql);
    }
}
