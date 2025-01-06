package com.ptit.graduation.dto.response.product;

import com.ptit.graduation.entity.product.LocationMongo;
import com.ptit.graduation.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class LocationListResponse {
  private int status;
  private String message;
  private List<LocationMongo> data;
  private String timestamp;
  private int total;

  public static LocationListResponse of(int status, String message, List<LocationMongo> data, int total) {
    return of(status, message, data, DateUtils.getCurrentDateString(), total);
  }

  public static LocationListResponse ofSuccess(String message, List<LocationMongo> data, int total) {
    return of(HttpStatus.OK.value(), message, data, DateUtils.getCurrentDateString(), total);
  }

  public static LocationListResponse ofSuccess(String message, int total) {
    return of(HttpStatus.OK.value(), message, null, DateUtils.getCurrentDateString(), total);
  }

}
