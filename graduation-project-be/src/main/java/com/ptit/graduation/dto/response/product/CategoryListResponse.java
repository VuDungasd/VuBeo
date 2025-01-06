package com.ptit.graduation.dto.response.product;

import com.ptit.graduation.entity.product.CategoryMongo;
import com.ptit.graduation.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class CategoryListResponse {
  private int status;
  private String message;
  private List<CategoryMongo> data;
  private String timestamp;
  private int total;

  public static CategoryListResponse of(int status, String message, List<CategoryMongo> data, int total) {
    return of(status, message, data, DateUtils.getCurrentDateString(), total);
  }

  public static CategoryListResponse ofSuccess(String message, List<CategoryMongo> data, int total) {
    return of(HttpStatus.OK.value(), message, data, DateUtils.getCurrentDateString(), total);
  }

  public static CategoryListResponse ofSuccess(String message, int total) {
    return of(HttpStatus.OK.value(), message, null, DateUtils.getCurrentDateString(), total);
  }

}
