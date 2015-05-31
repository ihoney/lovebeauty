package com.lb.controller;

import com.lb.service.CommonAddressService;
import com.lb.utils.Constant;
import net.sf.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value = "commonAddress")
public class CommonAddressController {

    @Resource
    private CommonAddressService commonAddressService;

    /**
     * 检索所有常用地址
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "queryAllAddressMobile")
    @ResponseBody
    public Map<String, Object> queryAllAddressMobile(HttpServletRequest request, String userId) throws JSONException {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> address = commonAddressService.queryAllAddressMobile(userId);
            jsonObject.put("address", address);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }

        return jsonObject;
    }

    /**
     * 增加常用地址
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "addAddressMobile")
    @ResponseBody
    public Map<String, Object> addAddressMobile(HttpServletRequest request, String userId, String address) throws JSONException {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            commonAddressService.addAddressMobile(userId, address);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "添加失败！");
        }

        return jsonObject;
    }

    /**
     * 删除常用地址
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "deleteAddressMobile")
    @ResponseBody
    public Map<String, Object> deleteAddressMobile(HttpServletRequest request, String addressId) throws JSONException {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            commonAddressService.deleteAddressMobile(addressId);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "删除失败！");
        }

        return jsonObject;
    }
}
