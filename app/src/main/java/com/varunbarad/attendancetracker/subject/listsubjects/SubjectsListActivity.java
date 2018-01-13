package com.varunbarad.attendancetracker.subject.listsubjects;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.varunbarad.attendancetracker.R;
import com.varunbarad.attendancetracker.data.DatabaseHelper;
import com.varunbarad.attendancetracker.data.model.Subject;
import com.varunbarad.attendancetracker.databinding.ActivitySubjectsListBinding;
import com.varunbarad.attendancetracker.subject.addsubject.AddSubjectActivity;

import java.util.ArrayList;

public class SubjectsListActivity extends AppCompatActivity {
  private ActivitySubjectsListBinding dataBinding;
  private SubjectsAdapter subjectsAdapter;
  private String currentFilter;
  
  public static void start(Context context) {
    Intent starter = new Intent(context, SubjectsListActivity.class);
    context.startActivity(starter);
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_subjects_list);
  
    this.setSupportActionBar(this.dataBinding.toolbar);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    this.dataBinding
        .recyclerViewSubjects
        .setLayoutManager(layoutManager);
  
    this.dataBinding
        .spinnerSubjectsFilter
        .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            final String[] filterEntries = SubjectsListActivity.this.getResources().getStringArray(R.array.subjectFilterItems);
            if ((SubjectsListActivity.this.currentFilter == null) || (!SubjectsListActivity.this.currentFilter.equals(filterEntries[position]))) {
              SubjectsListActivity.this.showSubjectsAccordingToFilter(position);
            }
          }
        
          @Override
          public void onNothingSelected(AdapterView<?> parent) {
          
          }
        });
  
    this.dataBinding
        .buttonAddSubject
        .setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            AddSubjectActivity.start(SubjectsListActivity.this);
          }
        });
  }
  
  @Override
  protected void onStart() {
    super.onStart();
    this.showSubjectsAccordingToFilter(this.dataBinding.spinnerSubjectsFilter.getSelectedItemPosition());
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
  
  private void showSubjectsAccordingToFilter(int position) {
    final String[] filterEntries = this.getResources().getStringArray(R.array.subjectFilterItems);
    SubjectsListActivity.this.currentFilter = filterEntries[position];
    
    if (SubjectsListActivity.this.currentFilter.equalsIgnoreCase("current")) {
      SubjectsListActivity.this.showProgress();
      SubjectsListActivity.this.showSubjects(
          DatabaseHelper.getCurrentSubjects(SubjectsListActivity.this)
      );
    } else if (SubjectsListActivity.this.currentFilter.equalsIgnoreCase("archived")) {
      SubjectsListActivity.this.showProgress();
      SubjectsListActivity.this.showSubjects(
          DatabaseHelper.getArchivedSubjects(SubjectsListActivity.this)
      );
    }
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
        this.subjectsAdapter = new SubjectsAdapter(this, subjects);
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
}
