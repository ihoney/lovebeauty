package com.lb.controller;

import com.lb.service.AdminService;
import com.lb.utils.Constant;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@RequestMapping(value = "admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 检查登录
     *
     * @param request
     * @param session
     * @param account
     * @param password
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "checkLogin")
    @ResponseBody
    public JSONObject checkLogin(HttpServletRequest request, HttpSession session, String account, String password) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        List<Map<String, Object>> admins = adminService.existsAdmin(account, password);
        if (admins != null && admins.size() > 0) {
            session.setAttribute("admin", admins.get(0));
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } else {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 退出系统
     *
     * @param request
     * @param session
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "quitSys")
    @ResponseBody
    public JSONObject quitSys(HttpServletRequest request, HttpSession session) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        session.removeAttribute("admin");
        jsonObject.put(Constant.TIPMESSAGE, "exit success！");
        return jsonObject;
    }

    /**
     * 查看所有开通城市
     *
     * @param request
     * @return
     */
    @RequestMapping("queryAllCities")
    public ModelAndView queryAllCities(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        int cityCount = adminService.getCityCount();
        List<Map<String, Object>> cities = adminService.getCityByPage(1, Constant.PAGENUM);
        modelAndView.addObject("cities", cities);
        int totalPage = cityCount / Constant.PAGENUM + (cityCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("cityCount", cityCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/admin/body/city_list");
        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryCityByPage")
    @ResponseBody
    public Map<String, Object> querySellerByPage(HttpServletRequest request) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        List<Map<String, Object>> cities = adminService.getCityByPage(pageIndex, Constant.PAGENUM);
        jsonObject.put("cities", cities);
        return jsonObject;
    }

    /**
     * 添加城市
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addCity")
    @ResponseBody
    public JSONObject addCity(HttpServletRequest request, String cityName) {
        JSONObject jsonObject = new JSONObject();
        adminService.addCity(cityName);
        return jsonObject;
    }

    /**
     * 更新城市状态
     *
     * @return
     */
    @RequestMapping(value = "changeCityState")
    @ResponseBody
    public JSONObject changeCityState(String cityId, String state) {
        JSONObject jsonObject = new JSONObject();
        adminService.changeCityState(cityId, state);
        return jsonObject;
    }


    /**
     * 查看所有广告
     *
     * @param request
     * @return
     */
    @RequestMapping("queryAllAds")
    public ModelAndView queryAllAds(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        int adCount = adminService.getAdCount();
        List<Map<String, Object>> ads = adminService.getAdByPage(1, Constant.PAGENUM);
        modelAndView.addObject("ads", ads);
        int totalPage = adCount / Constant.PAGENUM + (adCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("adCount", adCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/admin/body/ad_list");
        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryAdByPage")
    @ResponseBody
    public Map<String, Object> queryAdByPage(HttpServletRequest request) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        List<Map<String, Object>> ads = adminService.getAdByPage(pageIndex, Constant.PAGENUM);
        jsonObject.put("ads", ads);
        return jsonObject;
    }

    /**
     * 更新城市状态
     *
     * @return
     */
    @RequestMapping(value = "changeAdState")
    @ResponseBody
    public JSONObject changeAdState(String adId, String state) {
        JSONObject jsonObject = new JSONObject();
        adminService.changeAdState(adId, state);
        return jsonObject;
    }

    /**
     * 初始化添加广告页面
     *
     * @return
     */
    @RequestMapping("addAdInit")
    public String addAdInit() {
        return "business/ad_add";
    }

    /**
     * 添加城市
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addAd")
    @ResponseBody
    public JSONObject addAd(HttpServletRequest request, String type, String adUrl, String backup) {
        JSONObject jsonObject = new JSONObject();
        adminService.addAd(type, adUrl, backup);
        return jsonObject;
    }
}
