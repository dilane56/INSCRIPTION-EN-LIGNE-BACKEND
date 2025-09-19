package org.kfokam48.inscriptionenlignebackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kfokam48.inscriptionenlignebackend.service.CacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@ConditionalOnClass(RedisTemplate.class)
public class CacheServiceImpl implements CacheService {
    
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    
    public CacheServiceImpl(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void cacheStats(String key, Object data, int ttlMinutes) {
        try {
            String jsonData = objectMapper.writeValueAsString(data);
            redisTemplate.opsForValue().set("stats:" + key, jsonData, ttlMinutes, TimeUnit.MINUTES);
        } catch (Exception e) {
            // Log silencieux
        }
    }

    @Override
    public Object getFromCache(String key) {
        try {
            String jsonData = redisTemplate.opsForValue().get("stats:" + key);
            return jsonData != null ? objectMapper.readValue(jsonData, Object.class) : null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void evictCache(String key) {
        redisTemplate.delete("stats:" + key);
    }

    @Override
    public void clearAllCache() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }
}