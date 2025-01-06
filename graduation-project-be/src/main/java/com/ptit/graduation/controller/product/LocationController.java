package com.ptit.graduation.controller.product;

import com.ptit.graduation.dto.response.ResponseGeneral;
import com.ptit.graduation.entity.product.LocationMongo;
import com.ptit.graduation.service.product.LocationService;
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
@RequestMapping("api/v1/locations")
public class LocationController {
  private final LocationService locationService;

  @GetMapping("list")
  public ResponseGeneral<List<LocationMongo>> list() {
    log.info("(list) start");

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          locationService.list()
    );
  }
}
