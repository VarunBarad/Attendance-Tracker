package com.varunbarad.attendancetracker.about;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Creator: Varun Barad
 * Date: 22-01-2018
 * Project: AttendanceTracker
 */
public final class DeveloperDetails {
  @Expose
  @SerializedName("description")
  private final String description;
  
  public DeveloperDetails(String description) {
    this.description = description;
  }
  
  public String getDescription() {
    return description;
  }
}
