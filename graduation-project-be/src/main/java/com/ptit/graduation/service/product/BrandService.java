package com.ptit.graduation.service.product;

import com.ptit.graduation.entity.product.BrandMongo;
import com.ptit.graduation.service.base.BaseService;

import java.util.List;

public interface BrandService extends BaseService<BrandMongo> {
  BrandMongo findByName(String name);

  void saveAll(List<BrandMongo> brands);
}
