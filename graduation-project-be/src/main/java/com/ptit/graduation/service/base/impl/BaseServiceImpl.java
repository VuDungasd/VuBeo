package com.ptit.graduation.service.base.impl;


import com.ptit.graduation.exception.base.NotFoundException;
import com.ptit.graduation.repository.base.BaseMongoRepository;
import com.ptit.graduation.service.base.BaseService;

import java.util.List;


public class BaseServiceImpl<T> implements BaseService<T> {
  private final BaseMongoRepository<T> repository;

  public BaseServiceImpl(BaseMongoRepository<T> repository) {
    this.repository = repository;
  }

  @Override
  public T create(T t) {
    return repository.save(t);
  }

  @Override
  public T update(T t) {
    return repository.save(t);
  }

  @Override
  public void delete(String id) {
    repository.delete(get(id));
  }

  @Override
  public T get(String id) {
    return repository.findById(id).orElseThrow(NotFoundException::new);
  }

  @Override
  public List<T> list() {
    return repository.findAll();
  }
}
