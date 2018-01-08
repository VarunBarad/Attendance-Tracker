package com.varunbarad.attendancetracker.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.varunbarad.attendancetracker.R;

/**
 * Creator: Varun Barad
 * Date: 06-01-2018
 * Project: AttendanceTracker
 */
public class SettingsFragment extends PreferenceFragmentCompat {
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    this.addPreferencesFromResource(R.xml.prefs_attendance_tracker);
  }
}
