package com.lb.service;

import com.lb.bean.Employee;
import com.lb.dao.EmployeeDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-26
 * Time: 上午8:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class EmployeeService {
    @Resource
    private EmployeeDao employeeDao;

    public int getEmployeeCount() {
        return employeeDao.getEmployeeCount();
    }

    public List<Map<String, Object>> getEmployeeByPage(String sellerId, int pageIndex, int pageSize) {
        return employeeDao.getEmployeeByPage(sellerId, pageIndex, pageSize);
    }

    /**
     * 添加员工
     *
     * @param employee
     */
    public void addEmployee(Employee employee) {
        employeeDao.addEmployee(employee);
    }

    /**
     * 编辑员工
     *
     * @param employee
     */
    public void updateEmployee(Employee employee) {
        employeeDao.updateEmployee(employee);
    }

    public void deleteEmployee(String employeeId) {
        employeeDao.deleteEmployee(employeeId);
    }

    /**
     * 根据Id获取员工信息
     *
     * @param employeeId
     * @return
     */
    public List<Map<String, Object>> getEmployeeById(String employeeId) {
        return employeeDao.getEmployeeById(employeeId);
    }

    public List<Map<String, Object>> getSingleEmployeeById(String employeeId) {
        return employeeDao.getSingleEmployeeById(employeeId);
    }

    public List<Map<String, Object>> getEmployeeComment(String employeeId) {
        return employeeDao.getEmployeeComment(employeeId);
    }

    public List<Map<String, Object>> getEmployeeByPageAdmin(int pageIndex, int pageSize) {
        return employeeDao.getEmployeeByPageAdmin(pageIndex, pageSize);
    }

    public List<Map<String, Object>> queryEmployeeMobile(String page, String pageSize, String orderType) {
        return employeeDao.queryEmployeeMobile(page, pageSize, orderType);
    }

    public List<Map<String, Object>> queryEmployeeDetailByIdMobile(String empId, String userId) {
        return employeeDao.queryEmployeeDetailByIdMobile(empId, userId);
    }

    public List<Map<String, Object>> queryDemosByIdMobile(String empId, String page, String pageSize, String orderType) {
        return employeeDao.queryDemosByIdMobile(empId, page, pageSize, orderType);
    }

    public List<Map<String, Object>> queryCommentCountByType(String empId) {
        return employeeDao.queryCommentCountByType(empId);
    }

    public List<Map<String, Object>> queryEmployeeCommentByIdMobile(String empId, String page, String pageSize, String commentType) {
        return employeeDao.queryEmployeeCommentByIdMobile(empId, page, pageSize, commentType);
    }

    public void addEmployeeCommentByIdMobile(String empId, String userId, String commentType, BigDecimal majorScore, BigDecimal comScore, BigDecimal punctualScore, String comment) {
        employeeDao.addEmployeeCommentByIdMobile(empId, userId, commentType, majorScore, comScore, punctualScore, comment);
    }

    public List<Map<String, Object>> queryEmployeeXYLevelByIdMobile(String empId) {
        return employeeDao.queryEmployeeXYLevelByIdMobile(empId);
    }

    public List<Map<String, Object>> queryEmployeeBookInfoByIdMobile(String empId) {
        return employeeDao.queryEmployeeBookInfoByIdMobile(empId);
    }

    public List<Map<String, Object>> queryEmployeesByTimeMobile(String cityId, String dateType, String hour, String orderType, String page, String pageSize) {
        return employeeDao.queryEmployeesByTimeMobile(cityId, dateType, hour, orderType, page, pageSize);
    }
}
