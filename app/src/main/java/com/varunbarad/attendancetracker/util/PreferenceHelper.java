package com.varunbarad.attendancetracker.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.varunbarad.attendancetracker.R;

/**
 * Creator: Varun Barad
 * Date: 04-01-2018
 * Project: AttendanceTracker
 */
public class PreferenceHelper {
  public static boolean countCancelledAsSkipped(Context context) {
    SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.PREFS_NAME), Context.MODE_PRIVATE);
    
    return preferences.getBoolean(context.getString(R.string.PREFS_KEY_COUNT_CANCELLED), false);
  }
}
