package com.ptit.graduation.repository.product;

import com.ptit.graduation.entity.product.LocationMongo;
import com.ptit.graduation.repository.base.BaseMongoRepository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationMongoRepository extends BaseMongoRepository<LocationMongo> {

    @Query(value = "{'name': ?0}")
    LocationMongo findByName(String name);

}
