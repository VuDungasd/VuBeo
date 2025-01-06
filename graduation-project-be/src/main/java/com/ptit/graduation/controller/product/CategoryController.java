package com.ptit.graduation.controller.product;

import com.ptit.graduation.dto.response.ResponseGeneral;
import com.ptit.graduation.entity.product.CategoryMongo;
import com.ptit.graduation.service.product.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ptit.graduation.constants.GraduationProjectConstants.MessageCode.SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping("list")
  public ResponseGeneral<List<CategoryMongo>> list() {
    log.info("(list) start");

    List<CategoryMongo> categories = categoryService.list();

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          categories
    );
  }
}
