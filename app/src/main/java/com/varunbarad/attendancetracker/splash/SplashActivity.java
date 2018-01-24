package com.varunbarad.attendancetracker.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.varunbarad.attendancetracker.mainattendance.MainAttendanceActivity;

/**
 * Creator: Varun Barad
 * Date: 24-01-2018
 * Project: AttendanceTracker
 */
public class SplashActivity extends AppCompatActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MainAttendanceActivity.start(this);
    this.finish();
  }
}
