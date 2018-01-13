package com.varunbarad.attendancetracker.settings;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.varunbarad.attendancetracker.R;
import com.varunbarad.attendancetracker.databinding.ActivitySettingsBinding;

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
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
