package com.lb.service;

import com.lb.dao.ScheduleOperateDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 定时操作
 */
@Service
public class ScheduleOperateService {

    @Resource
    private ScheduleOperateDao scheduleOperateDao;

    /**
     * 每小时检查一次是否有过期的订单
     */
    public void abortOrder() {
        scheduleOperateDao.abortOrder();
        System.out.println("abortOrder");
    }

    /**
     * 每天修改一次预约情况
     */
    public void changeBookTime() {
        scheduleOperateDao.changeBookTime();
        System.out.println("changeBookTime");
    }

    /**
     * 每小时修改预约情况
     */
    public void changeBookTimeHour() {
        scheduleOperateDao.changeBookTimeHour();
        System.out.println("changeBookTimeHour");
    }
}
