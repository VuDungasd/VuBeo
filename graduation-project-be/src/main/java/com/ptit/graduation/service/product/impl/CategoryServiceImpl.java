package com.ptit.graduation.service.product.impl;

import com.ptit.graduation.entity.product.CategoryMongo;
import com.ptit.graduation.repository.product.CategoryMongoRepository;
import com.ptit.graduation.service.base.impl.BaseServiceImpl;
import com.ptit.graduation.service.product.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMongo> implements CategoryService {
  private final CategoryMongoRepository repository;

  public CategoryServiceImpl(CategoryMongoRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  public CategoryMongo findByName(String name) {
    log.debug("(findByName) name: {}", name);

    return repository.findByName(name);
  }

  @Override
  public CategoryMongo findByNameAndType(String name, String type) {
    log.debug("(findByNameAndType) name: {}, type: {}", name, type);

    return repository.findByNameAndType(name, type);
  }

  @Override
  public void saveAll(List<CategoryMongo> categories) {
    repository.saveAll(categories);
  }
}
