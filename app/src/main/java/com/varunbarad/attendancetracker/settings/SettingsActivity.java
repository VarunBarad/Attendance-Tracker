package com.varunbarad.attendancetracker.settings;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.varunbarad.attendancetracker.R;
import com.varunbarad.attendancetracker.databinding.ActivitySettingsBinding;
import com.varunbarad.attendancetracker.util.PreferenceHelper;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
  private ActivitySettingsBinding dataBinding;
  
  public static void start(Context context) {
    Intent starter = new Intent(context, SettingsActivity.class);
    context.startActivity(starter);
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
  
    this.dataBinding
        .toolbar
        .setTitle(R.string.title_settings);
    this.setSupportActionBar(this.dataBinding.toolbar);
    this.getSupportActionBar()
        .setDisplayHomeAsUpEnabled(true);
  
    this.handleSeekBar();
    this.handleCheckBox();
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
  
  private void handleSeekBar() {
    this.dataBinding
        .thresholdSeekBar
        .setMax(PreferenceHelper.MAX_THRESHOLD);
    this.dataBinding
        .thresholdSeekBar
        .setProgress(PreferenceHelper.getDefaultThreshold(this));
    
    this.dataBinding
        .thresholdTextViewValue
        .setText(String.format(Locale.getDefault(), "%d%%", PreferenceHelper.getDefaultThreshold(this)));
    
    this.dataBinding
        .thresholdSeekBar
        .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            SettingsActivity
                .this
                .dataBinding
                .thresholdTextViewValue
                .setText(String.format(Locale.getDefault(), "%d%%", progress));
          }
          
          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {
          
          }
          
          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {
            PreferenceHelper.setDefaultThreshold(
                SettingsActivity.this,
                seekBar.getProgress()
            );
          }
        });
  }
  
  private void handleCheckBox() {
    this.dataBinding
        .countCancelledCheckBox
        .setChecked(PreferenceHelper.countCancelledAsSkipped(this));
    
    this.setCountCancelledAsSkippedStatus(this.dataBinding.countCancelledCheckBox.isChecked());
    
    this.dataBinding
        .countCancelledCheckBox
        .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SettingsActivity.this.setCountCancelledAsSkippedStatus(isChecked);
            
            PreferenceHelper.setCountCancelledAsSkipped(
                SettingsActivity.this,
                isChecked
            );
          }
        });
  }
  
  private void setCountCancelledAsSkippedStatus(boolean countCancelledAsSkipped) {
    if (countCancelledAsSkipped) {
      this.dataBinding
          .countCancelledTextViewSubTitle
          .setText(R.string.desc_prefCountCancelled_on);
    } else {
      this.dataBinding
          .countCancelledTextViewSubTitle
          .setText(R.string.desc_prefCountCancelled_off);
    }
  }
}
