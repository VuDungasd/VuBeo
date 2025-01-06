package com.ptit.graduation.controller.product;

import com.ptit.graduation.dto.response.ResponseGeneral;
import com.ptit.graduation.entity.product.BrandMongo;
import com.ptit.graduation.service.product.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ptit.graduation.constants.GraduationProjectConstants.MessageCode.SUCCESS;

@Slf4j
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {
  private final BrandService brandService;

  @GetMapping
  public ResponseGeneral<List<BrandMongo>> list() {
    log.info("(list) start");

    List<BrandMongo> brands = brandService.list();

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          brands
    );
  }
}
