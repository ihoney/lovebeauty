package com.lb.dao;

import com.lb.bean.Employee;
import com.lb.utils.DateUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
public class EmployeeDao {

    @Resource
    private JdbcTemplate jdbcTemplate;


    public int getEmployeeCount() {
        String sql = "select count(*) from employee";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getEmployeeByPage(String sellerId, int pageIndex, int pageSize) {
        String sql = "SELECT * " +
                " FROM " +
                " employee " +
                " WHERE " +
                " sellerId = " + sellerId + " order by state  limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO employee ( " +
                " nickname, " +
                " sex, " +
                " headImg, " +
                " state, " +
                " sellerId, " +
                " serverScope " +
                ") values (?,?,?,?,?,?) ";
        jdbcTemplate.update(sql, new Object[]{employee.getNickName(), employee.getSex(), employee.getHeadImg(), employee.getState(), employee.getSellerId(), employee.getServerScope()});
    }

    public void updateEmployee(Employee employee) {
        String sql = "UPDATE employee SET nickName = ?, sex = ?,headImg =?, state =? WHERE id = ?";
        jdbcTemplate.update(sql, new Object[]{employee.getNickName(), employee.getSex(), employee.getHeadImg(),
                employee.getState(), employee.getId()});
    }

    public void deleteEmployee(String employeeId) {
        String sql = "delete from employee where id = " + employeeId;
        jdbcTemplate.update(sql);
    }

    public List<Map<String, Object>> getEmployeeById(String employeeId) {
        String sql = "select * from employee where id = " + employeeId;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getSingleEmployeeById(String employeeId) {
        String sql = "SELECT * FROM employee WHERE id = " + employeeId;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getEmployeeComment(String employeeId) {
        String sql = "SELECT ec.*, c.account FROM emp_comment ec LEFT JOIN customer c ON c.id = ec.customerId WHERE ec.empId = " + employeeId;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getEmployeeByPageAdmin(int pageIndex, int pageSize) {
        String sql = "SELECT e.*,s.account " +
                " FROM " +
                " employee e ,seller s  where s.id = e.sellerId " +
                " order by state  limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> queryEmployeeMobile(String page, String pageSize, String orderType) {
        int pageTmp = Integer.parseInt(page);
        int pageSizeTmp = Integer.parseInt(pageSize);
        StringBuffer sb = new StringBuffer("SELECT e.id, e.nickName, e.headImg, e.avgPrice, COUNT(o.id) empCount, CASE WHEN e.commentScore >= 1 && e.commentScore <= 30 THEN 1 WHEN e.commentScore >= 31 && e" +
                ".commentScore <= 285 THEN 2 WHEN e.commentScore >= 286 && e.commentScore <= 1025 THEN 3 WHEN e.commentScore >= 1026 && e.commentScore <= 2990 THEN 4 WHEN e.commentScore >= 2991 && e.commentScore <= " +
                "7195 THEN 5 ELSE 1 END AS picType, CASE WHEN e.commentScore = 1 THEN 1 WHEN e.commentScore >= 2 && e.commentScore <= 3 THEN 2 WHEN e.commentScore >= 4 && e.commentScore <= 8 THEN 3 WHEN e" +
                ".commentScore" +
                "  && e.commentScore <= 14 THEN 4 WHEN e.commentScore >= 15 && e.commentScore <= 30 THEN 5 WHEN e.commentScore >= 31 && e.commentScore <= 57 THEN 1 WHEN e.commentScore >= 58 && e.commentScore <= 93 " +
                "THEN 2 WHEN e.commentScore >= 94 && e.commentScore <= 146 THEN 3 WHEN e.commentScore >= 147 && e.commentScore <= 210 THEN 4 WHEN e.commentScore >= 211 && e.commentScore <= 285 THEN 5 WHEN e" +
                ".commentScore >= 286 && e.commentScore <= 384 THEN 1 WHEN e.commentScore >= 385 && e.commentScore <= 507 THEN 2 WHEN e.commentScore >= 508 && e.commentScore <= 655 THEN 3 WHEN e.commentScore >= 656 &&" +
                " e.commentScore <= 828 THEN 4 WHEN e.commentScore >= 829 && e.commentScore <= 1025 THEN 5 WHEN e.commentScore >= 1026 && e.commentScore <= 1306 THEN 1 WHEN e.commentScore >= 1307 && e.commentScore <= " +
                "1643 THEN 2 WHEN e.commentScore >= 1644 && e.commentScore <= 2036 THEN 3 WHEN e.commentScore >= 2037 && e.commentScore <= 2845 THEN 4 WHEN e.commentScore >= 2846 && e.commentScore <= 2990 THEN 5 WHEN " +
                "e.commentScore >= 2991 && e.commentScore <= 3607 THEN 1 WHEN e.commentScore >= 3608 && e.commentScore <= 4336 THEN 2 WHEN e.commentScore >= 4337 && e.commentScore <= 5177 THEN 3 WHEN e.commentScore >=" +
                " 5178 && e.commentScore <= 6130 THEN 4 WHEN e.commentScore >= 6131 && e.commentScore <= 7195 THEN 5 ELSE 0 END AS picCount FROM employee e LEFT JOIN `order` o ON o.demoid IN ( SELECT id FROM demo " +
                "WHERE employeeId = e.id ) AND o.state = '交易成功' GROUP BY e.id ORDER BY ");
        if ("1".equals(orderType)) {
            sb.append(" empCount DESC, e.avgPrice");
        } else if ("2".equals(orderType)) {
            sb.append(" empCount DESC ");
        } else if ("3".equals(orderType)) {
            sb.append(" e.avgPrice");
        } else if ("4".equals(orderType)) {
            sb.append(" e.avgPrice desc");
        }
        sb.append(" limit " + (pageTmp - 1) * pageSizeTmp + " , " + pageSizeTmp);
        return jdbcTemplate.queryForList(sb.toString());
    }

    public List<Map<String, Object>> queryEmployeeDetailByIdMobile(String empId, String userId) {
        String sql = "SELECT e.id, e.nickName, e.avgPrice, e.serverScope, e.headImg, e.majorScore, e.comScore, e.punctualScore, count(o.id) AS empCount, " +
                "count(DISTINCT f.id) AS isFavorite FROM employee e LEFT JOIN `order` o ON o.demoid IN ( SELECT id FROM demo WHERE employeeId = e.id ) AND o.state = '交易成功' LEFT JOIN favorite f ON f.type = 1 AND f" +
                ".userId = " + userId + " AND f.entityId = e.id WHERE e.id = " + empId;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> queryDemosByIdMobile(String empId, String page, String pageSize, String orderType) {
        int pageTmp = Integer.parseInt(page);
        int pageSizeTpm = Integer.parseInt(pageSize);
        StringBuffer sb = new StringBuffer("SELECT d.id, d. NAME, d.price, COUNT(o.demoid) AS demoCount, d.fileEName FROM demo d LEFT JOIN `order` o ON o.demoid = d.id AND o.state = '交易成功' WHERE d.employeeId = "
                + empId + " GROUP BY d.id order by ");
        if ("0".equals(orderType)) {
            sb.append(" d.price DESC, demoCount DESC ");
        } else if ("1".equals(orderType)) {
            sb.append(" demoCount DESC ");
        } else if ("2".equals(orderType)) {
            sb.append(" d.price ");
        } else if ("3".equals(orderType)) {
            sb.append(" d.price DESC");
        }

        sb.append(" limit " + (pageTmp - 1) * pageSizeTpm + " , " + pageSizeTpm);
        return jdbcTemplate.queryForList(sb.toString());
    }

    public List<Map<String, Object>> queryCommentCountByType(String empId) {
        String sql = "SELECT '1' AS level, COUNT(ec.id) AS commentCount FROM emp_comment ec WHERE empId = " + empId + " and ec.`level`  = 1 " +
                "union  " +
                "SELECT '2' AS level, COUNT(ec.id) AS commentCount FROM emp_comment ec WHERE empId =  " + empId + " and ec.`level`  = 2 " +
                "union  " +
                "SELECT '3' AS level, COUNT(ec.id) AS commentCount FROM emp_comment ec WHERE empId =  " + empId + " and ec.`level`  = 3 " +
                "union  " +
                "SELECT 'commentAllCount' as level, COUNT(ec.id) AS commentCount FROM emp_comment ec WHERE empId = " + empId;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> queryEmployeeCommentByIdMobile(String empId, String page, String pageSize, String commentType) {
        int pageTmp = Integer.parseInt(page);
        int pageSizeTmp = Integer.parseInt(pageSize);
        StringBuffer sb = new StringBuffer("SELECT ec.`comment`, ec.comTime, c.account,c.headImg FROM emp_comment ec, customer c WHERE ec.empId = " + empId + " AND c.id = ec.customerId ");
        if ("0".equals(commentType)) {
        } else {
            sb.append(" and ec.level = " + commentType);
        }
        sb.append(" limit " + (pageTmp - 1) * pageSizeTmp + "," + pageSizeTmp);
        return jdbcTemplate.queryForList(sb.toString());
    }

    public void addEmployeeCommentByIdMobile(String empId, String userId, String commentType, BigDecimal majorScore, BigDecimal comScore, BigDecimal punctualScore, String comment) {
        String sql = "insert into emp_comment (empId,customerId,level,majorScore,comScore,punctualScore,comment,comTime) values (?,?,?,?,?) ";
        jdbcTemplate.update(sql, new Object[]{empId, userId, commentType, majorScore, comScore, punctualScore, comment, DateUtil.cruTimeStr()});
        if ("1".equals(commentType)) {
            sql = "update employee set commentScore = commentScore + 1 where id = " + empId;
        } else if ("3".equals(commentType)) {
            sql = "update employee set commentScore = commentScore - 1 where id = " + empId;
        }
        jdbcTemplate.update(sql);
    }

    public List<Map<String, Object>> queryEmployeeXYLevelByIdMobile(String empId) {
        String sql = "SELECT " +
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
                " 5 ELSE 1 " +
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
                " 5 ELSE 0 " +
                "END AS picCount " +
                "FROM " +
                " employee e " +
                "WHERE " +
                " e.id = " + empId;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> queryEmployeeBookInfoByIdMobile(String empId) {
        String sql = "select * from book_time where empId = " + empId;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> queryEmployeesByTimeMobile(String cityId, String dateType, String hour, String orderType, String page, String pageSize) {
        int pageTmp = Integer.parseInt(page);
        int pageSizeTmp = Integer.parseInt(pageSize);
        StringBuffer sb = new StringBuffer("SELECT " +
                " e.id, " +
                " e.nickName, " +
                " e.headImg, " +
                " e.avgPrice, " +
                " COUNT(o.id) empCount, " +
                " CASE " +
                " WHEN e.commentScore >= 1 && e.commentScore <= 30 THEN " +
                " 1 " +
                " WHEN e.commentScore >= 31 && e.commentScore <= 285 THEN " +
                " 2 " +
                "WHEN e.commentScore >= 286 && e.commentScore <= 1025 THEN " +
                " 3 " +
                " WHEN e.commentScore >= 1026 && e.commentScore <= 2990 THEN " +
                " 4 " +
                " WHEN e.commentScore >= 2991 && e.commentScore <= 7195 THEN " +
                " 5 ELSE 1 " +
                " END AS picType, " +
                " CASE " +
                " WHEN e.commentScore = 1 THEN " +
                " 1 " +
                " WHEN e.commentScore >= 2 && e.commentScore <= 3 THEN " +
                " 2 " +
                " WHEN e.commentScore >= 4 && e.commentScore <= 8 THEN " +
                " 3 " +
                " WHEN e.commentScore >= 9 && e.commentScore <= 14 THEN " +
                " 4 " +
                " WHEN e.commentScore >= 15 && e.commentScore <= 30 THEN " +
                " 5 " +
                " WHEN e.commentScore >= 31 && e.commentScore <= 57 THEN " +
                " 1 " +
                " WHEN e.commentScore >= 58 && e.commentScore <= 93 THEN " +
                " 2 " +
                " WHEN e.commentScore >= 94 && e.commentScore <= 146 THEN " +
                " 3 " +
                " WHEN e.commentScore >= 147 && e.commentScore <= 210 THEN " +
                " 4 " +
                " WHEN e.commentScore >= 211 && e.commentScore <= 285 THEN " +
                " 5 " +
                " WHEN e.commentScore >= 286 && e.commentScore <= 384 THEN " +
                " 1 " +
                " WHEN e.commentScore >= 385 && e.commentScore <= 507 THEN " +
                " 2 " +
                " WHEN e.commentScore >= 508 && e.commentScore <= 655 THEN " +
                " 3 " +
                " WHEN e.commentScore >= 656 && e.commentScore <= 828 THEN " +
                " 4 " +
                " WHEN e.commentScore >= 829 && e.commentScore <= 1025 THEN " +
                " 5 " +
                " WHEN e.commentScore >= 1026 && e.commentScore <= 1306 THEN " +
                " 1 " +
                " WHEN e.commentScore >= 1307 && e.commentScore <= 1643 THEN " +
                " 2 " +
                " WHEN e.commentScore >= 1644 && e.commentScore <= 2036 THEN " +
                " 3 " +
                " WHEN e.commentScore >= 2037 && e.commentScore <= 2845 THEN " +
                " 4 " +
                " WHEN e.commentScore >= 2846 && e.commentScore <= 2990 THEN " +
                " 5 " +
                " WHEN e.commentScore >= 2991 && e.commentScore <= 3607 THEN " +
                " 1 " +
                " WHEN e.commentScore >= 3608 && e.commentScore <= 4336 THEN " +
                " 2 " +
                " WHEN e.commentScore >= 4337 && e.commentScore <= 5177 THEN " +
                " 3 " +
                " WHEN e.commentScore >= 5178 && e.commentScore <= 6130 THEN " +
                " 4 " +
                " WHEN e.commentScore >= 6131 && e.commentScore <= 7195 THEN " +
                " 5 ELSE 0 " +
                " END AS picCount " +
                " FROM " +
                " employee e " +
                " LEFT JOIN `order` o ON o.demoid IN ( " +
                " SELECT " +
                "  id " +
                " FROM " +
                "  demo " +
                " WHERE " +
                "  employeeId = e.id " +
                ") " +
                " AND o.state = '交易成功', " +
                " book_time bt, " +
                " seller_validate_info svi " +
                " WHERE " +
                " svi.sellerid = e.sellerId " +
                " AND svi.cityId =  " + cityId +
                " AND bt.empId = e.id " +
                " AND bt." + dateType + " LIKE '" + DateUtil.hourMap.get(hour) + "' " +
                " GROUP BY " +
                " e.id ORDER BY ");
        if ("1".equals(orderType)) {
            sb.append(" empCount DESC, e.avgPrice");
        } else if ("2".equals(orderType)) {
            sb.append(" empCount DESC ");
        } else if ("3".equals(orderType)) {
            sb.append(" e.avgPrice");
        } else if ("4".equals(orderType)) {
            sb.append(" e.avgPrice desc");
        }
        sb.append(" limit " + (pageTmp - 1) * pageSizeTmp + " , " + pageSizeTmp);
        return jdbcTemplate.queryForList(sb.toString());
    }
}
