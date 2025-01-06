package com.ptit.graduation.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NgramUtils {
  public static List<String> createNGrams(String text, int n) {
    List<String> ngramsList = new ArrayList<>();
    String[] words = text.trim().split("\\s+");
    for (String word : words) {
      if (word.length() >= n) {
        for (int i = 0; i <= word.length() - n; i++) {
          ngramsList.add(word.substring(i, i + n));
        }
      } else {
        ngramsList.add(word);
      }
    }
    return ngramsList;
  }
}
