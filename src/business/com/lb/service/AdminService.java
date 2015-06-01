package com.lb.service;

import com.lb.bean.Ad;
import com.lb.dao.AdminDao;
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
public class AdminService {
    @Resource
    private AdminDao adminDao;

    public List<Map<String, Object>> existsAdmin(String account, String password) {
        return adminDao.findAdmin(account, password);
    }

    public int getCityCount() {
        return adminDao.getCityCount();
    }

    public List<Map<String, Object>> getCityByPage(int pageIndex, int pageSize) {
        return adminDao.getCityByPage(pageIndex, pageSize);
    }

    public void addCity(String cityName, String serviceScope) {
        adminDao.addCity(cityName, serviceScope);
    }

    public void changeCityState(String cityId, String state) {
        adminDao.changeCityState(cityId, state);
    }

    public int getAdCount() {
        return adminDao.getAdCount();
    }

    public List<Map<String, Object>> getAdByPage(int pageIndex, int pageSize) {
        return adminDao.getAdByPage(pageIndex, pageSize);
    }

    public void changeAdState(String adId, String state) {
        adminDao.changeAdState(adId, state);
    }

    public void addAd(Ad ad) {
        adminDao.addAd(ad);
    }

    public List<Map<String, Object>> getAdById(String adId) {
        return adminDao.getAdById(adId);
    }

    public void updateAd(Ad ad) {
        adminDao.updateAd(ad);
    }

    public void deleteAd(String adId) {
        adminDao.deleteAd(adId);
    }

    public List<Map<String, Object>> queryCityServiceScopeMobile(String cityName) {
        return adminDao.queryCityServiceScopeMobile(cityName);
    }

    public void updateCityServiceScope(String cityId, String serviceScope) {
        adminDao.updateCityServiceScope(cityId, serviceScope);
    }
}
