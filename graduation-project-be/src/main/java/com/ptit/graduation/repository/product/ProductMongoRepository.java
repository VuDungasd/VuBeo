package com.ptit.graduation.repository.product;

import com.ptit.graduation.entity.product.ProductMongo;
import com.ptit.graduation.repository.base.BaseMongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMongoRepository extends BaseMongoRepository<ProductMongo> {


  @Aggregation(pipeline = {
      "{ $skip: ?0 }",
      "{ $limit: ?1 }",
  })
  List<ProductMongo> filter(int skip, int limit);

  @Aggregation(pipeline = {
      "{ $match: { review: { $gte: 3 } } }",
      "{ $sort: { sold_quantity: -1, review: -1, updated_at: -1 } }",
      "{ $skip: ?0 }",
      "{ $limit: ?1 }",
  })
  List<ProductMongo> getAll(int skip, int limit);

  @Aggregation(pipeline = {
        "{ $match: { category_id: ?0 } }",
        "{ $skip: ?1 }",
        "{ $limit: ?2 }",
  })
  List<ProductMongo> findByCategoryId(String categoryId, int skip, int limit);

  @Aggregation(pipeline = {
        "{ $match: { review: { $gte: 3 } } }",
        "{ $sort: { sold_quantity: -1, review: -1, updated_at: -1 } }",
        "{ $skip: ?0 }",
        "{ $limit: ?1 }",
  })
  List<ProductMongo> findTop20ByOrderByCreationDateDesc(int skip, int limit);

}
