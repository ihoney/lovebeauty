package com.lb.controller;

import com.lb.service.CityService;
import com.lb.utils.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
@RequestMapping(value = "city")
public class CityController {

    @Resource
    private CityService cityService;

    /**
     * 更新城市状态
     *
     * @return
     */
    @RequestMapping(value = "queryCitiesMobile")
    @ResponseBody
    public Map<String, Object> queryCitiesMobile() {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> cities = cityService.queryCitiesMobile();
            jsonObject.put("cities", cities);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }

    /**
     * 根据名称搜索城市
     *
     * @return
     */
    @RequestMapping(value = "searchByName")
    @ResponseBody
    public Map<String, Object> searchByName(String name) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> cities = cityService.searchByName(name);
            jsonObject.put("cities", cities);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }


}
