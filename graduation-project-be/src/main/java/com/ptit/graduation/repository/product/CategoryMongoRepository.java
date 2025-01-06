package com.ptit.graduation.repository.product;

import org.springframework.data.mongodb.repository.Query;

import com.ptit.graduation.entity.product.CategoryMongo;
import com.ptit.graduation.repository.base.BaseMongoRepository;

public interface CategoryMongoRepository extends BaseMongoRepository<CategoryMongo> {
    
    @Query(value = "{'name': ?0}")
    CategoryMongo findByName(String name);

    @Query(value = "{'name': ?0, 'type': ?1}")
    CategoryMongo findByNameAndType(String name, String type);

}
