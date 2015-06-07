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

    public int getOrderCount() {
        String sql = "select count(*) from `order`";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getOrderByPage(String sellerId, int pageIndex, int pageSize) {
        String sql = "SELECT " +
                " o.id, " +
                " c.account, " +
                " d.id as demoId, " +
                " d. NAME, " +
                " o.ordertime, " +
                " o.paytime, " +
                " o.state " +
                " FROM " +
                " `order` AS o, " +
                " customer AS c, " +
                " demo AS d " +
                " WHERE " +
                " o.sellerid = " + sellerId +
                " AND c.id = o.userid" +
                " AND d.id = o.demoid order by state limit " + (pageIndex - 1) * pageSize + "," + pageSize;
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

    public void submitOrderMobile(String orderId, String userId, String demoId, String price, String bookTime, String serverAddress) {
        String sql = "INSERT INTO `order` ( id, userid, demoid, price, bookTime, serverAddress,ordertime ) VALUES (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{orderId, userId, demoId, price, bookTime, serverAddress, DateUtil.cruTimeStr()});
    }

    public void changeBookInfo(String empId, String dateType, String hour) {
        String sql = "UPDATE book_time SET " + dateType + " = CONCAT( LEFT (" + dateType + ", " + MyStringUtils.getLeftNumber(hour) + "), '1', RIGHT (" + dateType + ", " +
                MyStringUtils.getRightNumber(hour) + ")) WHERE empId = '" + empId + "'";
        jdbcTemplate.update(sql);
    }
}
