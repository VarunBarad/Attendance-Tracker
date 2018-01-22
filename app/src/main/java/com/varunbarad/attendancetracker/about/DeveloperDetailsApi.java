package com.varunbarad.attendancetracker.about;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Creator: Varun Barad
 * Date: 22-01-2018
 * Project: AttendanceTracker
 */
public interface DeveloperDetailsApi {
  String PROFILE_IMAGE_URL = "https://raw.githubusercontent.com/VarunBarad/Capstone-Project/master/resources/developer-profile.png";
  String WEBSITE_URL = "http://varunbarad.com";
  
  String baseUrl = "https://raw.githubusercontent.com/";
  
  @GET("/VarunBarad/Capstone-Project/master/resources/developer-details.json")
  Call<DeveloperDetails> getDeveloperDetails();
}
