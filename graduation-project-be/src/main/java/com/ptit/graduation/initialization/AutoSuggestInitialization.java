package com.ptit.graduation.initialization;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ptit.graduation.service.product.ProductRedisService;

import lombok.extern.slf4j.Slf4j;

import static com.ptit.graduation.constants.GraduationProjectConstants.CommonConstants.TIME_DAYS;

@Slf4j
@Component
public class AutoSuggestInitialization implements CommandLineRunner {

  @Autowired
  private ProductRedisService productRedisService;

  @Override
  public void run(String... args) throws Exception {
    try {
      log.info("AutoSuggestInitialization started");
      List<String> lines = this.readFile("Data_Autocomplete.txt");
      if (lines == null || lines.isEmpty()) {
        log.error("AutoSuggestInitialization failed");
        return;
      }

      if (productRedisService.checkAutoSuggestExist() == true) {
        productRedisService.clearAutoSuggest();
        productRedisService.setKeysAutoSuggest(lines, TIME_DAYS, TimeUnit.DAYS);
        log.info("AutoSuggest already exists");
        return;
      }
      // productRedisService.clearAutoSuggest();
      productRedisService.setKeysAutoSuggest(lines, TIME_DAYS, TimeUnit.DAYS);
      log.info("AutoSuggestInitialization finished");
    } catch (Exception e) {
      log.error("AutoSuggestInitialization failed");
    }

  }

  private List<String> readFile(String filePath) {
    try {
      return Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
    } catch (IOException e) {
      log.error("Error reading file {}", filePath);
      return null;
    }
  }

}
