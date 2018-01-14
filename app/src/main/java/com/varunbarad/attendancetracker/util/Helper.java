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
  
  public static int calculateNumberOfClassesToAttend(int attended, int skipped, int threshold) {
    int numerator = (attended * (threshold - 100)) + (skipped * threshold);
    int denominator = 100 - threshold;
    
    return (numerator / denominator);
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
  
  public static Date stripTime(Date date) {
    return new Date(date.getYear(), date.getMonth(), date.getDate(), 0, 0, 0);
  }
  
  public static boolean isDateInFuture(Date d1, Date d2) {
    return d1.getTime() > d2.getTime();
  }
}
