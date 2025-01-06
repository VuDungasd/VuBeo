package com.ptit.graduation.service.product.impl;

import com.ptit.graduation.dto.response.product.ProductResponse;
import com.ptit.graduation.entity.product.ProductMongo;
import com.ptit.graduation.repository.product.ProductMongoRepository;
import com.ptit.graduation.service.product.ProductRedisService;
import com.ptit.graduation.utils.ConvertVietnameseToNormalText;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.ptit.graduation.constants.GraduationProjectConstants.CommonConstants.PRODUCT_HASH_KEY;
import static com.ptit.graduation.constants.GraduationProjectConstants.CommonConstants.AUTO_SUGGEST_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRedisServiceImpl implements ProductRedisService {
  // private static final String PRODUCT_HASH_KEY = "Products";
  // private static final String AUTO_SUGGEST_KEY = "AutoSuggestKey";

//  private final ProductMongoRepository productMongoRepository;

  private final RedisTemplate<String, Object> redisTemplate;

  private HashOperations<String, String, Object> hashOperations;

  @PostConstruct
  private void init() {
    hashOperations = redisTemplate.opsForHash();
  }

  @Override
  public void saveAll(List<ProductResponse> products, int timeout, TimeUnit unit) {
    for (ProductResponse product : products) {
      save(product);
    }
    redisTemplate.expire(PRODUCT_HASH_KEY, timeout, unit);
  }

  @Override
  public void save(ProductResponse product) {
    hashOperations.put(PRODUCT_HASH_KEY, product.getId(), product);
  }

  @Override
  public List<ProductResponse> getAll(int skip, int limit) {
    return hashOperations.entries(PRODUCT_HASH_KEY).values().stream()
        .skip(skip)
        .limit(limit)
        .map(product -> (ProductResponse) product)
        .collect(Collectors.toList());
  }

  @Override
  public ProductResponse get(String productId) {
    return (ProductResponse) hashOperations.get(PRODUCT_HASH_KEY, productId);
  }

  @Override
  public boolean checkProductsExist() {
    return redisTemplate.hasKey(PRODUCT_HASH_KEY);
  }

  @Override
  public void clearProdducts() {
    redisTemplate.delete(PRODUCT_HASH_KEY);
  }

  @Override
  public void setKeysAutoSuggest(List<String> keys, int timeout, TimeUnit unit) {
    // [{ "m": ["máy tính&&may-tinh", "mè&&me"] }, { "b": ["bánh&&banh"] }]
    List<Map<String, List<String>>> mapkeys = new ArrayList<>();
    ConvertVietnameseToNormalText convert = new ConvertVietnameseToNormalText();
    for (int i = 0; i < keys.size(); i++) {
      String key = keys.get(i).trim().toLowerCase();
      String keySlug = convert.slugify(key);
      String prefixTmp = keySlug.substring(0, 1);

      boolean isExist = false;
      for (int j = 0; j < mapkeys.size(); j++) {
        if (mapkeys.get(j).containsKey(prefixTmp)) {
          mapkeys.get(j).get(prefixTmp).add(key + "&&" + keySlug);
          isExist = true;
          break;
        }
      }
      if (!isExist) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(key + "&&" + keySlug);
        map.put(prefixTmp, list);
        mapkeys.add(map);
      }
    }
    redisTemplate.opsForList().rightPushAll(AUTO_SUGGEST_KEY, mapkeys.toArray());
    redisTemplate.expire(AUTO_SUGGEST_KEY, timeout, unit);
  }

  @Override
  public List<Object> getKeys() {
    return redisTemplate.opsForList().range(AUTO_SUGGEST_KEY, 0, -1);
  }

  @Override
  public boolean checkAutoSuggestExist() {
    return redisTemplate.hasKey(AUTO_SUGGEST_KEY);
  }

  @Override
  public void clearAutoSuggest() {
    redisTemplate.delete(AUTO_SUGGEST_KEY);
  }

  @Override
  public void clearAllKeysInRedis() {
    Set<String> keys = redisTemplate.keys("*");
    if (keys != null) {
      redisTemplate.delete(keys);
    }
  }
}
