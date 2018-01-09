package com.varunbarad.attendancetracker.widget.subjectselect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.varunbarad.attendancetracker.R;

public class SubjectSelectActivity extends AppCompatActivity {
  private static final String KEY_ATTENDANCE = "attendance";
  
  public static void start(Context context, int attendance) {
    Intent starter = getStarterIntent(context, attendance);
    context.startActivity(starter);
  }
  
  public static Intent getStarterIntent(Context context, int attendance) {
    Intent starter = new Intent(context, SubjectSelectActivity.class);
    starter.putExtra(KEY_ATTENDANCE, attendance);
    return starter;
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_subject_select);
  }
}
