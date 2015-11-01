package com.lb.controller;

import com.lb.service.FinanceService;
import com.lb.utils.Constant;
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
 * 财务控制器
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-27
 * Time: 下午10:19
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "finance")
public class FinanceController {

    @Resource
    private FinanceService financeService;

    @RequestMapping(value = "queryCycleFinance")
    public ModelAndView queryCycleFinance(HttpServletRequest request, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        String sellerId = seller.get("id").toString();
        int financeCount = financeService.getCycleFinanceCount(sellerId);
        List<Map<String, Object>> financeRecords = financeService.getCycleFinanceByPage(sellerId, 1, Constant.PAGENUM);
        modelAndView.addObject("financeRecords", financeRecords);
        int totalPage = financeCount / Constant.PAGENUM + (financeCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("financeCount", financeCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/body/finance_seller_list");
        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryCycleFinanceByPage")
    @ResponseBody
    public Map<String, Object> queryCycleFinanceByPage(HttpServletRequest request, HttpSession session) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        String sellerId = seller.get("id").toString();
        List<Map<String, Object>> financeRecords = financeService.getCycleFinanceByPage(sellerId, pageIndex, Constant.PAGENUM);
        jsonObject.put("financeRecords", financeRecords);
        return jsonObject;
    }

    /**
     * 申请转账
     *
     * @param request
     * @param financeRecordId
     * @return
     */
    @RequestMapping(value = "applyTransfer")
    @ResponseBody
    public Map<String, Object> applyTransfer(HttpServletRequest request, String financeRecordId, String code) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        financeService.applyTransfer(financeRecordId, code);
        return jsonObject;
    }

    @RequestMapping(value = "queryCycleFinanceAdmin")
    public ModelAndView queryCycleFinanceAdmin(HttpServletRequest request, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        int financeCount = financeService.getCycleFinanceCountAdmin();
        List<Map<String, Object>> financeRecords = financeService.getCycleFinanceAdminByPage(1, Constant.PAGENUM);
        modelAndView.addObject("financeRecords", financeRecords);
        int totalPage = financeCount / Constant.PAGENUM + (financeCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("financeCount", financeCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/admin/body/finance_admin_list");
        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryCycleFinanceAdminByPage")
    @ResponseBody
    public Map<String, Object> queryCycleFinanceAdminByPage(HttpServletRequest request, HttpSession session) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        String sellerId = seller.get("id").toString();
        List<Map<String, Object>> financeRecords = financeService.getCycleFinanceByPage(sellerId, pageIndex, Constant.PAGENUM);
        jsonObject.put("financeRecords", financeRecords);
        return jsonObject;
    }
}
