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

    public Map<String, Object> getSingleEmployeeById(String employeeId) {
        String sql = "SELECT " +
                " employee.*, AVG(`comment`.score) AS avgScore " +
                " FROM " +
                " employee, " +
                " `comment` " +
                " WHERE " +
                " employee.id = " + employeeId +
                " AND `comment`.employeeid = employee.id";
        return jdbcTemplate.queryForMap(sql);
    }

    public List<Map<String, Object>> getEmployeeComment(String employeeId) {
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
                " AND a.employeeid = " + employeeId;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getEmployeeByPageAdmin(int pageIndex, int pageSize) {
        String sql = "SELECT d.*,s.name as sellerName from employee d, seller_validate_info s where d.sellerid = s.sellerid limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }
}
