package com.varunbarad.attendancetracker.subject.subjectdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.varunbarad.attendancetracker.R;

public class SubjectDetailsActivity extends AppCompatActivity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_subject_details);
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    this
        .getMenuInflater()
        .inflate(R.menu.subject_details, menu);
    
    return true;
  }
}
