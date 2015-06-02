package com.lb.service;

import com.lb.bean.Demo;
import com.lb.dao.DemoDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-26
 * Time: 上午8:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DemoService {
    @Resource
    private DemoDao demoDao;

    public int getDemoCount() {
        return demoDao.getDemoCount();
    }

    public List<Map<String, Object>> getDemoByPage(String sellerId, int pageIndex, int pageSize) {
        return demoDao.getDemoByPage(sellerId, pageIndex, pageSize);
    }

    /**
     * 添加作品
     *
     * @param demo
     */
    public void addDemo(Demo demo) {
        demoDao.addDemo(demo);
    }

    /**
     * 编辑作品
     *
     * @param demo
     */
    public void updateDemo(Demo demo) {
        demoDao.updateDemo(demo);
    }

    public void deleteDemo(String demoId) {
        demoDao.deleteDemo(demoId);
    }

    /**
     * 根据Id获取作品i信息
     *
     * @param demoId
     * @return
     */
    public List<Map<String, Object>> getDemoById(String demoId) {
        return demoDao.getDemoById(demoId);
    }

    public Map<String, Object> getSingleDemoById(String demoId) {
        return demoDao.getSingleDemoById(demoId);
    }

    public List<Map<String, Object>> getDemoComment(String demoId) {
        return demoDao.getDemoComment(demoId);
    }

    public List<Map<String, Object>> getDemoByPageAdmin(int pageIndex, int pageSize) {
        return demoDao.getDemoByPageAdmin(pageIndex, pageSize);
    }

    public List<Map<String, Object>> getEmployeesBySellerId(String sellerId) {
        return demoDao.getEmployeesBySellerId(sellerId);
    }

    public List<Map<String, Object>> queryDemoByTypeMobile(String demoType, String orderType, String page, String pageSize) {
        return demoDao.queryDemoByTypeMobile(demoType, orderType, page, pageSize);
    }

    public List<Map<String, Object>> queryDemoDetailByIdMobile(String demoId, String userId) {
        return demoDao.queryDemoDetailByIdMobile(demoId, userId);
    }

    public List<Map<String, Object>> queryEmployeeDetailByIdMobile(String demoId) {
        return demoDao.queryEmployeeDetailByIdMobile(demoId);
    }
}
