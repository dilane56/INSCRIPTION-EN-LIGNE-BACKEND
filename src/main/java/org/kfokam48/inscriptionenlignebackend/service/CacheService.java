package org.kfokam48.inscriptionenlignebackend.service;

public interface CacheService {
    void cacheStats(String key, Object data, int ttlMinutes);
    Object getFromCache(String key);
    void evictCache(String key);
    void clearAllCache();
}