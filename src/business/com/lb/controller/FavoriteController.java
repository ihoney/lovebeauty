package com.lb.controller;

import com.lb.service.FavoriteService;
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
@RequestMapping(value = "favorite")
public class FavoriteController {

    @Resource
    private FavoriteService favoriteService;

    /**
     * 获取收藏信息
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "queryAllFavoriteMobile")
    @ResponseBody
    public Map<String, Object> queryAllFavoriteMobile(HttpServletRequest request, String userId, String type, String page, String pageSize) throws JSONException {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> favorites = favoriteService.queryAllFavoriteMobile(userId, type, page, pageSize);
            jsonObject.put("favorites", favorites);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }

        return jsonObject;
    }

    /**
     * 添加收藏
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "addFavoriteMobile")
    @ResponseBody
    public Map<String, Object> addFavoriteMobile(HttpServletRequest request, String userId, String type, String entityId) throws JSONException {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            favoriteService.addFavoriteMobile(userId, type, entityId);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "添加失败！");
        }
        return jsonObject;
    }

    /**
     * 取消收藏
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "deleteFavoriteMobile")
    @ResponseBody
    public Map<String, Object> deleteFavoriteMobile(HttpServletRequest request, String userId, String type, String entityId) throws JSONException {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            favoriteService.deleteFavoriteMobile(userId, type, entityId);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "取消失败！");
        }
        return jsonObject;
    }
}
