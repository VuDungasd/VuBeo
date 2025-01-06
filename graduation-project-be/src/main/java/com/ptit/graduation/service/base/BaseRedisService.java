package com.ptit.graduation.service.base;


import java.util.Map;

public interface BaseRedisService {
  void set(String key, Object value);

  void setTimeToLive(String key, long timeoutInDays);

  void hashSet(String key, String field, Object value);

  boolean hashExists(String key, String field);

  Object get(String key);

  Map<String, Object> getField(String key);

  Object hashGet(String key, String field);

  void delete(String key);

  void delete(String key, String field);

}
