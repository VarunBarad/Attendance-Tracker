package com.varunbarad.attendancetracker.subject.editsubject;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import com.varunbarad.attendancetracker.R;
import com.varunbarad.attendancetracker.data.DatabaseHelper;
import com.varunbarad.attendancetracker.data.model.Subject;
import com.varunbarad.attendancetracker.databinding.ActivityEditSubjectBinding;

import java.util.Locale;

public class EditSubjectActivity extends AppCompatActivity implements View.OnClickListener {
  private static final String KEY_SUBJECT_ID = "subject_id";
  private ActivityEditSubjectBinding dataBinding;
  private Subject subject;
  
  public static void start(Context context, long subjectId) {
    Intent starter = new Intent(context, EditSubjectActivity.class);
    starter.putExtra(KEY_SUBJECT_ID, subjectId);
    context.startActivity(starter);
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_subject);
  
    this.setSupportActionBar(this.dataBinding.toolbar);
    this.getSupportActionBar()
        .setDisplayHomeAsUpEnabled(true);
  }
  
  @Override
  protected void onStart() {
    super.onStart();
    this.subject = DatabaseHelper.getSingleSubject(this, this.getSubjectIdPassedToActivity());
    
    this.getSupportActionBar()
        .setTitle(this.subject.getName());
    
    this.dataBinding
        .textInputSubjectName
        .setText(this.subject.getName());
    
    this.dataBinding
        .seekBarThreshold
        .setProgress(this.subject.getThreshold());
    
    this.dataBinding
        .textViewThreshold
        .setText(String.format(Locale.getDefault(), "%d%%", this.subject.getThreshold()));
    
    this.dataBinding
        .seekBarThreshold
        .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            EditSubjectActivity
                .this
                .dataBinding
                .textViewThreshold
                .setText(String.format(Locale.getDefault(), "%d%%", progress));
          }
          
          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {
          
          }
          
          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {
          
          }
        });
    
    this.dataBinding
        .buttonSetDetails
        .setOnClickListener(this);
    
    this.dataBinding
        .textInputSubjectName
        .addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {
          
          }
          
          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
          
          }
          
          @Override
          public void afterTextChanged(Editable editable) {
            if (editable.toString().trim().matches(".*\\S+.*")) {
              EditSubjectActivity
                  .this
                  .dataBinding
                  .buttonSetDetails
                  .setEnabled(true);
            } else {
              EditSubjectActivity
                  .this
                  .dataBinding
                  .buttonSetDetails
                  .setEnabled(false);
            }
          }
        });
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
  
  private long getSubjectIdPassedToActivity() {
    return this.getIntent().getLongExtra(KEY_SUBJECT_ID, -1);
  }
  
  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.button_setDetails:
        this.commitChangesToDatabase();
        break;
    }
  }
  
  private void commitChangesToDatabase() {
    String subjectName = this.dataBinding.textInputSubjectName.getText().toString().trim();
    int threshold = this.dataBinding.seekBarThreshold.getProgress();
    
    this.subject
        .setName(subjectName);
    this.subject
        .setThreshold(threshold);
    
    DatabaseHelper.editSubject(this, this.subject);
    this.onBackPressed();
  }
}
