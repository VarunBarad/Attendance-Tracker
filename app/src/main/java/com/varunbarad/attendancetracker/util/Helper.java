package com.varunbarad.attendancetracker.util;

/**
 * Creator: Varun Barad
 * Date: 04-01-2018
 * Project: AttendanceTracker
 */
public final class Helper {
  public static int calculateNumberOfClassesToAttend(int attended, int skipped, double threshold) {
    double numerator = (((double) attended) * (threshold - 1.0d)) + (((double) skipped) * threshold);
    double denominator = 1.0d - threshold;
    
    return Double.valueOf(Math.ceil(numerator / denominator)).intValue();
  }
}
