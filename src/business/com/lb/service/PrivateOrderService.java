package com.lb.service;

import com.lb.dao.PrivateOrderDao;
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
public class PrivateOrderService {
    @Resource
    private PrivateOrderDao privateOrderDao;

    public int getOrderCount(String sellerId) {
        return privateOrderDao.getOrderCount(sellerId);
    }

    public int getOrderCount() {
        return privateOrderDao.getOrderCount();
    }

    public List<Map<String, Object>> getOrderByPage(String sellerId, int pageIndex, int pageSize) {
        return privateOrderDao.getOrderByPage(sellerId, pageIndex, pageSize);
    }

    public List<Map<String, Object>> getOrderByPage(int pageIndex, int pageSize) {
        return privateOrderDao.getOrderByPage(pageIndex, pageSize);
    }

    /**
     * 提交订单
     */
    public void submitOrder(String userId, String sellerId, String price, String description, String fileEName) {
        privateOrderDao.submitOrder(userId, sellerId, price, description, fileEName);
    }

    /**
     * 取消订单
     *
     * @param orderId
     */
    public void abortOrder(String orderId) {
        privateOrderDao.abortOrder(orderId);
    }

    public void orderSure(String orderId) {
        privateOrderDao.orderSure(orderId);
    }

    public void grabOrder(String sellerId, String orderId) {
        privateOrderDao.grabOrder(sellerId, orderId);
    }

    public List<Map<String, Object>> getPrivateOrderById(String orderId) {
        return privateOrderDao.getPrivateOrderById(orderId);
    }

    public int getOrderCountAdmin() {
        return privateOrderDao.getOrderCountAdmin();
    }

    public List<Map<String, Object>> getOrderByPageAdmin(int pageIndex, int pageSize) {
        return privateOrderDao.getOrderByPageAdmin(pageIndex, pageSize);
    }

    public void addPrivateOrderMobile(String userId, String cityId, String price, String bookTime, String serverAddress, String description, String fileEName) {
        privateOrderDao.addPrivateOrderMobile(userId, cityId, price, bookTime, serverAddress, description, fileEName);
    }
}
