package com.varunbarad.attendancetracker.widget.subjectselect;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.varunbarad.attendancetracker.R;
import com.varunbarad.attendancetracker.data.DatabaseHelper;
import com.varunbarad.attendancetracker.data.model.Attendance;
import com.varunbarad.attendancetracker.data.model.Subject;
import com.varunbarad.attendancetracker.databinding.ActivitySubjectSelectBinding;
import com.varunbarad.attendancetracker.util.Helper;
import com.varunbarad.attendancetracker.util.eventlistener.ListItemClickListener;

import java.util.ArrayList;
import java.util.Date;

public class SubjectSelectActivity extends AppCompatActivity implements ListItemClickListener {
  private static final String KEY_ATTENDANCE = "attendance";
  private ActivitySubjectSelectBinding dataBinding;
  private SubjectsAdapter subjectsAdapter;
  private int selectedAttendanceStatus;

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
    this.dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_subject_select);
  
    this.selectedAttendanceStatus = this.getAttendanceStatusSelectedInWidget();
  
    this.setSupportActionBar(this.dataBinding.toolbar);
    this.getSupportActionBar()
        .setDisplayHomeAsUpEnabled(true);
  
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    this.dataBinding
        .recyclerViewSubjects
        .setLayoutManager(layoutManager);
  }
  
  @Override
  protected void onStart() {
    super.onStart();
    this.showProgress();
    this.showSubjects(DatabaseHelper.getCurrentSubjects(this));
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      this.onBackPressed();
      return true;
    } else {
      return super.onOptionsItemSelected(item);
    }
  }
  
  private int getAttendanceStatusSelectedInWidget() {
    return this.getIntent().getIntExtra(KEY_ATTENDANCE, -1);
  }
  
  private void showProgress() {
    this.dataBinding
        .containerProgress
        .setVisibility(View.VISIBLE);
    
    this.dataBinding
        .containerContent
        .setVisibility(View.GONE);
    
    this.dataBinding
        .containerPlaceholder
        .setVisibility(View.GONE);
  }
  
  private void showSubjects(ArrayList<Subject> subjects) {
    if ((subjects == null) || (subjects.size() < 1)) {
      this.showPlaceholder();
    } else {
      if (this.subjectsAdapter != null) {
        this.subjectsAdapter.setSubjects(subjects);
      } else {
        this.subjectsAdapter = new SubjectsAdapter(subjects, this);
        this.dataBinding
            .recyclerViewSubjects
            .setAdapter(this.subjectsAdapter);
      }
      
      this.dataBinding
          .containerProgress
          .setVisibility(View.GONE);
      
      this.dataBinding
          .containerContent
          .setVisibility(View.VISIBLE);
      
      this.dataBinding
          .containerPlaceholder
          .setVisibility(View.GONE);
    }
  }
  
  private void showPlaceholder() {
    this.dataBinding
        .containerProgress
        .setVisibility(View.GONE);
    
    this.dataBinding
        .containerContent
        .setVisibility(View.GONE);
    
    this.dataBinding
        .containerPlaceholder
        .setVisibility(View.VISIBLE);
  }
  
  @Override
  public void onListItemClicked(int position, String data) {
    Attendance attendance = new Attendance(
        this.subjectsAdapter.getSubjects().get(position).getId(),
        Helper.stripTime(new Date()),
        this.selectedAttendanceStatus
    );
    DatabaseHelper.addAttendance(this, attendance);
    
    this.finish();
  }
}
