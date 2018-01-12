package com.varunbarad.attendancetracker.mainattendance;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.varunbarad.attendancetracker.R;
import com.varunbarad.attendancetracker.databinding.ActivityMainAttendanceBinding;

public class MainAttendanceActivity extends AppCompatActivity {
  private ActivityMainAttendanceBinding dataBinding;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_attendance);
  
    this.setSupportActionBar(this.dataBinding.toolbar);
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    this
        .getMenuInflater()
        .inflate(R.menu.main_attendance, menu);
    
    return true;
  }
}
