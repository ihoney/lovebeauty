package com.lb.cache;

public class CacheManagerFactory {

    private CacheManagerFactory() {
    }

    private static CacheManagerFactory cmf = null;

    /**
     * 获取CacheManagerFactory实例
     */
    public static CacheManagerFactory getInstance() {
        if (cmf == null) {
            cmf = new CacheManagerFactory();
        }
        return cmf;
    }

    /**
     * 获取MemcachedManager
     */
    public CacheManager getMemCacheManager() {
        return new MemcacheCacheManager();
    }
}
