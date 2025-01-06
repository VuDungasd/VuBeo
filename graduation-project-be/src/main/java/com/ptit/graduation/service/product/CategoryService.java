package com.ptit.graduation.service.product;


import com.ptit.graduation.entity.product.CategoryMongo;
import com.ptit.graduation.service.base.BaseService;

import java.util.List;

public interface CategoryService extends BaseService<CategoryMongo> {

  CategoryMongo findByName(String name);

  CategoryMongo findByNameAndType(String name, String type);

  void saveAll(List<CategoryMongo> categories);

}
