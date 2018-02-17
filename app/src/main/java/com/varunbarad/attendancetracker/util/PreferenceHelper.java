package com.varunbarad.attendancetracker.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.varunbarad.attendancetracker.R;

/**
 * Creator: Varun Barad
 * Date: 04-01-2018
 * Project: AttendanceTracker
 */
public class PreferenceHelper {
  public static final int MAX_THRESHOLD = 100;
  
  public static int getDefaultThreshold(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
  
    return preferences.getInt(
        context.getString(R.string.PREFS_KEY_THRESHOLD),
        context.getResources().getInteger(R.integer.default_threshold)
    );
  }
  
  public static void setDefaultThreshold(Context context, int threshold) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    
    editor.putInt(
        context.getString(R.string.PREFS_KEY_THRESHOLD),
        threshold
    ).apply();
  }
  
  public static boolean countCancelledAsSkipped(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
  
    return preferences.getBoolean(
        context.getString(R.string.PREFS_KEY_COUNT_CANCELLED),
        context.getResources().getBoolean(R.bool.default_countCancelled)
    );
  }
  
  public static void setCountCancelledAsSkipped(Context context, boolean countCancelledAsSkipped) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    
    editor.putBoolean(
        context.getString(R.string.PREFS_KEY_COUNT_CANCELLED),
        countCancelledAsSkipped
    ).apply();
  }
}
