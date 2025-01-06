package com.ptit.graduation.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ProductPageResponse {
  private List<ProductResponse> products;
  private long total;
}
