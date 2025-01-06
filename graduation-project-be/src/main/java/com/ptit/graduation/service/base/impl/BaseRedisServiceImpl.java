package com.ptit.graduation.service.base.impl;

import com.ptit.graduation.service.base.BaseRedisService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseRedisServiceImpl implements BaseRedisService {
  private final RedisTemplate<String, Object> redisTemplate;
  private final HashOperations<String, String, Object> hashOperations;

  public BaseRedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.hashOperations = redisTemplate.opsForHash();
  }

  @Override
  public void set(String key, Object value) {
    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public void setTimeToLive(String key, long timeoutInDays) {
    redisTemplate.expire(key, timeoutInDays, TimeUnit.DAYS);
  }

  @Override
  public void hashSet(String key, String field, Object value) {
    hashOperations.put(key, field, value);
  }


  @Override
  public boolean hashExists(String key, String field) {
    return hashOperations.hasKey(key, field);
  }

  @Override
  public Object get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public Map<String, Object> getField(String key) {
    return hashOperations.entries(key);
  }

  @Override
  public Object hashGet(String key, String field) {
    return hashOperations.get(key, field);
  }

  @Override
  public void delete(String key) {
    redisTemplate.delete(key);
  }

  @Override
  public void delete(String key, String field) {
    hashOperations.delete(key, field);
  }

}
