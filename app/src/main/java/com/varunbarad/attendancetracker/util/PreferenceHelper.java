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
  public static boolean countCancelledAsSkipped(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    
    return preferences.getBoolean(context.getString(R.string.PREFS_KEY_COUNT_CANCELLED), false);
  }
}
