package com.ptit.graduation.repository.product;

import com.ptit.graduation.entity.product.ProductES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductESRepository extends ElasticsearchRepository<ProductES, String> {
}
