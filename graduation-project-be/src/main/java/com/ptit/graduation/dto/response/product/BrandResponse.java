package com.ptit.graduation.dto.response.product;

import com.ptit.graduation.entity.product.BrandMongo;
import com.ptit.graduation.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class BrandResponse {
  private int status;
  private String message;
  private BrandMongo data;
  private String timestamp;

  public static BrandResponse of(int status, String message, BrandMongo data) {
    return of(status, message, data, DateUtils.getCurrentDateString());
  }

  public static BrandResponse ofSuccess(String message, BrandMongo data) {
    return of(HttpStatus.OK.value(), message, data, DateUtils.getCurrentDateString());
  }

  public static BrandResponse ofSuccess(String message) {
    return of(HttpStatus.OK.value(), message, null, DateUtils.getCurrentDateString());
  }

  public static BrandResponse ofCreated(String message, BrandMongo data) {
    return of(HttpStatus.CREATED.value(), message, data, DateUtils.getCurrentDateString());
  }

}
