package com.lb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-4-21
 * Time: 上午12:18
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "frame")
public class FrameController {

    @RequestMapping(value = "down")
    public String down(HttpServletRequest request) {
        return "frame/down";
    }

    @RequestMapping(value = "adminDown")
    public String adminDown(HttpServletRequest request) {
        return "frame/admin/down";
    }

    @RequestMapping(value = "left")
    public ModelAndView left(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        if (seller != null) {
            modelAndView.addObject("authed", seller.get("authed"));
            modelAndView.addObject("checked", seller.get("checked"));
        }
        modelAndView.setViewName("frame/left");
        return modelAndView;
    }

    @RequestMapping(value = "adminLeft")
    public String adminLeft(HttpServletRequest request) {
        return "frame/admin/left";
    }

    @RequestMapping(value = "main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("frame/main");
        return modelAndView;
    }

    @RequestMapping(value = "adminMain")
    public String adminMain(HttpServletRequest request) {
        return "frame/admin/admin_main";
    }

    @RequestMapping(value = "top")
    public String top(HttpServletRequest request) {
        return "frame/top";
    }

    @RequestMapping(value = "adminTop")
    public String adminTop(HttpServletRequest request) {
        return "frame/admin/top";
    }

    @RequestMapping(value = "tab")
    public String tab(HttpServletRequest request) {
        return "frame/body/demo_list_1";
    }

    @RequestMapping(value = "adminTab")
    public String adminTab(HttpServletRequest request) {
        return "frame/admin/body/seller_list";
    }
}
