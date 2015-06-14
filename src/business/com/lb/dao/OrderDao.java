package com.lb.dao;

import com.lb.bean.Order;
import com.lb.utils.DateUtil;
import com.lb.utils.MyStringUtils;
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
public class OrderDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public int getOrderCount(String sellerId) {
        String sql = "SELECT COUNT(*) FROM `order` WHERE empId IN ( SELECT id FROM employee WHERE sellerId = " + sellerId + ")";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getOrderByPage(String sellerId, int pageIndex, int pageSize) {
        String sql = "SELECT " +
                " o.id, " +
                " c.account, " +
                " d.id AS demoId, " +
                " d. NAME, " +
                " o.ordertime, " +
                " o.paytime, " +
                " o.state, " +
                " o.price, " +
                " o.bookTime, " +
                " o.serverAddress " +
                "FROM " +
                " `order` AS o, " +
                " customer AS c, " +
                " demo AS d " +
                "WHERE " +
                " o.empId IN ( " +
                "  SELECT " +
                "   id " +
                "  FROM " +
                "   employee " +
                "  WHERE " +
                "   sellerid =  " + sellerId +
                "  ) " +
                " AND c.id = o.userid " +
                "AND d.id = o.demoid " +
                "ORDER BY " +
                " state desc  limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void submitOrder(Order order) {
        String sql = "insert into order (userid,sellerid,state,demoid,ordertime) values (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{order.getUserId(), order.getSellerId(), 1, order.getDemoId(), DateUtil.cruTimeStr()});
    }

    public void abortOrder(String orderId) {
        String sql = "update `order` set state = '取消订单' where id = '" + orderId + "'";
        jdbcTemplate.update(sql);
    }

    public void orderSure(String orderId) {
        String sql = "update `order` set state = '交易成功',paytime = '" + DateUtil.cruTimeStr() + "' where id = '" + orderId + "'";
        jdbcTemplate.update(sql);
    }

    public void submitOrderMobile(String orderId, String userId, String demoId, String empId, String price, String bookTime, String serverAddress) {
        String sql = "INSERT INTO `order` ( id, userid, demoid,empId, price, bookTime, serverAddress,ordertime ) VALUES (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{orderId, userId, demoId, empId, price, bookTime, serverAddress, DateUtil.cruTimeStr()});
    }

    public void changeBookInfo(String empId, String dateType, String hour) {
        String sql = "UPDATE book_time SET " + dateType + " = CONCAT( LEFT (" + dateType + ", " + MyStringUtils.getLeftNumber(hour) + "), '1', RIGHT (" + dateType + ", " +
                MyStringUtils.getRightNumber(hour) + ")) WHERE empId = '" + empId + "'";
        jdbcTemplate.update(sql);
    }

    public void changeOrderStateMobile(String orderId) {
        String sql = "UPDATE `order` SET state = '交易成功', paytime = ? WHERE id = ?";
        jdbcTemplate.update(sql, new Object[]{DateUtil.cruTimeStr(), orderId});
    }

    public void deleteOrderMobile(String orderId) {
        String sql = "update `order`set state = '取消订单' where id = " + orderId;
        jdbcTemplate.update(sql);
    }

    public List<Map<String, Object>> queryOrdersMobile(String userId, String orderState) {
        StringBuffer sb = new StringBuffer("SELECT " +
                " o.id AS orderId, " +
                " o.state, " +
                " o.price, " +
                " o.bookTime, " +
                " o.serverAddress, " +
                " d.id AS demoId, " +
                " d.`name` AS demoName, " +
                " d.fileEName, " +
                " e.id AS empId, " +
                " e.nickName " +
                "FROM " +
                " `order` o, " +
                " demo d, " +
                " employee e " +
                "WHERE " +
                " e.id = o.empId " +
                "AND d.id = o.demoid " +
                "AND userid = " + userId);
        if ("1".equals(orderState)) {
            sb.append(" and o.state != '取消订单' ");
        } else if ("2".equals(orderState)) {
            sb.append(" and o.state = '未付款' ");
        } else if ("3".equals(orderState)) {
            sb.append(" and o.hasComment = 0 and o.state != '取消订单' ");
        }
        sb.append(" ORDER BY o.state desc ");
        return jdbcTemplate.queryForList(sb.toString());
    }

    public List<Map<String, Object>> queryOrderDetailMobile(String orderId) {
        String sql = "SELECT " +
                " o.id AS orderId, " +
                " o.state, " +
                " o.price, " +
                " o.bookTime, " +
                " o.serverAddress, " +
                " d.id AS demoId, " +
                " d.`name` AS demoName, " +
                " d.fileEName, " +
                " e.id AS empId, " +
                " e.nickName, " +
                " c.id AS userId, " +
                " c.account AS userAccount, " +
                " c.nickName AS userNickName " +
                "FROM " +
                " `order` o, " +
                " demo d, " +
                " employee e, " +
                " customer c " +
                "WHERE " +
                " e.id = o.empId " +
                "AND d.id = o.demoid " +
                "AND o.id = '" + orderId + "' " +
                "AND c.id = o.userid ";
        return jdbcTemplate.queryForList(sql);
    }

    public String validateOrderMobile(String orderId) {
        String sql = "select state from `order` where id = " + orderId;
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public List<Map<String, Object>> getPayInfo(String orderId) {
        String sql = "SELECT " +
                " svi.payaccount, " +
                " svi.alipay_key, " +
                " svi.alipay_pid " +
                "FROM " +
                " seller_validate_info svi, " +
                " `order` o, " +
                " employee e " +
                "WHERE " +
                " svi.sellerid = e.sellerId " +
                "AND e.id = o.empId " +
                "AND o.id = '" + orderId + "'";
        return jdbcTemplate.queryForList(sql);
    }
}
