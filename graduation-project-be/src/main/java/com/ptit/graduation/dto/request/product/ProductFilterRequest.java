package com.ptit.graduation.dto.request.product;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductFilterRequest {
  private int type; // 1: Filter in Mongo, 2: Filter in ES
  private String keyword; // Từ khóa tìm kiếm
  private String brandId; // 
  private String categoryId;
  private List<String> locationIds;
  private Integer priceFrom;
  private Integer priceTo;
  private int sort; // 1: Price Asc, 2: Price Desc, khác: Review Desc
  private float review = 3.0f;
  private int page = 0;
  private int size = 20; // Số lượng sản phẩm trên 1 trang
}
