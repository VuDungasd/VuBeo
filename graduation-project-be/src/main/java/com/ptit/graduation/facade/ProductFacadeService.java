package com.ptit.graduation.facade;

import com.ptit.graduation.dto.request.product.ProductFilterRequest;
import com.ptit.graduation.dto.response.product.ProductPageResponse;

public interface ProductFacadeService {
  ProductPageResponse filter(ProductFilterRequest request);
}
