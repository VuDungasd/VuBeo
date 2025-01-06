package com.ptit.graduation.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Stopword {
  private static String[] STOP_WORDS = {"của", "là", "và", "có", "được", "trong", "để", "với", "không", "một", "nhiều", "các", "đã", "đang", "thì", "về", "như", "nếu", "sẽ", "từ", "cũng", "hay", "cho", "nên", "đến", "rằng", "sau", "làm", "đi", "vì", "lại", "nào", "cũng", "đây", "đó", "này", "khi", "mà", "vì", "thì", "thế", "nếu" };

  public static String removeStopWords(String text) {
    for (String stopWord : STOP_WORDS) {
      for (String word : text.split(" ")) {
        if (word.equals(stopWord)) {
          text = text.replace(word, "");
        }
      }
    }
    log.info("Remove stop words from text: {}", text);
    return text.replaceAll("\\s+", " ").trim();
  }
  public static String[] getStopWords() {
    log.info("Get stop words");
    return STOP_WORDS;
  }

}
