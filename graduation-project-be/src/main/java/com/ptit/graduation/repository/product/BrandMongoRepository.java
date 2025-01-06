package com.ptit.graduation.repository.product;

import org.springframework.data.mongodb.repository.Query;

import com.ptit.graduation.entity.product.BrandMongo;
import com.ptit.graduation.repository.base.BaseMongoRepository;

public interface BrandMongoRepository extends BaseMongoRepository<BrandMongo> {

    @Query(value = "{'name': ?0}")
    BrandMongo findByName(String name);

    @Query(value = "{'name': ?0, 'type': ?1}")
    BrandMongo findByNameAndType(String name, String type);
    
}
