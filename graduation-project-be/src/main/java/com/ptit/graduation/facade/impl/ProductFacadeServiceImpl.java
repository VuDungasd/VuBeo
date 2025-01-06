package com.ptit.graduation.facade.impl;

import com.ptit.graduation.dto.request.product.ProductFilterRequest;
import com.ptit.graduation.dto.response.product.ProductPageResponse;
import com.ptit.graduation.facade.ProductFacadeService;
import com.ptit.graduation.service.product.ProductESService;
import com.ptit.graduation.service.product.ProductMongoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.ptit.graduation.constants.GraduationProjectConstants.CommonConstants.FILTER_IN_MONGO;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductFacadeServiceImpl implements ProductFacadeService {
  private final ProductMongoService productMongoService;
  private final ProductESService productESService;

  @Override
  public ProductPageResponse filter(ProductFilterRequest request) {
    log.info("(filter) request: {}", request);

    if(request.getType() == FILTER_IN_MONGO){
      return productMongoService.filter(request);
    }

    return productESService.filter(request);
  }
}
