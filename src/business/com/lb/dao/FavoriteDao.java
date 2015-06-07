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
            sb.append("SELECT " +
                    " entity.*, COUNT(o.id) AS empCount " +
                    "FROM " +
                    " `order` o, " +
                    " ( " +
                    "  SELECT " +
                    "   e.id, " +
                    "   e.nickName, " +
                    "   e.headImg, " +
                    "   e.avgPrice, " +
                    "   CASE " +
                    "  WHEN e.commentScore >= 1 && e.commentScore <= 30 THEN " +
                    "   1 " +
                    "  WHEN e.commentScore >= 31 && e.commentScore <= 285 THEN " +
                    "   2 " +
                    "  WHEN e.commentScore >= 286 && e.commentScore <= 1025 THEN " +
                    "   3 " +
                    "  WHEN e.commentScore >= 1026 && e.commentScore <= 2990 THEN " +
                    "   4 " +
                    "  WHEN e.commentScore >= 2991 && e.commentScore <= 7195 THEN " +
                    "   5 " +
                    "  END AS picType, " +
                    "  CASE " +
                    " WHEN e.commentScore = 1 THEN " +
                    "  1 " +
                    " WHEN e.commentScore >= 2 && e.commentScore <= 3 THEN " +
                    "  2 " +
                    " WHEN e.commentScore >= 4 && e.commentScore <= 8 THEN " +
                    "  3 " +
                    " WHEN e.commentScore >= 9 && e.commentScore <= 14 THEN " +
                    "  4 " +
                    " WHEN e.commentScore >= 15 && e.commentScore <= 30 THEN " +
                    "  5 " +
                    " WHEN e.commentScore >= 31 && e.commentScore <= 57 THEN " +
                    "  1 " +
                    " WHEN e.commentScore >= 58 && e.commentScore <= 93 THEN " +
                    "  2 " +
                    " WHEN e.commentScore >= 94 && e.commentScore <= 146 THEN " +
                    "  3 " +
                    " WHEN e.commentScore >= 147 && e.commentScore <= 210 THEN " +
                    "  4 " +
                    " WHEN e.commentScore >= 211 && e.commentScore <= 285 THEN " +
                    "  5 " +
                    " WHEN e.commentScore >= 286 && e.commentScore <= 384 THEN " +
                    "  1 " +
                    " WHEN e.commentScore >= 385 && e.commentScore <= 507 THEN " +
                    "  2 " +
                    " WHEN e.commentScore >= 508 && e.commentScore <= 655 THEN " +
                    "  3 " +
                    " WHEN e.commentScore >= 656 && e.commentScore <= 828 THEN " +
                    "  4 " +
                    " WHEN e.commentScore >= 829 && e.commentScore <= 1025 THEN " +
                    "  5 " +
                    " WHEN e.commentScore >= 1026 && e.commentScore <= 1306 THEN " +
                    "  1 " +
                    " WHEN e.commentScore >= 1307 && e.commentScore <= 1643 THEN " +
                    "  2 " +
                    " WHEN e.commentScore >= 1644 && e.commentScore <= 2036 THEN " +
                    "  3 " +
                    " WHEN e.commentScore >= 2037 && e.commentScore <= 2845 THEN " +
                    "  4 " +
                    " WHEN e.commentScore >= 2846 && e.commentScore <= 2990 THEN " +
                    "  5 " +
                    " WHEN e.commentScore >= 2991 && e.commentScore <= 3607 THEN " +
                    "  1 " +
                    " WHEN e.commentScore >= 3608 && e.commentScore <= 4336 THEN " +
                    "  2 " +
                    " WHEN e.commentScore >= 4337 && e.commentScore <= 5177 THEN " +
                    "  3 " +
                    " WHEN e.commentScore >= 5178 && e.commentScore <= 6130 THEN " +
                    "  4 " +
                    " WHEN e.commentScore >= 6131 && e.commentScore <= 7195 THEN " +
                    "  5 " +
                    " END AS picCount " +
                    " FROM " +
                    "  employee e " +
                    " WHERE " +
                    "  e.id IN ( " +
                    "   SELECT " +
                    "    entityId " +
                    "   FROM " +
                    "    favorite " +
                    "   WHERE " +
                    "    type = 1 " +
                    "   AND userId =  " + userId +
                    "  ) " +
                    " ) entity " +
                    "WHERE " +
                    " o.state = '交易成功' " +
                    "AND o.demoId IN ( " +
                    " SELECT " +
                    "  id " +
                    " FROM " +
                    "  demo " +
                    " WHERE " +
                    "  employeeId = entity.id " +
                    ") " +
                    "GROUP BY " +
                    " entity.id");
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
