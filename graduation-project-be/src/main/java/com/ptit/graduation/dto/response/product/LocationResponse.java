package com.ptit.graduation.dto.response.product;

import com.ptit.graduation.entity.product.LocationMongo;
import com.ptit.graduation.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class LocationResponse {
  private int status;
  private String message;
  private LocationMongo data;
  private String timestamp;

  public static LocationResponse of(int status, String message, LocationMongo data) {
    return of(status, message, data, DateUtils.getCurrentDateString());
  }

  public static LocationResponse ofSuccess(String message, LocationMongo data) {
    return of(HttpStatus.OK.value(), message, data, DateUtils.getCurrentDateString());
  }

  public static LocationResponse ofSuccess(String message) {
    return of(HttpStatus.OK.value(), message, null, DateUtils.getCurrentDateString());
  }

  public static LocationResponse ofCreated(String message, LocationMongo data) {
    return of(HttpStatus.CREATED.value(), message, data, DateUtils.getCurrentDateString());
  }

}
