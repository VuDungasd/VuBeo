package com.ptit.graduation.service.product.impl;

import com.ptit.graduation.service.product.AutoSuggestKeyService;
import com.ptit.graduation.service.product.ProductRedisService;
import com.ptit.graduation.utils.ConvertVietnameseToNormalText;
import com.ptit.graduation.utils.NormalTextSearch;
import com.ptit.graduation.utils.Stopword;
import com.ptit.graduation.utils.VietnameseAccentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoSuggestKeyServiceImpl implements AutoSuggestKeyService {
  @Autowired
  private ProductRedisService productRedisService;

  public Set<String> getAutoSuggest(String prefix) {
    log.info("AutoSuggestKeyServiceImpl.getAutoSuggest");
    try {
      prefix = NormalTextSearch.normalize(prefix);
      prefix = VietnameseAccentMapper.convertToAccented(prefix);
      prefix = Stopword.removeStopWords(prefix);

      ConvertVietnameseToNormalText convert = new ConvertVietnameseToNormalText();

      Set<String> suggestions = new HashSet<>();
      List<Object> keysRedis = productRedisService.getKeys();

      String[] prefixArr = prefix.split(" ");
      for (int i = 0; i < prefixArr.length; i++) {
        String text = String.join(" ", Arrays.asList(prefixArr).subList(i, prefixArr.length));
        List<String> keyList = new ArrayList<>();
        for (Object obj : keysRedis) {
          if (obj instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) obj;
            if (map.containsKey(text.substring(0, 1))) {
              try {
                keyList.addAll((List<String>) map.get(convert.toNonAccentVietnamese(text.substring(0, 1))));
              } catch (Exception e) {
                log.error(e.getMessage());
              }
            }
          }
        }
        
        int start = 0;
        while (start < text.length()) {
          String prefixTemp = text.substring(0, text.length() - start).trim();
          String prefixEnglishTemp = convert.slugify(prefixTemp);
          start++;

          for (String key : keyList) {
            String[] keyArr = key.split("&&");
            if (suggestions.size() > 7) return suggestions;
            if (keyArr[0].startsWith(prefixTemp) || keyArr[1].startsWith(prefixEnglishTemp)) {
              suggestions.add(keyArr[0]);
            }
          }
        }

      }
      return suggestions;
    } catch (Exception e) {
      log.error("AutoSuggestKeyServiceImpl.getAutoSuggest failed");
      return new HashSet<>();
    }

  }

}
