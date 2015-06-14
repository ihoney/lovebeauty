package com.lb.service;

import com.lb.dao.CustomerDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CustomerService {
    @Resource
    private CustomerDao customerDao;

    public List<Map<String, Object>> existsCustomer(String account, String password) {
        return customerDao.findCustomer(account, password);
    }

    public void register(String account, String password, String regIp) {
        customerDao.register(account, password, regIp);
    }

    /**
     * 获取客户总数
     *
     * @return
     */
    public int getCustomerCount() {
        return customerDao.getCustomerCount();
    }

    public List<Map<String, Object>> getCustomerByPage(int pageIndex, int pageSize) {
        return customerDao.getCustomerByPage(pageIndex, pageSize);
    }

    public void deleteCustomer(String customerId) {
        customerDao.deleteCustomer(customerId);
    }

    public void forbiddenCustomer(String customerId) {
        customerDao.forbiddenCustomer(customerId);
    }

    public void jbCustomer(String sellerId, String customerId, String reason) {
        customerDao.jbCustomer(sellerId, customerId, reason);
    }

    public void loginInfo(String account, String loginIp) {
        customerDao.loginInfo(account, loginIp);
    }

    public void reUseCustomer(String customerId) {
        customerDao.reUseCustomer(customerId);
    }

    public void changeHeadImgMobile(String userId, String fileEName) {
        customerDao.changeHeadImgMobile(userId, fileEName);
    }

    public void changeNickNameMobile(String userId, String nickName) {
        customerDao.changeNickNameMobile(userId, nickName);
    }

    public boolean changePasswordMobile(String userId, String oldPassword, String newPassword) {
        return customerDao.changePasswordMobile(userId, oldPassword, newPassword);
    }
}
