package com.ptit.graduation.utils;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VietnameseAccentMapper {
  // Bảng ánh xạ không dấu -> có dấu
  private static final Map<String, String> ACCENT_MAP = new HashMap<>();

  static {
    // Nguyên âm đơn giản
    ACCENT_MAP.put("as", "á");
    ACCENT_MAP.put("af", "à");
    ACCENT_MAP.put("ar", "ả");
    ACCENT_MAP.put("aj", "ạ");
    ACCENT_MAP.put("ax", "ã");
    ACCENT_MAP.put("ays", "áy");
    ACCENT_MAP.put("ayf", "ày");
    ACCENT_MAP.put("ayr", "ảy");
    ACCENT_MAP.put("ayj", "ạy");
    ACCENT_MAP.put("ayx", "ãy");
    ACCENT_MAP.put("aa", "â");
    ACCENT_MAP.put("aas", "ấ");
    ACCENT_MAP.put("aaf", "ầ");
    ACCENT_MAP.put("aar", "ẩ");
    ACCENT_MAP.put("aaj", "ậ");
    ACCENT_MAP.put("aax", "ẫ");
    ACCENT_MAP.put("aw", "ă");
    ACCENT_MAP.put("aws", "ắ");
    ACCENT_MAP.put("awf", "ằ");
    ACCENT_MAP.put("awr", "ẳ");
    ACCENT_MAP.put("awj", "ặ");
    ACCENT_MAP.put("awx", "ẵ");
    ACCENT_MAP.put("aos", "áo");
    ACCENT_MAP.put("aof", "ào");
    ACCENT_MAP.put("aoj", "ạo");
    ACCENT_MAP.put("aox", "ão");
    ACCENT_MAP.put("ais", "ái");
    ACCENT_MAP.put("aif", "ài");
    ACCENT_MAP.put("aij", "ại");
    ACCENT_MAP.put("aix", "ãi");
    ACCENT_MAP.put("aus", "áu");
    ACCENT_MAP.put("auf", "àu");
    ACCENT_MAP.put("auj", "ạu");
    ACCENT_MAP.put("aux", "ãu");

    // Nguyên âm "e" và "ê"
    ACCENT_MAP.put("es", "é");
    ACCENT_MAP.put("ef", "è");
    ACCENT_MAP.put("er", "ẻ");
    ACCENT_MAP.put("ej", "ẹ");
    ACCENT_MAP.put("ex", "ẽ");
    ACCENT_MAP.put("ee", "ê");
    ACCENT_MAP.put("ees", "ế");
    ACCENT_MAP.put("eef", "ề");
    ACCENT_MAP.put("eer", "ể");
    ACCENT_MAP.put("eej", "ệ");
    ACCENT_MAP.put("eex", "ễ");
    ACCENT_MAP.put("eos", "éo");
    ACCENT_MAP.put("eof", "èo");
    ACCENT_MAP.put("eor", "ẻo");
    ACCENT_MAP.put("eoj", "ẹo");
    ACCENT_MAP.put("eox", "ẽo");
    ACCENT_MAP.put("eeo", "êo");
    ACCENT_MAP.put("eeos", "ếo");
    ACCENT_MAP.put("eeof", "ềo");
    ACCENT_MAP.put("eeor", "ểo");
    ACCENT_MAP.put("eeoj", "ệo");
    ACCENT_MAP.put("eeox", "ễo");

    // Nguyên âm "i"
    ACCENT_MAP.put("is", "í");
    ACCENT_MAP.put("if", "ì");
    ACCENT_MAP.put("ir", "ỉ");
    ACCENT_MAP.put("ij", "ị");
    ACCENT_MAP.put("ix", "ĩ");
    ACCENT_MAP.put("ios", "ío");
    ACCENT_MAP.put("ias", "ía");
    ACCENT_MAP.put("ius", "íu");
    ACCENT_MAP.put("iaf", "ìa");
    ACCENT_MAP.put("iof", "ìo");
    ACCENT_MAP.put("iuf", "ìu");
    ACCENT_MAP.put("iar", "ỉa");
    ACCENT_MAP.put("ior", "ỉo");
    ACCENT_MAP.put("iur", "ỉu");
    ACCENT_MAP.put("iaj", "ịa");
    ACCENT_MAP.put("ioj", "ịo");
    ACCENT_MAP.put("iuj", "ịu");
    ACCENT_MAP.put("iax", "ĩa");
    ACCENT_MAP.put("iox", "ĩo");
    ACCENT_MAP.put("iux", "ĩu");

    // Nguyên âm "o", "ô", "ơ"
    ACCENT_MAP.put("os", "ó");
    ACCENT_MAP.put("of", "ò");
    ACCENT_MAP.put("or", "ỏ");
    ACCENT_MAP.put("oj", "ọ");
    ACCENT_MAP.put("ox", "õ");
    ACCENT_MAP.put("oas", "óa");
    ACCENT_MAP.put("oes", "óe");
    ACCENT_MAP.put("ois", "ói");
    ACCENT_MAP.put("oaf", "òa");
    ACCENT_MAP.put("oef", "òe");
    ACCENT_MAP.put("oif", "òi");
    ACCENT_MAP.put("oar", "ỏa");
    ACCENT_MAP.put("oer", "ỏe");
    ACCENT_MAP.put("oir", "ỏi");
    ACCENT_MAP.put("oaj", "ọa");
    ACCENT_MAP.put("oej", "ọe");
    ACCENT_MAP.put("oij", "ọi");
    ACCENT_MAP.put("oax", "õa");
    ACCENT_MAP.put("oex", "õe");
    ACCENT_MAP.put("oix", "õi");

    ACCENT_MAP.put("oo", "ô");
    ACCENT_MAP.put("oos", "ố");
    ACCENT_MAP.put("oof", "ồ");
    ACCENT_MAP.put("oor", "ổ");
    ACCENT_MAP.put("ooj", "ộ");
    ACCENT_MAP.put("oox", "ỗ");
    ACCENT_MAP.put("ow", "ơ");
    ACCENT_MAP.put("ows", "ớ");
    ACCENT_MAP.put("owf", "ờ");
    ACCENT_MAP.put("owr", "ở");
    ACCENT_MAP.put("owj", "ợ");
    ACCENT_MAP.put("owx", "ỡ");

    // Nguyên âm "u", "ư"
    ACCENT_MAP.put("us", "ú");
    ACCENT_MAP.put("uf", "ù");
    ACCENT_MAP.put("ur", "ủ");
    ACCENT_MAP.put("uj", "ụ");
    ACCENT_MAP.put("ux", "ũ");
    ACCENT_MAP.put("uas", "úa");
    ACCENT_MAP.put("uis", "úi");
    ACCENT_MAP.put("uaf", "ùa");
    ACCENT_MAP.put("uif", "ùi");
    ACCENT_MAP.put("uar", "ủa");
    ACCENT_MAP.put("uir", "ủi");
    ACCENT_MAP.put("uaj", "ụa");
    ACCENT_MAP.put("uij", "ụi");
    ACCENT_MAP.put("uax", "ũa");
    ACCENT_MAP.put("uix", "ũi");
    ACCENT_MAP.put("uw", "ư");
    ACCENT_MAP.put("uow", "ươ");
    ACCENT_MAP.put("uws", "ứ");
    ACCENT_MAP.put("uwf", "ừ");
    ACCENT_MAP.put("uwr", "ử");
    ACCENT_MAP.put("uwj", "ự");
    ACCENT_MAP.put("uwx", "ữ");
    ACCENT_MAP.put("uows", "ướ");
    ACCENT_MAP.put("uowf", "ườ");
    ACCENT_MAP.put("uowr", "ưở");
    ACCENT_MAP.put("uowj", "ượ");
    ACCENT_MAP.put("uowx", "ưỡ");

    // Nguyên âm "y"
    ACCENT_MAP.put("ys", "ý");
    ACCENT_MAP.put("yf", "ỳ");
    ACCENT_MAP.put("yr", "ỷ");
    ACCENT_MAP.put("yj", "ỵ");
    ACCENT_MAP.put("yx", "ỹ");
  }

  public static String convertToAccented(String text) {
    
    StringBuilder result = new StringBuilder();
    String[] words = text.split("\\s+");
    for (String word : words) {
        result.append(processWord(word)).append(" ");
    }
    log.info("Convert Vietnamese text to accented text: {}", result.toString().trim());
    return result.toString().trim();
}

private static String processWord(String word) {
    ConvertVietnameseToNormalText convertVietnameseToNormalText = new ConvertVietnameseToNormalText();
    String wordSlug = convertVietnameseToNormalText.toNonAccentVietnamese(word);
    String vowel = "aeiouy";
    int vowelIndex = -1;
    for (int i = 0; i < wordSlug.length(); i++) {
        if (vowel.contains(wordSlug.charAt(i) + "")) {
            vowelIndex = i;
        }
    }
    int start = word.length()-1;
    while(start>vowelIndex && start>word.length()-3){
        if(word.charAt(word.length()-1)=='s' || word.charAt(word.length()-1)=='f' || word.charAt(word.length()-1)=='r' || word.charAt(word.length()-1)=='j' || word.charAt(word.length()-1)=='x' || word.charAt(word.length()-1)=='w'){
            word = word.substring(0, vowelIndex+1) + word.charAt(word.length()-1) + word.substring(vowelIndex+1, word.length()-1);
        }
        start--;
    }

    StringBuilder result = new StringBuilder();
    int i = 0;

    while (i < word.length()) {
        boolean matched = false;

        for (int len = 4; len > 1; len--) {
            if (word.length() >= i + len) {
                String substring = word.substring(i, i + len);
                if (ACCENT_MAP.containsKey(substring)) {
                    result.append(ACCENT_MAP.get(substring));
                    i += len;
                    matched = true;
                    break;
                }
            }
        }

        if (!matched) {
            result.append(word.charAt(i));
            i++;
        }
    }

    return result.toString();
}

}
