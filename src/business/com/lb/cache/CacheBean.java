package com.lb.cache;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-1-26
 * Time: 下午2:34
 * To change this template use File | Settings | File Templates.
 */
public class CacheBean {

    private static CacheManager cm = null;

    private static CacheBean bean = null;

    private CacheBean() {
    }

    public static CacheBean instance() {
        if (bean == null) {
            bean = new CacheBean();
            cm = getMemCacheManager();
        }
        return bean;
    }

    /**
     * 获取缓存管理器
     */
    private static CacheManager getMemCacheManager() {
        CacheManager cm = CacheManagerFactory.getInstance().getMemCacheManager();
        if (cm == null) {
            throw new ExceptionInInitializerError("Cache Manager Initial Error,Please Check cache config.");
        }
        return cm;
    }

    public void saveUsers(String key, Map<String, String> userMap) {
        cm.add(key, userMap);
    }

    public Map<String, String> getCustomer(String key) {
        return (Map<String, String>) cm.get(key);
    }
}
