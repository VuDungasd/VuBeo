package com.ptit.graduation.service.product.impl;

import com.ptit.graduation.entity.product.BrandMongo;
import com.ptit.graduation.repository.product.BrandMongoRepository;
import com.ptit.graduation.service.base.impl.BaseServiceImpl;
import com.ptit.graduation.service.product.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BrandServiceImpl extends BaseServiceImpl<BrandMongo> implements BrandService {
  private final BrandMongoRepository repository;

  public BrandServiceImpl(BrandMongoRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  public BrandMongo findByName(String name) {
    log.debug("(findByName) name: {}", name);

    return repository.findByName(name);
  }

  @Override
  public void saveAll(List<BrandMongo> brands) {
    repository.saveAll(brands);
  }
}
