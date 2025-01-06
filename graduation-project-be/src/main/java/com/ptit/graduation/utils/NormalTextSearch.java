package com.ptit.graduation.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NormalTextSearch {

  public static String normalize(String input) {
    // xóa dấu: , . ! ? " ' @ # $ % ^ & * ( ) - + = { } [ ] | \ / : ; < > ~ `
    input = input.replaceAll("[,\\.\\!\\?\"'@#$%^&*\\(\\)\\-\\+=\\{\\}\\[\\]\\|\\\\/:;<>&~`]", "")
        .replaceAll("\\s+", " ")
        .trim()
        .toLowerCase();
    log.info("Normalize text: {}", input);
    return input;
  }

}
