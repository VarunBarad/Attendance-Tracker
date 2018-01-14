package com.varunbarad.attendancetracker.subject.addsubject;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
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
import com.varunbarad.attendancetracker.databinding.ActivityAddSubjectBinding;
import com.varunbarad.attendancetracker.util.PreferenceHelper;

import java.util.Locale;

public class AddSubjectActivity extends AppCompatActivity implements View.OnClickListener {
  private ActivityAddSubjectBinding dataBinding;
  
  public static void start(Context context) {
    Intent starter = new Intent(context, AddSubjectActivity.class);
    context.startActivity(starter);
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_subject);
  
    this.dataBinding
        .toolbar
        .setTitle(R.string.title_addSubject);
    this.setSupportActionBar(this.dataBinding.toolbar);
    this.getSupportActionBar()
        .setDisplayHomeAsUpEnabled(true);
  
    this.dataBinding
        .seekBarThreshold
        .setProgress(PreferenceHelper.getDefaultThreshold(this));
  
    this.dataBinding
        .textViewThreshold
        .setText(String.format(Locale.getDefault(), "%d%%", PreferenceHelper.getDefaultThreshold(this)));
  
    this.dataBinding
        .seekBarThreshold
        .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            AddSubjectActivity
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
        .buttonClear
        .setOnClickListener(this);
  
    this.dataBinding
        .buttonAddSubject
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
              AddSubjectActivity
                  .this
                  .dataBinding
                  .buttonAddSubject
                  .setEnabled(true);
            } else {
              AddSubjectActivity
                  .this
                  .dataBinding
                  .buttonAddSubject
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
  
  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.button_addSubject:
        this.addSubjectToDatabase();
        break;
      case R.id.button_clear:
        this.clearInputs();
        break;
    }
  }
  
  private void addSubjectToDatabase() {
    String subjectName = this.dataBinding.textInputSubjectName.getText().toString().trim();
    int threshold = this.dataBinding.seekBarThreshold.getProgress();
    
    Subject subject = new Subject(subjectName, threshold, false, null);
    DatabaseHelper.addSubject(this, subject);
    
    this.onBackPressed();
  }
  
  private void clearInputs() {
    this.dataBinding
        .textInputSubjectName
        .setText("");
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      this.dataBinding
          .seekBarThreshold
          .setProgress(PreferenceHelper.getDefaultThreshold(this), true);
    } else {
      this.dataBinding
          .seekBarThreshold
          .setProgress(PreferenceHelper.getDefaultThreshold(this));
    }
  }
}
