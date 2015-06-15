package com.lb.controller;

import com.lb.service.PrivateOrderService;
import com.lb.utils.Constant;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
@RequestMapping(value = "privateOrder")
public class PrivateOrderController {

    @Resource
    private PrivateOrderService privateOrderService;

    /**
     * 初始化私人订制列表
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("queryAllPrivateOrders")
    public ModelAndView queryAllPrivateOrders(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        String sellerId = seller.get("id").toString();
        int orderCount = privateOrderService.getOrderCount(sellerId);
        List<Map<String, Object>> orders = privateOrderService.getOrderByPage(sellerId, 1, Constant.PAGENUM);
        modelAndView.addObject("orders", orders);
        int totalPage = orderCount / Constant.PAGENUM + (orderCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("orderCount", orderCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/body/private_order_list");
        return modelAndView;
    }


    /**
     * 初始化私人订制列表
     *
     * @param request
     * @return
     */
    @RequestMapping("queryPrivateOrderPool")
    public ModelAndView queryPrivateOrderPool(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        int orderCount = privateOrderService.getOrderCount();
        List<Map<String, Object>> orders = privateOrderService.getOrderByPage(1, Constant.PAGENUM);
        modelAndView.addObject("orders", orders);
        int totalPage = orderCount / Constant.PAGENUM + (orderCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("orderCount", orderCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/body/order_pool_list");
        return modelAndView;
    }


    /**
     * 初始化私人订制列表 管理员
     *
     * @param request
     * @return
     */
    @RequestMapping("queryPrivateOrderAdmin")
    public ModelAndView queryPrivateOrderAdmin(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        int orderCount = privateOrderService.getOrderCountAdmin();
        List<Map<String, Object>> orders = privateOrderService.getOrderByPageAdmin(1, Constant.PAGENUM);
        modelAndView.addObject("orders", orders);
        int totalPage = orderCount / Constant.PAGENUM + (orderCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("orderCount", orderCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/body/private_order_admin_list");
        return modelAndView;
    }

    /**
     * 提交订单
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "submitPrivateOrder", method = RequestMethod.POST)
    @ResponseBody
    public String submitPrivateOrder(HttpServletRequest request, @RequestParam MultipartFile file) throws JSONException {
        JSONObject balkJson = new JSONObject();
        String userId = request.getParameter("userId");
        String price = request.getParameter("price");
        String description = request.getParameter("description");
        String sellerId = request.getParameter("sellerId");
        String fileEName = "";
        String fileName = "";
        if (file != null) {
            fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            fileEName = "pic_po_" + System.currentTimeMillis() + fileSuffix;
        }

        try {
            privateOrderService.submitOrder(userId, sellerId, price, description, fileEName);
            if (!file.isEmpty()) {
                InputStream is = file.getInputStream();
                String filePath = request.getRealPath("/fileUpload");
                FileOutputStream fos = new FileOutputStream(filePath + "/" + fileEName);
                byte[] buf = new byte[1024];
                int i;
                while ((i = is.read(buf)) > 0) {
                    fos.write(buf, 0, i);
                }
                fos.flush();
                fos.close();
            }
            balkJson.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (IOException e) {
            balkJson.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return balkJson.toString();
    }

    /**
     * 取消订单
     *
     * @param request
     * @param orderId 订单Id
     * @return
     */
    @RequestMapping(value = "abortPrivateOrder")
    @ResponseBody
    public JSONObject abortPrivateOrder(HttpServletRequest request, String orderId) {
        JSONObject jsonObject = new JSONObject();
        privateOrderService.abortOrder(orderId);
        return jsonObject;
    }

    /**
     * 抢单
     *
     * @param orderId 订单Id
     * @return
     */
    @RequestMapping(value = "grabOrder")
    @ResponseBody
    public JSONObject grabOrder(HttpSession session, String orderId) {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        String sellerId = seller.get("id").toString();
        privateOrderService.grabOrder(sellerId, orderId);
        return jsonObject;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryPrivateOrderByPage")
    @ResponseBody
    public Map<String, Object> queryPrivateOrderByPage(HttpServletRequest request, HttpSession session) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        List<Map<String, Object>> orders = privateOrderService.getOrderByPage(seller.get("id").toString(), pageIndex, Constant.PAGENUM);
        jsonObject.put("orders", orders);
        return jsonObject;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryPrivateOrderByPageAdmin")
    @ResponseBody
    public Map<String, Object> queryPrivateOrderByPageAdmin(HttpServletRequest request) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        List<Map<String, Object>> orders = privateOrderService.getOrderByPageAdmin(pageIndex, Constant.PAGENUM);
        jsonObject.put("orders", orders);
        return jsonObject;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryPrivateOrderPoolByPage")
    @ResponseBody
    public Map<String, Object> queryPrivateOrderPoolByPage(HttpServletRequest request) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        List<Map<String, Object>> orders = privateOrderService.getOrderByPage(pageIndex, Constant.PAGENUM);
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
    @RequestMapping(value = "privateOrderSure")
    @ResponseBody
    public JSONObject privateOrderSure(HttpServletRequest request, String orderId) {
        JSONObject jsonObject = new JSONObject();
        privateOrderService.orderSure(orderId);
        return jsonObject;
    }

    /**
     * 确认订单
     *
     * @param request
     * @param orderId
     * @return
     */
    @RequestMapping(value = "privateOrderDetail")
    public ModelAndView privateOrderDetail(HttpServletRequest request, String orderId) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> privateOrder = privateOrderService.getPrivateOrderById(orderId).get(0);
        modelAndView.addObject("privateOrder", privateOrder);
        modelAndView.setViewName("business/privateOrderDetail");
        return modelAndView;
    }

    /*-----------------------移动端---------------------*/

    /**
     * 确认订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addPrivateOrderMobile")
    @ResponseBody
    public JSONObject addPrivateOrderMobile(HttpServletRequest request, String userId, String cityId, String bookTime, String serverAddress, String description, @RequestParam(required = false) MultipartFile[] reqPicName) {
        JSONObject jsonObject = new JSONObject();
        MultipartFile reqPicNameTmp = null;
        String fileEName = "";
        String fileName;
        if (reqPicName != null && reqPicName.length > 0) {
            reqPicNameTmp = reqPicName[0];
            fileName = reqPicNameTmp.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            fileEName = "pic_reqImg_" + System.currentTimeMillis() + fileSuffix;
        }
        try {
            privateOrderService.addPrivateOrderMobile(userId, cityId, bookTime, serverAddress, description, fileEName);
            if (reqPicNameTmp != null && !reqPicNameTmp.isEmpty()) {
                InputStream is = reqPicNameTmp.getInputStream();
                String filePath = request.getRealPath("/fileUpload");
                FileOutputStream fos = new FileOutputStream(filePath + "/" + fileEName);
                byte[] buf = new byte[1024];
                int i;
                while ((i = is.read(buf)) > 0) {
                    fos.write(buf, 0, i);
                }
                fos.flush();
                fos.close();
            }
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }

    /**
     * 确认订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryPrivateOrdersMobile")
    @ResponseBody
    public Map<String, Object> queryPrivateOrdersMobile(HttpServletRequest request, String userId) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<Map<String, Object>> privateOrders = privateOrderService.queryPrivateOrdersMobile(userId);
            jsonObject.put("privateOrders", privateOrders);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }
}
