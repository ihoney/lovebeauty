package com.lb.controller;

import com.lb.bean.SellerValidateInfo;
import com.lb.service.CityService;
import com.lb.service.SellerService;
import com.lb.utils.Constant;
import com.lb.utils.DateUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
@RequestMapping(value = "seller")
public class SellerController {

    @Resource
    private SellerService sellerService;

    @Resource
    private CityService cityService;

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
        List<Map<String, Object>> sellers = sellerService.existsSeller(account, password);
        if (sellers != null && sellers.size() > 0) {
            Map<String, Object> seller = sellers.get(0);
            sellerService.updateLoginTimeAndIp(seller.get("id") + "", request.getRemoteAddr());
            session.setAttribute("seller", seller);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } else {
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
    @RequestMapping(value = "register")
    @ResponseBody
    public JSONObject register(HttpServletRequest request, String account, String password) {
        JSONObject jsonObject = new JSONObject();
        String regIp = request.getRemoteAddr();
        try {
            sellerService.register(account, password, regIp);
            jsonObject.put(Constant.TIPMESSAGE, "恭喜，注册成功！");
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put("message", "用户已存在！");
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
        session.removeAttribute("seller");
        jsonObject.put(Constant.TIPMESSAGE, "exit success！");
        return jsonObject;
    }

    @RequestMapping("queryAllSellers")
    public ModelAndView queryAllSellers(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        int empCount = sellerService.getSellerCount();
        List<Map<String, Object>> sellers = sellerService.getSellerByPage(1, Constant.PAGENUM);
        modelAndView.addObject("sellers", sellers);
        int totalPage = empCount / Constant.PAGENUM + (empCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("sellerCount", empCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.setViewName("frame/admin/body/seller_list");
        return modelAndView;
    }

    /**
     * 禁用商铺
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "forbiddenSeller")
    @ResponseBody
    public JSONObject forbiddenSeller(HttpServletRequest request, String sellerId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            sellerService.forbiddenSeller(sellerId);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 启用商铺
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "reUseSeller")
    @ResponseBody
    public JSONObject reUseSeller(HttpServletRequest request, String sellerId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            sellerService.reUseSeller(sellerId);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "querySellerByPage")
    @ResponseBody
    public Map<String, Object> querySellerByPage(HttpServletRequest request) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        int pageIndex = Integer.parseInt(request.getParameter("curPage"));
        List<Map<String, Object>> sellers = sellerService.getSellerByPage(pageIndex, Constant.PAGENUM);
        jsonObject.put("sellers", sellers);
        return jsonObject;
    }

    /**
     * 单个商铺审核
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "checkSeller")
    @ResponseBody
    public JSONObject checkSeller(HttpServletRequest request, String sellerId) {
        JSONObject jsonObject = new JSONObject();
        try {
            sellerService.checkSeller(sellerId);
            jsonObject.put("checkTime", DateUtil.cruTimeStr());
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 初始化个人信息认证界面
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("authInit")
    public ModelAndView authInit(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        modelAndView.addObject("seller", seller);
        List<Map<String, Object>> cities = cityService.queryCitiesMobile();
        modelAndView.addObject("cities", cities);
        if ("否".equals(seller.get("authed").toString())) {
            modelAndView.setViewName("business/seller_auth");
        } else {
            Map<String, Object> sellerAuthInfo = sellerService.getSellerAuthInfo(seller.get("id").toString()).get(0);
            modelAndView.addObject("sellerAuthInfo", sellerAuthInfo);
            modelAndView.setViewName("business/seller_auth_update");
        }
        return modelAndView;
    }

    /**
     * 添加个人信息认证界面
     *
     * @return
     */
    @RequestMapping("addAuth")
    @ResponseBody
    public String addAuth(HttpServletRequest request, HttpSession session, SellerValidateInfo sellerValidateInfo) {
        JSONObject balkJson = new JSONObject();
        String headImgStr = "";
        String identifyImgStr = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile headImg = multipartRequest.getFile("headImg");
        MultipartFile identifyImg = multipartRequest.getFile("identifyImg");
        InputStream is = null;
        InputStream is2 = null;
        FileOutputStream fos = null;
        FileOutputStream fos2 = null;
        String path = request.getRealPath("fileUpload");
        try {
            if (headImg != null) {
                headImgStr = headImg.getOriginalFilename();
                String fileSuffix = headImgStr.substring(headImgStr.lastIndexOf("."));
                headImgStr = "pic_head_" + System.currentTimeMillis() + fileSuffix;
                sellerValidateInfo.setHeadImgStr(headImgStr);
            } else {
                sellerValidateInfo.setHeadImgStr("");
            }
            Thread.sleep(30);
            if (identifyImg != null) {
                identifyImgStr = identifyImg.getOriginalFilename();
                String fileSuffix = identifyImgStr.substring(identifyImgStr.lastIndexOf("."));
                identifyImgStr = "pic_identify_" + System.currentTimeMillis() + fileSuffix;
                sellerValidateInfo.setIdentifyImgStr(identifyImgStr);
            } else {
                sellerValidateInfo.setIdentifyImgStr("");
            }
            sellerService.addAuth(sellerValidateInfo);

            if (headImg != null) {
                is = headImg.getInputStream();
                fos = new FileOutputStream(path + "/" + headImgStr);
                byte[] buf = new byte[1024];
                int len;
                while ((len = is.read()) > 0) {
                    fos.write(buf, 0, len);
                }
                fos.close();
            }

            if (identifyImg != null) {
                is2 = identifyImg.getInputStream();
                fos2 = new FileOutputStream(path + "/" + headImgStr);
                byte[] buf = new byte[1024];
                int len;
                while ((len = is2.read()) > 0) {
                    fos2.write(buf, 0, len);
                }
                fos2.close();
            }
            Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
            seller.put("authed", true);
            session.setAttribute("seller", seller);
            balkJson.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            balkJson.put(Constant.REQRESULT, Constant.REQFAILED);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (is2 != null) {
                try {
                    is2.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos2 != null) {
                try {
                    fos2.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return balkJson.toString();
    }

    /**
     * 添加个人信息认证界面
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("updateAuth")
    @ResponseBody
    public String updateAuth(HttpServletRequest request, HttpSession session, SellerValidateInfo sellerValidateInfo) {
        JSONObject balkJson = new JSONObject();
        String headImgStr = "";
        String identifyImgStr = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile headImg = multipartRequest.getFile("headImg");
        MultipartFile identifyImg = multipartRequest.getFile("identifyImg");
        InputStream is = null;
        InputStream is2 = null;
        FileOutputStream fos = null;
        FileOutputStream fos2 = null;
        String path = request.getRealPath("fileUpload");
        try {
            if (headImg != null) {
                headImgStr = headImg.getOriginalFilename();
                String fileSuffix = headImgStr.substring(headImgStr.lastIndexOf("."));
                headImgStr = "pic_head_" + System.currentTimeMillis() + fileSuffix;
                sellerValidateInfo.setHeadImgStr(headImgStr);
            }
            Thread.sleep(30);
            if (identifyImg != null) {
                identifyImgStr = identifyImg.getOriginalFilename();
                String fileSuffix = identifyImgStr.substring(identifyImgStr.lastIndexOf("."));
                identifyImgStr = "pic_identify_" + System.currentTimeMillis() + fileSuffix;
                sellerValidateInfo.setIdentifyImgStr(identifyImgStr);
            }
            sellerService.updateAuth(sellerValidateInfo);
            if (headImg != null) {
                is = headImg.getInputStream();
                fos = new FileOutputStream(path + "/" + headImgStr);
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = is.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                }
                fos.close();
            }

            if (identifyImg != null) {
                is2 = identifyImg.getInputStream();
                fos2 = new FileOutputStream(path + "/" + identifyImgStr);
                byte[] buf = new byte[1024];
                int len;
                while ((len = is2.read(buf)) > 0) {
                    fos2.write(buf, 0, len);
                }
                fos2.close();
            }
            balkJson.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            balkJson.put(Constant.REQRESULT, Constant.REQFAILED);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (is2 != null) {
                try {
                    is2.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos2 != null) {
                try {
                    fos2.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return balkJson.toString();
    }

    /**
     * 删除商铺
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "deleteSeller")
    @ResponseBody
    public JSONObject deleteSeller(HttpServletRequest request, String sellerId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            sellerService.deleteSeller(sellerId);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 获取认证信息
     *
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "getValidateInfo")
    @ResponseBody
    public JSONObject getValidateInfo(String sellerId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        List<Map<String, Object>> validateInfos = sellerService.getValidateInfo(sellerId);
        jsonObject.put("validateInfo", validateInfos.get(0));
        return jsonObject;
    }

    /**
     * 初始化修改密码界面
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "changePwdInit")
    public String changePwdInit(HttpServletRequest request) throws JSONException {
        return "business/changePassword";
    }

    /**
     * 修改密码
     *
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "changePwd")
    @ResponseBody
    public JSONObject changePwd(HttpSession session, String oldPassword, String newPassword) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        String sellerId = seller.get("id") + "";
        String password = seller.get("password") + "";
        if (!password.equals(oldPassword)) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "当前密码输入错误！");
        } else {
            try {
                sellerService.changePwd(sellerId, newPassword);
                seller.put("password", newPassword);
                session.setAttribute("seller", seller);
                jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
            } catch (Exception e) {
                jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
                jsonObject.put(Constant.TIPMESSAGE, "操作失败！");
            }
        }
        return jsonObject;
    }

    /**
     * flexTest
     *
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "flexTest")
    public String flexTest(HttpSession session) throws JSONException {
        return "frame/flexTest";
    }


    /**
     * flex加载数据
     *
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "flexLoadData")
    @ResponseBody
    public Map<String, Object> flexLoadData(HttpServletRequest request) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();
        int pageIndex = Integer.parseInt(request.getParameter("page"));
        String pageSize = request.getParameter("rp");
        String qtype = request.getParameter("qtype");
        String query = request.getParameter("query");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapTmp;

        if (query != null && !"".equals(query)) {
            mapTmp = new HashMap<String, Object>();
            mapTmp.put("id", 45);
            mapTmp.put("cell", new Object[]{45, "job_name" + query, "work_address" + query, "salary" + query, "date" + query, "language" + query});
            list.add(mapTmp);
        } else {
            for (int i = 1; i < 30; i++) {
                mapTmp = new HashMap<String, Object>();
                mapTmp.put("id", i * pageIndex);
                mapTmp.put("cell", new Object[]{i, "job_name" + i * pageIndex, "work_address" + i * pageIndex, "salary" + i * pageIndex, "date" + i * pageIndex, "language" + i * pageIndex});
                list.add(mapTmp);
            }
        }
        map.put("page", pageIndex);
        map.put("total", 1000);
        map.put("rows", list);
        return map;
    }

    @RequestMapping("getSellerDetail")
    public ModelAndView getSellerDetail(String sellerId) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> seller = sellerService.getSingleSellerById(sellerId).get(0);
        modelAndView.addObject("seller", seller);
        modelAndView.setViewName("business/sellerDetail");
        return modelAndView;
    }
}


