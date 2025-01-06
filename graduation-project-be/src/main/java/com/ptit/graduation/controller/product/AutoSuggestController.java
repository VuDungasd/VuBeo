package com.ptit.graduation.controller.product;

import com.ptit.graduation.dto.response.ResponseGeneral;
import com.ptit.graduation.service.product.AutoSuggestKeyService;
import com.ptit.graduation.service.product.ProductRedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.ptit.graduation.constants.GraduationProjectConstants.MessageCode.SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auto-suggest")
public class AutoSuggestController {
  private final AutoSuggestKeyService autoSuggestKeyService;

  private final ProductRedisService productRedisService;

  @GetMapping("")
  public ResponseGeneral<Set<String>> getSuggestions(@RequestParam(value = "prefix", defaultValue = "") String prefix) {
    log.info("(getSuggestions) prefix: {}", prefix);

    Set<String> suggestions = autoSuggestKeyService.getAutoSuggest(prefix);

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          suggestions
    );
  }

  @GetMapping("/clear")
  public ResponseGeneral<String> clearAutoSuggest() {
    log.info("(clearAutoSuggest)");

    productRedisService.clearAutoSuggest();

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          "Cleared"
    );
  }

  @GetMapping("/clear-all-keys-in-redis")
  public ResponseGeneral<String> clearAllKeysInRedis() {
    log.info("(clearAllKeysInRedis)");

    productRedisService.clearAllKeysInRedis();

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          "Cleared"
    );
  }
}
