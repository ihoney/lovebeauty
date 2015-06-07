package com.lb.controller;

import com.lb.bean.Order;
import com.lb.service.OrderService;
import com.lb.utils.Constant;
import com.lb.utils.DateUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-27
 * Time: 下午10:19
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "order")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 提交订单
     *
     * @param request
     * @param order
     * @return
     */
    @RequestMapping(value = "submitOrder")
    @ResponseBody
    public JSONObject submitOrder(HttpServletRequest request, Order order) {
        JSONObject jsonObject = new JSONObject();
        orderService.submitOrder(order);
        return jsonObject;
    }

    /**
     * 取消订单
     *
     * @param request
     * @param orderId 订单Id
     * @return
     */
    @RequestMapping(value = "abortOrder")
    @ResponseBody
    public JSONObject abortOrder(HttpServletRequest request, String orderId) {
        JSONObject jsonObject = new JSONObject();
        orderService.abortOrder(orderId);
        return jsonObject;
    }

    @RequestMapping("queryAllOrders")
    public ModelAndView queryAllOrders(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        int orderCount = orderService.getOrderCount();
        List<Map<String, Object>> orders = orderService.getOrderByPage(seller.get("id").toString(), 1, Constant.PAGENUM);
        modelAndView.addObject("orders", orders);
        int totalPage = orderCount / Constant.PAGENUM + (orderCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("orderCount", orderCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/body/order_list");
        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryOrderByPage")
    @ResponseBody
    public Map<String, Object> queryOrderByPage(HttpServletRequest request, HttpSession session) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        List<Map<String, Object>> orders = orderService.getOrderByPage(seller.get("id").toString(), pageIndex, Constant.PAGENUM);
        jsonObject.put("orders", orders);
        return jsonObject;
    }

    /**
     * 确认订单
     *
     * @param request
     * @param orderId
     * @return
     */
    @RequestMapping(value = "orderSure")
    @ResponseBody
    public JSONObject orderSure(HttpServletRequest request, String orderId) {
        JSONObject jsonObject = new JSONObject();
        orderService.orderSure(orderId);
        return jsonObject;
    }


    /**
     * 客户端下订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "submitOrderMobile")
    @ResponseBody
    public Map<String, Object> submitOrderMobile(HttpServletRequest request, String orderId, String userId, String demoId, String empId, String price, String dateType, String hour, String serverAddress) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            Calendar calendar = Calendar.getInstance();
            if ("tomorrow".equals(dateType)) {
                calendar.add(Calendar.DATE, 1);
            } else if ("afterTomorrow".equals(dateType)) {
                calendar.add(Calendar.DATE, 2);
            } else if ("bigDayAfterTomorrow".equals(dateType)) {
                calendar.add(Calendar.DATE, 3);
            }
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            calendar.set(Calendar.MINUTE, 0);
            String bookTime = DateUtil.getYmdhmFormat(calendar.getTime());
            orderService.submitOrderMobile(orderId, userId, demoId, empId, price, bookTime, serverAddress);
            orderService.changeBookInfo(empId, dateType, hour);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }
}
