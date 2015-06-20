package com.lb.controller;

import com.lb.bean.Employee;
import com.lb.service.EmployeeService;
import com.lb.utils.Constant;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import java.math.BigDecimal;
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
@RequestMapping(value = "employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @RequestMapping("addInit")
    public ModelAndView addInit(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("business/employee_add");
        return modelAndView;
    }

    /**
     * 添加员工
     *
     * @param request
     * @param session
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "addEmployee", method = RequestMethod.POST)
    @ResponseBody
    public String addEmployee(HttpServletRequest request, HttpSession session, Employee employee) throws JSONException {
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        int sellerId = Integer.parseInt(seller.get("id").toString());
        JSONObject balkJson = new JSONObject();
        employee.setSellerId(sellerId);
        String fileEName = "";
        String fileName;
        if (file != null) {
            fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            fileEName = "pic_" + sellerId + "_empHeadImg_" + System.currentTimeMillis() + fileSuffix;
            employee.setHeadImg(fileEName);
        } else {
            employee.setHeadImg("");
        }

        try {
            employeeService.addEmployee(employee);
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
     * 编辑员工
     *
     * @param employeeId
     * @return
     */
    @RequestMapping(value = "editEmployee")
    public ModelAndView editEmployee(String employeeId) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> employee = employeeService.getEmployeeById(employeeId).get(0);
        modelAndView.addObject("employee", employee);
        modelAndView.setViewName("business/employee_edit");
        return modelAndView;
    }

    /**
     * 更新员工
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "updateEmployee", method = RequestMethod.POST)
    @ResponseBody
    public String updateEmployee(HttpServletRequest request, Employee employee) throws JSONException {
        JSONObject balkJson = new JSONObject();
        String fileEName = "";
        String fileName;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        if (file != null) {
            fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            fileEName = "pic_" + employee.getSellerId() + "_empHeadImg_" + System.currentTimeMillis() + fileSuffix;
            employee.setHeadImg(fileEName);
        }
        try {
            employeeService.updateEmployee(employee);
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
     * 删除员工
     *
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "deleteEmployee")
    @ResponseBody
    public JSONObject deleteEmployee(HttpServletRequest request, String employeeId, String headImg) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            employeeService.deleteEmployee(employeeId);
            if (headImg != null && !"".equals(headImg)) {
                String filePath = request.getRealPath("/fileUpload");
                String fileName = filePath + "/" + headImg;
                File file = new File(fileName);
                file.delete();
            }
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    @RequestMapping("queryAllEmployees")
    public ModelAndView queryAllEmployees(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        int employeeCount = employeeService.getEmployeeCount();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        List<Map<String, Object>> employees = employeeService.getEmployeeByPage(seller.get("id").toString(), 1, Constant.PAGENUM);
        modelAndView.addObject("employees", employees);
        int totalPage = employeeCount / Constant.PAGENUM + (employeeCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("employeeCount", employeeCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/body/employee_list");

        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryEmployeeByPage")
    @ResponseBody
    public Map<String, Object> queryEmployeeByPage(HttpServletRequest request, HttpSession session) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        List<Map<String, Object>> employees = employeeService.getEmployeeByPage(seller.get("id").toString(), pageIndex, Constant.PAGENUM);
        jsonObject.put("employees", employees);
        return jsonObject;
    }

    @RequestMapping("getEmployeeDetail")
    public ModelAndView getEmployeeDetail(String employeeId) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> employee = employeeService.getSingleEmployeeById(employeeId).get(0);
        List<Map<String, Object>> comments = employeeService.getEmployeeComment(employeeId);
        modelAndView.addObject("employee", employee);
        modelAndView.addObject("comments", comments);
        modelAndView.setViewName("business/employeeDetail");
        return modelAndView;
    }


    @RequestMapping("queryAllEmployeesAdmin")
    public ModelAndView queryAllEmployeesAdmin(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        int employeeCount = employeeService.getEmployeeCount();
        List<Map<String, Object>> employees = employeeService.getEmployeeByPageAdmin(1, Constant.PAGENUM);
        modelAndView.addObject("employees", employees);
        int totalPage = employeeCount / Constant.PAGENUM + (employeeCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("employeeCount", employeeCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/body/admin_employee_list");
        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryEmployeeByPageAdmin")
    @ResponseBody
    public JSONObject queryEmployeeByPageAdmin(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        List<Map<String, Object>> employees = employeeService.getEmployeeByPageAdmin(pageIndex, Constant.PAGENUM);
        jsonObject.put("employees", employees);
        return jsonObject;
    }

    /*
    * 移动段请求
    * */

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryEmployeeMobile")
    @ResponseBody
    public Map<String, Object> queryEmployeeMobile(HttpServletRequest request, String page, String pageSize, String orderType) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> employees = employeeService.queryEmployeeMobile(page, pageSize, orderType);
            jsonObject.put("employees", employees);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求服务失败！");
        }

        return jsonObject;
    }

    /**
     * 获取手艺人详细信息包括
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryEmployeeDetailByIdMobile")
    @ResponseBody
    public Map<String, Object> queryEmployeeDetailByIdMobile(HttpServletRequest request, String empId, String userId, String page, String pageSize, String orderType) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> employees = employeeService.queryEmployeeDetailByIdMobile(empId, userId);
            List<Map<String, Object>> XYlevel = employeeService.queryEmployeeXYLevelByIdMobile(empId);
            List<Map<String, Object>> commentCounts = employeeService.queryCommentCountByType(empId);
            jsonObject.put("XYlevel", XYlevel);
            jsonObject.put("commentCounts", commentCounts);
            jsonObject.put("employee", employees.get(0));
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }

    /**
     * 获取手艺人下面的作品信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryEmployeeDemosByIdMobile")
    @ResponseBody
    public Map<String, Object> queryEmployeeDemosByIdMobile(HttpServletRequest request, String empId, String page, String pageSize, String orderType) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> demos = employeeService.queryDemosByIdMobile(empId, page, pageSize, orderType);
            jsonObject.put("demos", demos);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }

    /**
     * 获取手艺人下面的评论信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryEmployeeCommentByIdMobile")
    @ResponseBody
    public Map<String, Object> queryEmployeeCommentByIdMobile(HttpServletRequest request, String empId, String page, String pageSize, String commentType) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> commentCounts = employeeService.queryCommentCountByType(empId);
            List<Map<String, Object>> comments = employeeService.queryEmployeeCommentByIdMobile(empId, page, pageSize, commentType);
            jsonObject.put("commentCounts", commentCounts);
            jsonObject.put("comments", comments);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }

    /**
     * 评论订单信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addEmployeeCommentByIdMobile")
    @ResponseBody
    public Map<String, Object> addEmployeeCommentByIdMobile(HttpServletRequest request, String empId, String userId, String commentType, BigDecimal majorScore, BigDecimal comScore, BigDecimal punctualScore, String comment) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            employeeService.addEmployeeCommentByIdMobile(empId, userId, commentType, majorScore, comScore, punctualScore, comment);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }

    /**
     * 获取手艺人的预约时间 闲忙信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryEmployeeBookInfoByIdMobile")
    @ResponseBody
    public Map<String, Object> queryEmployeeBookInfoByIdMobile(HttpServletRequest request, String empId) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            Map<String, Object> bookInfo = employeeService.queryEmployeeBookInfoByIdMobile(empId).get(0);
            jsonObject.put("bookInfo", bookInfo);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }

    /**
     * 根据预约时间 和地点获取手艺人
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryEmployeesByTimeMobile")
    @ResponseBody
    public Map<String, Object> queryEmployeesByTimeMobile(HttpServletRequest request, String cityId, String dateType, String hour, String orderType, String page, String pageSize) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> employees = employeeService.queryEmployeesByTimeMobile(cityId, dateType, hour, orderType, page, pageSize);
            jsonObject.put("employees", employees);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败！");
        }
        return jsonObject;
    }
}
