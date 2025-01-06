package com.ptit.graduation.initialization;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ptit.graduation.service.product.ProductMongoService;
import com.ptit.graduation.service.product.ProductRedisService;

import lombok.extern.slf4j.Slf4j;

import com.ptit.graduation.dto.response.product.ProductListResponse;

import static com.ptit.graduation.constants.GraduationProjectConstants.CommonConstants.TIME_DAYS;
import static com.ptit.graduation.constants.GraduationProjectConstants.CommonConstants.PRODUCTS_IN_REDIS;

@Slf4j
@Component
public class ProductsIntoRedisInit implements CommandLineRunner {

  @Autowired
  private ProductMongoService productMongoService;
  @Autowired
  private ProductRedisService productRedisService;

  @Override
  public void run(String... args) throws Exception {
    try {
      log.info("ProductsIntoRedisInit started");
      if(productRedisService.checkProductsExist()){
        log.info("Products already exist");
        // productRedisService.clearProdducts();
        // ProductListResponse products = productMongoService.getAll(0, PRODUCTS_IN_REDIS);
        // productRedisService.saveAll(products.getProducts(), TIME_DAYS, TimeUnit.DAYS);
        return;
      }
      ProductListResponse products = productMongoService.getAll(0, PRODUCTS_IN_REDIS);
      productRedisService.saveAll(products.getProducts(), TIME_DAYS, TimeUnit.DAYS);
      log.info("ProductsIntoRedisInit finished");
    } catch (Exception e) {
      log.info(e.getMessage());
      log.error("ProductsIntoRedisInit failed");
    }
  }

}
