package com.lb.controller;

import com.lb.bean.Ad;
import com.lb.service.AdService;
import com.lb.service.CityService;
import com.lb.service.DemoService;
import com.lb.service.SellerService;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Adistrator
 * Date: 15-5-31
 * Time: 上午12:52
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "ad")
public class AdController {

    @Resource
    private AdService adService;

    @Resource
    private CityService cityService;

    @Resource
    private SellerService sellerService;

    @Resource
    private DemoService demoService;

    /**
     * 查看所有广告
     *
     * @param request
     * @return
     */
    @RequestMapping("queryAllAds")
    public ModelAndView queryAllAds(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        int adCount = adService.getAdCount();
        List<Map<String, Object>> ads = adService.getAdByPage(1, Constant.PAGENUM);
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
        List<Map<String, Object>> ads = adService.getAdByPage(pageIndex, Constant.PAGENUM);
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
        adService.changeAdState(adId, state);
        return jsonObject;
    }

    /**
     * 初始化添加广告页面
     *
     * @return
     */
    @RequestMapping("addAdInit")
    public ModelAndView addAdInit(String type) {
        ModelAndView modelAndView = new ModelAndView();
        String page;
        if (type == null || "1".equals(type)) {
            page = "business/ad_add_1";
        } else {
            List<Map<String, Object>> cities = cityService.queryCitiesMobile();
            List<Map<String, Object>> sellers = sellerService.queryAllSellers();
            List<Map<String, Object>> demos = demoService.queryAllDemos();
            modelAndView.addObject("cities", cities);
            modelAndView.addObject("sellers", sellers);
            modelAndView.addObject("demos", demos);
            page = "business/ad_add_2";
        }
        modelAndView.setViewName(page);
        return modelAndView;
    }

    /**
     * 添加城市
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "addAd")
    @ResponseBody
    public String addAd(HttpServletRequest request, Ad ad) {
        JSONObject balkJson = new JSONObject();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        String fileEName = "";
        String fileName;
        if (file != null) {
            fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            fileEName = "pic_ad_" + System.currentTimeMillis() + fileSuffix;
            ad.setPicName(fileEName);
        } else {
            ad.setPicName("");
        }
        try {
            adService.addAd(ad);
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
        } catch (Exception e) {
            balkJson.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return balkJson.toString();
    }

    /**
     * 更新作品
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "updateAd", method = RequestMethod.POST)
    @ResponseBody
    public String updateAd(HttpServletRequest request, Ad ad) throws JSONException {
        JSONObject balkJson = new JSONObject();
        String fileEName = "";
        String fileName = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        if (file != null) {
            fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            fileEName = "pic_ad_" + System.currentTimeMillis() + fileSuffix;
            ad.setPicName(fileEName);
        }

        try {
            adService.updateAd(ad);
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
     * 删除广告
     *
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "deleteAd")
    @ResponseBody
    public JSONObject deleteAd(HttpServletRequest request, String adId, String fileName) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            adService.deleteAd(adId);
            if (fileName != null && !"".equals(fileName)) {
                String filePath = request.getRealPath("/fileUpload");
                String fileNameTmp = filePath + "/" + fileName;
                File file = new File(fileNameTmp);
                file.delete();
            }
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 移动端获取广告
     *
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "queryAdsMobile")
    @ResponseBody
    public Map<String, Object> queryAdsMobile(HttpServletRequest request) throws JSONException {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> ads = adService.queryAdsMobile();
            jsonObject.put("ads", ads);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "请求失败!");
        }
        return jsonObject;
    }
}
