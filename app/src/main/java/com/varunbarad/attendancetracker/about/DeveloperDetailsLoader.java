package com.varunbarad.attendancetracker.about;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.varunbarad.attendancetracker.R;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Creator: Varun Barad
 * Date: 22-01-2018
 * Project: AttendanceTracker
 */
public class DeveloperDetailsLoader extends AsyncTaskLoader<DeveloperDetails> {
  private static final long ACCEPTABLE_DELAY = 10 * 60 * 1000;
  
  private DeveloperDetails developerDetails;
  
  private long lastLoadTime = 0;
  
  private DeveloperDetailsLoader(Context context) {
    super(context);
  }
  
  public static DeveloperDetailsLoader getInstance(Context context) {
    return new DeveloperDetailsLoader(context);
  }
  
  @Override
  protected void onStartLoading() {
    if ((this.developerDetails != null) && (!this.isDataRefreshNeeded())) {
      this.deliverResult(this.developerDetails);
    } else {
      this.forceLoad();
    }
  }
  
  @Nullable
  @Override
  public DeveloperDetails loadInBackground() {
    if ((this.developerDetails != null) && (!this.isDataRefreshNeeded())) {
      return this.developerDetails;
    } else {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(DeveloperDetailsApi.baseUrl)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
      
      DeveloperDetailsApi developerDetailsApi = retrofit.create(DeveloperDetailsApi.class);
      
      Response<DeveloperDetails> response;
      try {
        response = developerDetailsApi
            .getDeveloperDetails()
            .execute();
      } catch (IOException e) {
        e.printStackTrace();
        response = null;
      }
      
      final DeveloperDetails developerDetails;
      if ((response != null) && (response.isSuccessful())) {
        developerDetails = response.body();
      } else {
        developerDetails = new DeveloperDetails(this.getContext().getString(R.string.message_developerDetails));
      }
      
      this.lastLoadTime = System.currentTimeMillis();
      return developerDetails;
    }
  }
  
  @Override
  public void deliverResult(DeveloperDetails data) {
    this.developerDetails = data;
    
    // Need to return a new object every time or else it won't call
    // onLoadFinished if it finds the same reference being returned
    super.deliverResult(new DeveloperDetails(this.developerDetails.getDescription()));
  }
  
  private boolean isDataRefreshNeeded() {
    return ((System.currentTimeMillis() - this.lastLoadTime) > DeveloperDetailsLoader.ACCEPTABLE_DELAY);
  }
}
