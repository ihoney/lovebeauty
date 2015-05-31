package com.lb.service;

import com.lb.dao.FavoriteDao;
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
public class FavoriteService {
    @Resource
    private FavoriteDao favoriteDao;

    public List<Map<String, Object>> queryAllFavoriteMobile(String userId, String type, String page, String pageSize) {
        return favoriteDao.queryAllFavoriteMobile(userId, type, page, pageSize);
    }

    public void addFavoriteMobile(String userId, String type, String entityId) {
        favoriteDao.addFavoriteMobile(userId, type, entityId);
    }

    public void deleteFavoriteMobile(String userId, String type, String entityId) {
        favoriteDao.deleteFavoriteMobile(userId, type, entityId);
    }
}
