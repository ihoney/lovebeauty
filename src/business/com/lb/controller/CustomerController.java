package com.lb.controller;

import com.lb.service.CustomerService;
import com.lb.utils.Constant;
import com.lb.utils.MD5Util;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
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
@RequestMapping(value = "customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    /**
     * 检查登录
     *
     * @param request
     * @param account
     * @param password
     * @return
     * @throws JSONException
     */
    @RequestMapping(value = "checkLogin")
    @ResponseBody
    public Map<String, Object> checkLogin(HttpServletRequest request, String account, String password) throws JSONException {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        String loginIp = request.getRemoteAddr();
        List<Map<String, Object>> customers = customerService.existsCustomer(account, password);
        if (customers != null && customers.size() > 0) {
            String forbidden = customers.get(0).get("forbidden").toString();
            if ("是".equals(forbidden)) {
                jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
                jsonObject.put(Constant.TIPMESSAGE, "账号已禁用，请联系管理员!");
            } else {
                customerService.loginInfo(account, loginIp);
                jsonObject.put("customer", customers.get(0));
                jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
            }
        } else {
            jsonObject.put(Constant.TIPMESSAGE, "账号或密码错误!");
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 注册用户
     *
     * @param request
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(value = "registerMobile")
    @ResponseBody
    public JSONObject registerMobile(HttpServletRequest request, String account, String password, String time, String key) {
        JSONObject jsonObject = new JSONObject();
        String regIp = request.getRemoteAddr();
        String keyTmp = MD5Util.toHexString(account + password + time);
        if (!keyTmp.equals(key)) {
            jsonObject.put(Constant.TIPMESSAGE, "禁止非法注册！");
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        } else {
            try {
                if (account != null && account.matches("1\\d{10}")) {
                    if (password != null && password.matches("\\w{6,25}")) {
                        customerService.register(account, password, regIp);
                        jsonObject.put(Constant.TIPMESSAGE, "恭喜，注册成功！");
                        jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
                    } else {
                        jsonObject.put(Constant.TIPMESSAGE, "密码不合法！");
                        jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
                    }
                } else {
                    jsonObject.put(Constant.TIPMESSAGE, "请输入正确手机号！");
                    jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
                }
            } catch (Exception e) {
                jsonObject.put(Constant.TIPMESSAGE, "注册失败！");
                jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            }
        }
        return jsonObject;
    }


    @RequestMapping("queryAllCustomers")
    public ModelAndView queryAllCustomers(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        int customerCount = customerService.getCustomerCount();
        List<Map<String, Object>> customers = customerService.getCustomerByPage(1, Constant.PAGENUM);
        modelAndView.addObject("customers", customers);
        int totalPage = customerCount / Constant.PAGENUM + (customerCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("customerCount", customerCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/admin/body/customer_list");
        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryCustomerByPage")
    @ResponseBody
    public Map<String, Object> queryCustomerByPage(HttpServletRequest request) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        List<Map<String, Object>> customers = customerService.getCustomerByPage(pageIndex, Constant.PAGENUM);
        jsonObject.put("customers", customers);
        return jsonObject;
    }

    /**
     * 删除客户
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "deleteCustomer")
    @ResponseBody
    public JSONObject deleteCustomer(HttpServletRequest request, String customerId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            customerService.deleteCustomer(customerId);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 禁用客户
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "forbiddenCustomer")
    @ResponseBody
    public JSONObject forbiddenCustomer(HttpServletRequest request, String customerId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            customerService.forbiddenCustomer(customerId);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 启用客户
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "reUseCustomer")
    @ResponseBody
    public JSONObject reUseCustomer(HttpServletRequest request, String customerId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            customerService.reUseCustomer(customerId);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 举报客户
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "jbCustomer")
    @ResponseBody
    public JSONObject jbCustomer(HttpServletRequest request, HttpSession session, String customerId, String reason) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        String sellerId = seller.get("id") + "";
        customerService.jbCustomer(sellerId, customerId, reason);
        return jsonObject;
    }

    /**
     * 更换头像
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "changeHeadImgMobile")
    @ResponseBody
    public JSONObject changeHeadImgMobile(HttpServletRequest request, String userId, @RequestParam(required = false) MultipartFile[] headImg) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String fileEName = "";
        String fileName;
        MultipartFile headImgTmp = null;
        if (headImg != null) {
            headImgTmp = headImg[0];
            fileName = headImgTmp.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            fileEName = "pic_user_headImg_" + "_" + System.currentTimeMillis() + fileSuffix;
        }

        try {
            customerService.changeHeadImgMobile(userId, fileEName);
            if (headImg != null) {
                InputStream is = headImgTmp.getInputStream();
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
            jsonObject.put(Constant.REQSUCCESS, "请求失败！");
        }
        return jsonObject;
    }

    /**
     * 更换昵称
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "changeNickNameMobile")
    @ResponseBody
    public JSONObject changeNickNameMobile(HttpServletRequest request, String userId, String nickName) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        try {
            customerService.changeNickNameMobile(userId, nickName);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }

    /**
     * 更换密码
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "changePasswordMobile")
    @ResponseBody
    public JSONObject changePasswordMobile(HttpServletRequest request, String userId, String oldPassword, String newPassword) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            boolean flag = customerService.changePasswordMobile(userId, oldPassword, newPassword);
            if (flag) {
                jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
            } else {
                jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
                jsonObject.put(Constant.TIPMESSAGE, "当前密码不对！");
            }
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }

}
