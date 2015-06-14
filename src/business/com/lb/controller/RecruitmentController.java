package com.lb.controller;

import com.lb.service.RecruitmentService;
import com.lb.utils.Constant;
import net.sf.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
@RequestMapping(value = "recruitment")
public class RecruitmentController {

    @Resource
    private RecruitmentService recruitmentService;


    @RequestMapping("queryAllRecruitment")
    public ModelAndView queryAllRecruitment(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        int recruitmentCount = recruitmentService.getRecruitmentCount();
        List<Map<String, Object>> recruitment = recruitmentService.getRecruitmentByPage(1, Constant.PAGENUM);
        modelAndView.addObject("recruitment", recruitment);
        int totalPage = recruitmentCount / Constant.PAGENUM + (recruitmentCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("recruitmentCount", recruitmentCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/body/recruitment_list");
        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryRecruitmentByPage")
    @ResponseBody
    public Map<String, Object> queryRecruitmentByPage(HttpServletRequest request) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        List<Map<String, Object>> recruitment = recruitmentService.getRecruitmentByPage(pageIndex, Constant.PAGENUM);
        jsonObject.put("recruitment", recruitment);
        return jsonObject;
    }

    /**
     * 添加招聘人
     *
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "addRecruitmentMobile")
    @ResponseBody
    public Map<String, Object> addRecruitmentMobile(HttpServletRequest request, String city, String name, String telephone, String workYear, String openShop, String hopeSalary, String comFocus) throws JSONException {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            recruitmentService.addRecruitmentMobile(city, name, telephone, workYear, openShop, hopeSalary, comFocus);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败!");
        }
        return jsonObject;
    }
}
