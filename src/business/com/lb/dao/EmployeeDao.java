package com.lb.dao;

import com.lb.bean.Employee;
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
                " majorScore, " +
                " comScore, " +
                " punctualScore, " +
                " avgPrice, " +
                " headImg, " +
                " state, " +
                " sellerId, " +
                " serverScope " +
                ") values (?,?,?,?,?,?,?,?,?,?) ";
        jdbcTemplate.update(sql, new Object[]{employee.getNickName(), employee.getSex(), employee.getMajorScore(), employee.getComScore(), employee.getPunctualScore(),
                employee.getAvgPrice(), employee.getHeadImg(), employee.getState(), employee.getSellerId(), employee.getServerScope()});
    }

    public void updateEmployee(Employee employee) {
        String sql = "UPDATE employee SET nickName = ?, sex = ?, majorScore = ?, comScore = ?, punctualScore = ?, avgPrice = ?, headImg =?, state =? WHERE id = ?";
        jdbcTemplate.update(sql, new Object[]{employee.getNickName(), employee.getSex(), employee.getMajorScore(), employee.getComScore(), employee.getPunctualScore(), employee.getAvgPrice(), employee.getHeadImg(),
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
        StringBuffer sb = new StringBuffer("SELECT e.id, e.nickName, e.headImg, e.avgPrice, COUNT(o.id) empCount FROM employee e LEFT JOIN `order` o ON o.demoid IN ( SELECT id FROM demo WHERE employeeId = e.id ) AND o.state = '交易成功' GROUP BY e.id ORDER BY ");
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
