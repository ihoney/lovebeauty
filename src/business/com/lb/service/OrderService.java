package com.lb.service;

import com.lb.bean.Order;
import com.lb.dao.OrderDao;
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
public class OrderService {
    @Resource
    private OrderDao orderDao;

    public int getOrderCount(String sellerId) {
        return orderDao.getOrderCount(sellerId);
    }

    public List<Map<String, Object>> getOrderByPage(String sellerId, int pageIndex, int pageSize) {
        return orderDao.getOrderByPage(sellerId, pageIndex, pageSize);
    }

    /**
     * 提交订单
     *
     * @param order
     */
    public void submitOrder(Order order) {
        orderDao.submitOrder(order);
    }

    /**
     * 取消订单
     *
     * @param orderId
     */
    public void abortOrder(String orderId) {
        orderDao.abortOrder(orderId);
    }

    public void orderSure(String orderId) {
        orderDao.orderSure(orderId);
    }

    public void submitOrderMobile(String orderId, String userId, String demoId, String empId, String price, String bookTime, String serverAddress) {
        orderDao.submitOrderMobile(orderId, userId, demoId, empId, price, bookTime, serverAddress);
    }

    public void changeBookInfo(String empId, String dateType, String hour) {
        orderDao.changeBookInfo(empId, dateType, hour);
    }

    public void changeOrderStateMobile(String orderId) {
        orderDao.changeOrderStateMobile(orderId);
    }

    public void deleteOrderMobile(String orderId) {
        orderDao.deleteOrderMobile(orderId);
    }

    public List<Map<String, Object>> queryOrdersMobile(String userId, String orderState) {
        return orderDao.queryOrdersMobile(userId, orderState);
    }

    public List<Map<String, Object>> queryOrderDetailMobile(String orderId) {
        return orderDao.queryOrderDetailMobile(orderId);
    }

    public String validateOrderMobile(String orderId) {
        return orderDao.validateOrderMobile(orderId);
    }

    public List<Map<String, Object>> getPayInfo(String orderId) {
        return orderDao.getPayInfo(orderId);
    }
}
