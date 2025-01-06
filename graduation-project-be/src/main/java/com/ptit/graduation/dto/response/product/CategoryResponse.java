package com.ptit.graduation.dto.response.product;

import com.ptit.graduation.entity.product.CategoryMongo;
import com.ptit.graduation.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class CategoryResponse {
  private int status;
  private String message;
  private CategoryMongo data;
  private String timestamp;

  public static CategoryResponse of(int status, String message, CategoryMongo data) {
    return of(status, message, data, DateUtils.getCurrentDateString());
  }

  public static CategoryResponse ofSuccess(String message, CategoryMongo data) {
    return of(HttpStatus.OK.value(), message, data, DateUtils.getCurrentDateString());
  }

  public static CategoryResponse ofSuccess(String message) {
    return of(HttpStatus.OK.value(), message, null, DateUtils.getCurrentDateString());
  }

  public static CategoryResponse ofCreated(String message, CategoryMongo data) {
    return of(HttpStatus.CREATED.value(), message, data, DateUtils.getCurrentDateString());
  }

}
