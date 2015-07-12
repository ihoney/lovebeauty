package com.lb.controller;

import com.lb.bean.Demo;
import com.lb.service.DemoService;
import com.lb.service.EmployeeService;
import com.lb.utils.Constant;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
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
@RequestMapping(value = "demo")
public class DemoController {

    @Resource
    private DemoService demoService;

    @Resource
    private EmployeeService employeeService;

    @RequestMapping("queryAllDemos")
    public ModelAndView queryAllDemos(HttpServletRequest request, HttpSession session, String showType) {
        ModelAndView modelAndView = new ModelAndView();
        int demoCount = demoService.getDemoCount();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        List<Map<String, Object>> demos = demoService.getDemoByPage(seller.get("id").toString(), 1, Constant.PAGENUM);
        modelAndView.addObject("demos", demos);
        int totalPage = demoCount / Constant.PAGENUM + (demoCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("demoCount", demoCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/body/demo_list_" + showType);
        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryDemoByPage")
    @ResponseBody
    public Map<String, Object> queryDemoByPage(HttpServletRequest request, HttpSession session) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        List<Map<String, Object>> demos = demoService.getDemoByPage(seller.get("id").toString(), pageIndex, Constant.PAGENUM);
        jsonObject.put("demos", demos);
        return jsonObject;
    }

    @RequestMapping("addInit")
    public ModelAndView addInit(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        String sellerId = seller.get("id").toString();
        List<Map<String, Object>> employees = demoService.getEmployeesBySellerId(sellerId);
        modelAndView.addObject("employees", employees);
        modelAndView.setViewName("business/demo_add");
        return modelAndView;
    }

    /**
     * 添加作品
     *
     * @param request
     * @param session
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "addDemo", method = RequestMethod.POST)
    @ResponseBody
    public String addDemo(HttpServletRequest request, HttpSession session, Demo demo, @RequestParam MultipartFile file) throws JSONException {
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        int sellerId = Integer.parseInt(seller.get("id").toString());
        JSONObject balkJson = new JSONObject();
        demo.setSellerId(sellerId);
        String fileEName = "";
        String fileName = "";
        if (file != null) {
            fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            demo.setPicName(fileName);
            fileEName = "pic_" + sellerId + "_" + System.currentTimeMillis() + fileSuffix;
            demo.setFileEName(fileEName);
        } else {
            demo.setPicName("");
        }

        try {
            demoService.addDemo(demo);
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
     * 编辑作品
     *
     * @param request
     * @param demoId
     * @return
     */
    @RequestMapping(value = "editDemo")
    public ModelAndView editDemo(HttpServletRequest request, HttpSession session, String demoId) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> demo = demoService.getDemoById(demoId).get(0);

        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        String sellerId = seller.get("id").toString();
        List<Map<String, Object>> employees = demoService.getEmployeesBySellerId(sellerId);
        modelAndView.addObject("employees", employees);

        modelAndView.addObject("demo", demo);
        modelAndView.setViewName("business/demo_edit");
        return modelAndView;
    }

    /**
     * 更新作品
     *
     * @param request
     * @param session
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "updateDemo", method = RequestMethod.POST)
    @ResponseBody
    public String updateDemo(HttpServletRequest request, HttpSession session, Demo demo) throws JSONException {
        JSONObject balkJson = new JSONObject();
        String fileEName = "";
        String fileName = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        if (file != null) {
            fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            demo.setPicName(fileName);
            fileEName = "pic_" + demo.getSellerId() + "_" + System.currentTimeMillis() + fileSuffix;
            demo.setFileEName(fileEName);
        }

        try {
            demoService.updateDemo(demo);
            if (file != null) {
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
     * 删除作品
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "deleteDemo")
    @ResponseBody
    public JSONObject deleteDemo(HttpServletRequest request, String demoId, String fileName) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            demoService.deleteDemo(demoId);
            if (fileName != null && !"".equals(fileName)) {
                String filePath = request.getRealPath("/fileUpload");
                String fileTmp = filePath + "/" + fileName;
                File file = new File(fileTmp);
                file.delete();
            }
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }


    @RequestMapping("getDemoDetail")
    public ModelAndView getDemoDetail(HttpSession session, String demoId) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> demo = demoService.getSingleDemoById(demoId);
        List<Map<String, Object>> comments = demoService.getDemoComment(demoId);
        modelAndView.addObject("demo", demo);
        modelAndView.addObject("comments", comments);
        modelAndView.setViewName("business/demoDetail");
        return modelAndView;
    }


    @RequestMapping("queryAllDemosAdmin")
    public ModelAndView queryAllDemosAdmin(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        int demoCount = demoService.getDemoCount();
        List<Map<String, Object>> demos = demoService.getDemoByPageAdmin(1, Constant.PAGENUM);
        modelAndView.addObject("demos", demos);
        int totalPage = demoCount / Constant.PAGENUM + (demoCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("demoCount", demoCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/body/admin_demo_list");
        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryDemoByPageAdmin")
    @ResponseBody
    public JSONObject queryDemoByPageAdmin(HttpServletRequest request, HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        List<Map<String, Object>> demos = demoService.getDemoByPageAdmin(pageIndex, Constant.PAGENUM);
        jsonObject.put("demos", demos);
        return jsonObject;
    }

    /**
     * 获取所有的作品信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getAllDemos")
    @ResponseBody
    public Map<String, Object> getAllDemos(HttpServletRequest request) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        List<Map<String, Object>> demos = demoService.getAllDemos();
        jsonObject.put("demos", demos);
        return jsonObject;
    }

    /**************************线面是手机端请求地址******************************/
    /**
     * 根据类型 和排序类型查询作品 移动端
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryDemoByTypeMobile")
    @ResponseBody
    public Map<String, Object> queryDemoByTypeMobile(HttpServletRequest request, String demoType, String orderType, String page, String pageSize) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> demos = demoService.queryDemoByTypeMobile(demoType, orderType, page, pageSize);
            jsonObject.put("demos", demos);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }

    /**
     * 获取作品详细信息包括 技师信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryDemoDetailByIdMobile")
    @ResponseBody
    public Map<String, Object> queryDemoDetailByIdMobile(HttpServletRequest request, String demoId, String userId) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> demos = demoService.queryDemoDetailByIdMobile(demoId, userId);
            List<Map<String, Object>> employees = demoService.queryEmployeeDetailByIdMobile(demoId);
            List<Map<String, Object>> commentCounts = employeeService.queryCommentCountByType(employees.get(0).get("id").toString());
            int orderCount = demoService.queryFirstOrder(demoId, userId);
            if (orderCount > 0) {
                jsonObject.put("isFirstBuy", false);
            } else {
                jsonObject.put("isFirstBuy", true);
            }
            jsonObject.put("demo", demos.get(0));
            jsonObject.put("employee", employees.get(0));
            jsonObject.put("commentCounts", commentCounts);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }

    /**
     * 根据预约时间 和地点获取作品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryDemosByTimeMobile")
    @ResponseBody
    public Map<String, Object> queryDemosByTimeMobile(HttpServletRequest request, String cityId, String demoType, String dateType, String hour, String orderType, String page, String pageSize) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        if (demoType == null) {
            demoType = "美甲";
        }
        try {
            List<Map<String, Object>> demos = demoService.queryDemosByTimeMobile(cityId, demoType, dateType, hour, orderType, page, pageSize);
            jsonObject.put("demos", demos);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }

    /**
     * 根据预约时间 和地点获取作品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryDemoAndEmpByNameMobile")
    @ResponseBody
    public Map<String, Object> queryDemoAndEmpByNameMobile(HttpServletRequest request, String type, String name, String demoType) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();

        try {
            if ("0".equals(type)) {
                if (demoType == null) {
                    demoType = "美甲";
                }
                List<Map<String, Object>> demos = demoService.queryDemosByNameMobile(name, demoType);
                jsonObject.put("demos", demos);
                jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
            } else if ("1".equals(type)) {
                List<Map<String, Object>> employees = employeeService.queryEmployeesByName(name);
                jsonObject.put("employees", employees);
                jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
            }
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }
}
