package com.ptit.graduation.service.product;

import com.ptit.graduation.entity.product.LocationMongo;
import com.ptit.graduation.service.base.BaseService;

import java.util.List;

public interface LocationService extends BaseService<LocationMongo> {
  void saveAll(List<LocationMongo> locations);

  LocationMongo findByName(String name);

}
