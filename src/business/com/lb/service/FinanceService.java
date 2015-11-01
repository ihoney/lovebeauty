package com.lb.service;

import com.lb.dao.FinanceDao;
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
public class FinanceService {
    @Resource
    private FinanceDao financeDao;

    public List<Map<String, Object>> getCycleFinanceByPage(String sellerId, int pageIndex, int pageSize) {
        return financeDao.getCycleFinanceByPage(sellerId, pageIndex, pageSize);
    }

    public int getCycleFinanceCount(String sellerId) {
        return financeDao.getCycleFinanceCount(sellerId);
    }

    public void applyTransfer(String financeRecordId, String code) {
        financeDao.applyTransfer(financeRecordId, code);
    }

    public int getCycleFinanceCountAdmin() {
        return financeDao.getCycleFinanceCountAdmin();
    }

    public List<Map<String, Object>> getCycleFinanceAdminByPage(int pageIndex, int pageSize) {
        return financeDao.getCycleFinanceAdminByPage(pageIndex, pageSize);
    }
}
