package com.lb.service;

import com.lb.bean.Ad;
import com.lb.dao.AdDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Adistrator
 * Date: 14-9-26
 * Time: 上午8:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AdService {
    @Resource
    private AdDao adDao;


    public int getAdCount() {
        return adDao.getAdCount();
    }

    public List<Map<String, Object>> getAdByPage(int pageIndex, int pageSize) {
        return adDao.getAdByPage(pageIndex, pageSize);
    }

    public void changeAdState(String adId, String state) {
        adDao.changeAdState(adId, state);
    }

    public void addAd(Ad ad) {
        adDao.addAd(ad);
    }

    public List<Map<String, Object>> getAdById(String adId) {
        return adDao.getAdById(adId);
    }

    public void updateAd(Ad ad) {
        adDao.updateAd(ad);
    }

    public void deleteAd(String adId) {
        adDao.deleteAd(adId);
    }

    public List<Map<String, Object>> queryAdsMobile() {
        return adDao.queryAdsMobile();
    }
}
