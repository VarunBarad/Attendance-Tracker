package com.varunbarad.attendancetracker.about;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.varunbarad.attendancetracker.R;
import com.varunbarad.attendancetracker.databinding.ActivityDeveloperBinding;
import com.varunbarad.attendancetracker.util.Helper;

public class DeveloperActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<DeveloperDetails> {
  private static final int LOADER_DEVELOPER_DETAILS = 1;
  private ActivityDeveloperBinding dataBinding;
  
  public static void start(Context context) {
    Intent starter = new Intent(context, DeveloperActivity.class);
    context.startActivity(starter);
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_developer);
  
    this.setSupportActionBar(this.dataBinding.toolbar);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  
    Picasso
        .with(this)
        .load(DeveloperDetailsApi.PROFILE_IMAGE_URL)
        .placeholder(R.drawable.developer_profile)
        .error(R.drawable.developer_profile)
        .into(this.dataBinding.profilePicture);
  
    this.dataBinding
        .openWebsite
        .setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Helper.openUrlInBrowser(
                DeveloperDetailsApi.WEBSITE_URL,
                DeveloperActivity.this
            );
          }
        });
  }
  
  @Override
  public Loader<DeveloperDetails> onCreateLoader(int id, Bundle args) {
    return DeveloperDetailsLoader.getInstance(this);
  }
  
  @Override
  public void onLoadFinished(Loader<DeveloperDetails> loader, DeveloperDetails data) {
    this.showDetails(data);
  }
  
  @Override
  public void onLoaderReset(Loader<DeveloperDetails> loader) {
  
  }
  
  private void showDetails(DeveloperDetails developerDetails) {
    this.dataBinding
        .description
        .setText(developerDetails.getDescription());
  }
}
