package com.ptit.graduation.service.product.impl;

import com.ptit.graduation.entity.product.LocationMongo;
import com.ptit.graduation.repository.product.LocationMongoRepository;
import com.ptit.graduation.service.base.impl.BaseServiceImpl;
import com.ptit.graduation.service.product.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LocationServiceImpl extends BaseServiceImpl<LocationMongo> implements LocationService {
  private final LocationMongoRepository repository;

  public LocationServiceImpl(LocationMongoRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  public void saveAll(List<LocationMongo> locations) {
    repository.saveAll(locations);
  }

  @Override
  public LocationMongo findByName(String name) {
    return repository.findByName(name);
  }
}
