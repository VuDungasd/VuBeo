package com.ptit.graduation.service.product;

import com.ptit.graduation.entity.base.BaseMongoEntity;
import com.ptit.graduation.entity.product.ProductMongo;
import com.ptit.graduation.repository.product.ProductMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {
  private final ProductMongoRepository productMongoRepository;
  private final RedisTemplate<String, Object> redisTemplate;

  public void addProductToViewed(String userIdOrSession, String productId) {
    String key = "viewed_products:" + userIdOrSession;
    ListOperations<String, Object> listOps = redisTemplate.opsForList();
    long size = listOps.size(key) != null ? listOps.size(key) : 0;

    // Nếu danh sách rỗng, tải sản phẩm từ DB
    if (size == 0) {
      log.info("Redis list is empty. Fetching top 20 products from DB.");
      List<ProductMongo> topProducts = getTop20Products();
      addProductsToRedis(userIdOrSession, topProducts.stream().map(BaseMongoEntity::getId).toList());
    }

    // Đảm bảo danh sách không vượt quá 100 phần tử
    if (size >= 100) {
      listOps.leftPop(key);
    }

    listOps.rightPush(key, productId);
  }

  public List<Object> getViewedProducts(String userIdOrSession) {
    String key = "viewed_products:" + userIdOrSession;
    ListOperations<String, Object> listOps = redisTemplate.opsForList();
    List<Object> viewedProducts = listOps.range(key, 0, -1);

    if (viewedProducts == null || viewedProducts.isEmpty()) {
      log.info("No products in Redis, loading from DB.");
      List<ProductMongo> topProducts = getTop20Products();
      addProductsToRedis(userIdOrSession, topProducts.stream().map(BaseMongoEntity::getId).toList());
      return listOps.range(key, 0, -1);
    }

    return viewedProducts;
  }

  private void addProductsToRedis(String userIdOrSession, List<String> productIds) {
    String key = "viewed_products:" + userIdOrSession;
    ListOperations<String, Object> listOps = redisTemplate.opsForList();
    productIds.forEach(id -> listOps.rightPush(key, id));
  }

  private List<ProductMongo> getTop20Products() {
    return productMongoRepository.findTop20ByOrderByCreationDateDesc(0, 20);
  }
}

