package com.ptit.graduation.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GraduationProjectConstants {

  public static class CommonConstants {
    public static final String ENCODING_UTF_8 = "UTF-8";
    public static final String LANGUAGE = "Accept-Language";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final int FIRST_ELEMENT_OR_ARRAY = 0;
    public static final String BODY_WITH_NONE_ARGUMENT = "";
    public static final int FIRST_PAGE_NUMBER = 0;
    public static final String EXCHANGE_RATE = "exchange_rate";
    public static final double DEFAULT_EXCHANGE_RATE = 24800.00;
    public static final int NGRAM_COUNT = 3;
    public static final int FILTER_IN_MONGO = 1;
    public static final int FILTER_IN_ES = 2;
    public static final int TIME_DAYS = 30;
    public static final String PRODUCT_HASH_KEY = "Products";
    public static final String AUTO_SUGGEST_KEY = "AutoSuggestKey";
    public static final List<String> LOCATIONS_POPULAR = List.of("Hà Nội", "TP Hồ Chí Minh", "Đà Nẵng", "Hải Phòng", "Nam Định", "Thái Bình", "Quảng Ninh", "Nghệ An", "Ninh Bình", "Thanh Hóa", "Thừa Thiên Huế");
    public static final int PRODUCTS_IN_REDIS = 1000;

    private CommonConstants() {
    }
  }

  public static class AuditorConstant {
    public static final String ANONYMOUS = "anonymousUser";
    public static final String SYSTEM = "SYSTEM";

    private AuditorConstant() {
    }
  }

  public static class MessageCode {
    public static final String SUCCESS = "Thành công";
    public static final String FAILED = "com.lawnman.payment.server.constanst.MessageCode.FAILED";

    private MessageCode() {
    }
  }
}
