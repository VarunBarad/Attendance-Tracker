package com.varunbarad.attendancetracker.mainattendance;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.varunbarad.attendancetracker.BuildConfig;
import com.varunbarad.attendancetracker.R;
import com.varunbarad.attendancetracker.about.DeveloperActivity;
import com.varunbarad.attendancetracker.data.DatabaseHelper;
import com.varunbarad.attendancetracker.data.model.Attendance;
import com.varunbarad.attendancetracker.data.model.Subject;
import com.varunbarad.attendancetracker.databinding.ActivityMainAttendanceBinding;
import com.varunbarad.attendancetracker.settings.SettingsActivity;
import com.varunbarad.attendancetracker.subject.listsubjects.SubjectsListActivity;
import com.varunbarad.attendancetracker.util.Helper;
import com.varunbarad.attendancetracker.util.eventlistener.ListItemClickListener;

import java.util.ArrayList;
import java.util.Date;

public class MainAttendanceActivity extends AppCompatActivity implements ListItemClickListener {
  private ActivityMainAttendanceBinding dataBinding;
  private AttendanceSubjectAdapter subjectsAdapter;
  
  private FirebaseAnalytics analytics;
  
  public static void start(Context context) {
    Intent starter = new Intent(context, MainAttendanceActivity.class);
    context.startActivity(starter);
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_attendance);
  
    this.setSupportActionBar(this.dataBinding.toolbar);
  
    int columnCount = this.getResources().getInteger(R.integer.columns_mainAttendanceList);
    GridLayoutManager layoutManager =
        new GridLayoutManager(this, columnCount, GridLayoutManager.VERTICAL, false);
    this.dataBinding
        .recyclerViewAttendance
        .setLayoutManager(layoutManager);
  
    this.analytics = FirebaseAnalytics.getInstance(this);
  
    this.loadBannerAd();
  }
  
  @Override
  protected void onStart() {
    super.onStart();
    this.showProgress();
    this.showSubjects(DatabaseHelper.getCurrentSubjects(this));
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    this
        .getMenuInflater()
        .inflate(R.menu.main_attendance, menu);
    
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_subjects:
        SubjectsListActivity.start(this);
        break;
      case R.id.action_settings:
        SettingsActivity.start(this);
        break;
      case R.id.action_developer:
        DeveloperActivity.start(this);
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    
    return true;
  }
  
  private void showProgress() {
    this.dataBinding
        .contentProgress
        .setVisibility(View.VISIBLE);
    
    this.dataBinding
        .recyclerViewAttendance
        .setVisibility(View.GONE);
    
    this.dataBinding
        .contentPlaceholder
        .setVisibility(View.GONE);
  }
  
  private void showSubjects(ArrayList<Subject> subjects) {
    if ((subjects == null) || (subjects.size() < 1)) {
      this.showPlaceholder();
    } else {
      if (this.subjectsAdapter != null) {
        this.subjectsAdapter.setSubjects(subjects);
      } else {
        this.subjectsAdapter = new AttendanceSubjectAdapter(subjects, this);
        this.dataBinding
            .recyclerViewAttendance
            .setAdapter(this.subjectsAdapter);
      }
      
      this.dataBinding
          .contentProgress
          .setVisibility(View.GONE);
      
      this.dataBinding
          .recyclerViewAttendance
          .setVisibility(View.VISIBLE);
      
      this.dataBinding
          .contentPlaceholder
          .setVisibility(View.GONE);
    }
  }
  
  private void showPlaceholder() {
    this.dataBinding
        .contentProgress
        .setVisibility(View.GONE);
    
    this.dataBinding
        .recyclerViewAttendance
        .setVisibility(View.GONE);
    
    this.dataBinding
        .contentPlaceholder
        .setVisibility(View.VISIBLE);
  }
  
  @Override
  public void onListItemClicked(int position, String data) {
    int attendanceStatus = Integer.parseInt(data);
    Subject subject = this.subjectsAdapter.getSubjects().get(position);
  
    Attendance attendance = new Attendance(subject.getId(), Helper.stripTime(new Date()), attendanceStatus);
    subject.addAttendance(attendance);
    
    this.subjectsAdapter.updateSubject(subject);
  
    this.logFirebaseEvent();
    
    DatabaseHelper.addAttendance(this, attendance);
  }
  
  private void logFirebaseEvent() {
    Bundle bundle = new Bundle();
    bundle.putString(FirebaseAnalytics.Param.LOCATION, "application");
    
    this.analytics
        .logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
  }
  
  private void loadBannerAd() {
    MobileAds.initialize(this, BuildConfig.AdMobAppId);
    
    AdRequest adRequest =
        new AdRequest.Builder()
            .build();
    
    this.dataBinding
        .adViewBanner
        .loadAd(adRequest);
  }
}
