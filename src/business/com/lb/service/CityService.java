package com.lb.service;

import com.lb.dao.CityDao;
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
public class CityService {
    @Resource
    private CityDao cityDao;

    public List<Map<String, Object>> queryCitiesMobile() {
        return cityDao.queryCitiesMobile();
    }

}
