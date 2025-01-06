package com.ptit.graduation.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
  public static String getCurrentDateString() {
    return LocalDate.now().toString();
  }

  public static Long currentTimeMillis() {
    return System.currentTimeMillis();
  }

  public static long calculateDaysBetween(long startTimeMillis, long endTimeMillis) {
    long timeDifferenceMillis = endTimeMillis - startTimeMillis;
    return TimeUnit.MILLISECONDS.toDays(timeDifferenceMillis);
  }

  public static long getStartOfYesterday() {
    LocalDateTime startOfYesterday = LocalDate.now().minusDays(1).atStartOfDay();
    return toTimestamp(startOfYesterday);
  }

  public static long getEndOfYesterday() {
    LocalDateTime endOfYesterday = LocalDate.now().minusDays(1).atTime(23, 59, 59);
    return toTimestamp(endOfYesterday);
  }

  private static long toTimestamp(LocalDateTime dateTime) {
    ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
    return zonedDateTime.toInstant().toEpochMilli();
  }

}
