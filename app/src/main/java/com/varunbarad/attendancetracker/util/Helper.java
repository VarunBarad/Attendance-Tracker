package com.varunbarad.attendancetracker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Creator: Varun Barad
 * Date: 04-01-2018
 * Project: AttendanceTracker
 */
public final class Helper {
  public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
  
  public static int calculateNumberOfClassesToAttend(int attended, int skipped, double threshold) {
    double numerator = (((double) attended) * (threshold - 1.0d)) + (((double) skipped) * threshold);
    double denominator = 1.0d - threshold;
    
    return Double.valueOf(Math.ceil(numerator / denominator)).intValue();
  }
  
  public static String serializeDate(Date date) {
    return dateFormat.format(date);
  }
  
  public static Date deserializeDate(String date) {
    try {
      return dateFormat.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
      return new Date();
    }
  }
  
  public static boolean isSameDay(Date d1, Date d2) {
    assert d1 != null;
    assert d2 != null;
    
    return (d1.getYear() == d2.getYear()) && (d1.getMonth() == d2.getMonth()) && (d1.getDate() == d2.getDate());
  }
}
