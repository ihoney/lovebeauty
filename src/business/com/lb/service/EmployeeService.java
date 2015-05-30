package com.lb.service;

import com.lb.bean.Employee;
import com.lb.dao.EmployeeDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public Map<String, Object> getSingleEmployeeById(String employeeId) {
        return employeeDao.getSingleEmployeeById(employeeId);
    }

    public List<Map<String, Object>> getEmployeeComment(String employeeId) {
        return employeeDao.getEmployeeComment(employeeId);
    }

    public List<Map<String, Object>> getEmployeeByPageAdmin(int pageIndex, int pageSize) {
        return employeeDao.getEmployeeByPageAdmin(pageIndex, pageSize);
    }
}