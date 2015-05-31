package com.lb.service;

import com.lb.dao.CommonAddressDao;
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
public class CommonAddressService {
    @Resource
    private CommonAddressDao commonAddressDao;

    public List<Map<String, Object>> queryAllAddressMobile(String userId) {
        return commonAddressDao.queryAllAddressMobile(userId);
    }

    public void addAddressMobile(String userId, String address) {
        commonAddressDao.addAddressMobile(userId, address);
    }

    public void deleteAddressMobile(String addressId) {
        commonAddressDao.deleteAddressMobile(addressId);
    }
}
