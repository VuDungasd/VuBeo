package com.ptit.graduation.dto.response.product;

import com.ptit.graduation.entity.product.ProductMongo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ProductListResponse {
  private List<ProductResponse> products;
  private long total;
}
