package com.lb.service;

import com.lb.bean.SellerValidateInfo;
import com.lb.dao.SellerDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SellerService {
    @Resource
    private SellerDao sellerDao;

    public List<Map<String, Object>> existsSeller(String account, String password) {
        return sellerDao.findSeller(account, password);
    }

    public void register(String account, String password, String regIp) {
        sellerDao.register(account, password, regIp);
    }


    public int getSellerCount() {
        return sellerDao.getSellerCount();
    }

    public List<Map<String, Object>> getSellerByPage(int pageIndex, int pageSize) {
        return sellerDao.getSellerByPage(pageIndex, pageSize);
    }

    public void checkSeller(String sellerId) {
        sellerDao.checkSeller(sellerId);
    }

    /**
     * 每次登陆更新登陆信息
     *
     * @param sellerId
     * @param logIp
     */
    public void updateLoginTimeAndIp(String sellerId, String logIp) {
        sellerDao.updateLoginTimeAndIp(sellerId, logIp);
    }

    /**
     * 得到卖家的认证信息
     *
     * @param sellerId
     * @return
     */
    public List<Map<String, Object>> getSellerAuthInfo(String sellerId) {
        return sellerDao.getSellerAuthInfo(sellerId);
    }

    /**
     * 添加认证信息
     *
     * @param sellerValidateInfo
     */
    public void addAuth(SellerValidateInfo sellerValidateInfo) {
        sellerDao.addAuth(sellerValidateInfo);
        sellerDao.setAuth(sellerValidateInfo.getSellerId());
    }

    public void updateAuth(SellerValidateInfo sellerValidateInfo) {
        sellerDao.updateAuth(sellerValidateInfo);
    }

    public void deleteSeller(String sellerId) {
        sellerDao.deleteSeller(sellerId);
    }

    public List<Map<String, Object>> getValidateInfo(String sellerId) {
        return sellerDao.getValidateInfo(sellerId);
    }

    public void changePwd(String sellerId, String newPassword) {
        sellerDao.changePwd(sellerId, newPassword);
    }

    public void forbiddenSeller(String sellerId) {
        sellerDao.forbiddenSeller(sellerId);
    }

    public void reUseSeller(String sellerId) {
        sellerDao.reUseSeller(sellerId);
    }

    public List<Map<String,Object>> getSingleSellerById(String sellerId) {
        return sellerDao.getSingleSellerById(sellerId);
    }

    public List<Map<String, Object>> queryAllSellers() {
        return sellerDao.queryAllSellers();
    }
}
