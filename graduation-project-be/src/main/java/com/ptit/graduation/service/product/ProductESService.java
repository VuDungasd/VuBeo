package com.ptit.graduation.service.product;

import com.ptit.graduation.dto.request.product.ProductFilterRequest;
import com.ptit.graduation.dto.response.product.ProductPageResponse;
import com.ptit.graduation.entity.product.ProductES;

import java.util.List;

public interface ProductESService {
  void bulkInsert(List<ProductES> productESList);

  ProductPageResponse filter(ProductFilterRequest request);
}
