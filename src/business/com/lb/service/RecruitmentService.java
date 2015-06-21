package com.lb.service;

import com.lb.dao.RecruitmentDao;
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
public class RecruitmentService {
    @Resource
    private RecruitmentDao recruitmentDao;

    public void addRecruitmentMobile(String city, String name, String telephone, String workYear, String openShop, String hopeSalary, String comFocus) {
        recruitmentDao.addRecruitmentMobile(city, name, telephone, workYear, openShop, hopeSalary, comFocus);
    }

    public int getRecruitmentCount() {
        return recruitmentDao.getRecruitmentCount();
    }

    public List<Map<String, Object>> getRecruitmentByPage(int pageIndex, int pageSize) {
        return recruitmentDao.getRecruitmentByPage(pageIndex, pageSize);
    }

    public void applyRecruitment(String recruitmentId, String sellerId) {
        recruitmentDao.applyRecruitment(recruitmentId, sellerId);
    }

    public int getRecruitmentCountAdmin() {
        return recruitmentDao.getRecruitmentCountAdmin();
    }

    public List<Map<String, Object>> getRecruitmentByPageAdmin(int pageIndex, int pageSize) {
        return recruitmentDao.getRecruitmentByPageAdmin(pageIndex, pageSize);
    }

    public void checkPassed(String recruitmentId) {
        recruitmentDao.checkPassed(recruitmentId);
    }

    public void checkRefuse(String recruitmentId) {
        recruitmentDao.checkRefuse(recruitmentId);
    }

    public List<Map<String, Object>> onChecking(String sellerId) {
        return recruitmentDao.onChecking(sellerId);
    }

    public List<Map<String, Object>> hasChecked(String sellerId) {
        return recruitmentDao.hasChecked(sellerId);
    }
}
