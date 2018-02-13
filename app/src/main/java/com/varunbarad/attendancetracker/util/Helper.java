package com.varunbarad.attendancetracker.util;

import android.appwidget.AppWidgetManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.varunbarad.attendancetracker.widget.AttendanceWidget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Creator: Varun Barad
 * Date: 04-01-2018
 * Project: AttendanceTracker
 */
public final class Helper {
  public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
  public static final SimpleDateFormat dateFormatUserFriendly = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
  
  private static double calculateSkippableClasses(int attended, int skipped, int threshold) {
    double numerator = (attended * (100 - threshold)) - (skipped * threshold);
    double denominator = threshold;
    
    return (numerator / denominator);
  }
  
  private static double calculateClassesToAttend(int attended, int skipped, int threshold) {
    int numerator = (attended * (threshold - 100)) + (skipped * threshold);
    int denominator = 100 - threshold;
    
    return (numerator / denominator);
  }
  
  public static int calculateAttendanceRequirement(int attended, int skipped, int threshold) {
    if (threshold == 0) {
      return 0;
    } else {
      double toAttend = Helper.calculateClassesToAttend(attended, skipped, threshold);
      double canSkip = Helper.calculateSkippableClasses(attended, skipped, threshold);
  
      if (toAttend >= 0.0d) {
        return (int) Math.ceil(toAttend);
      } else {
        return (int) (-Math.floor(canSkip));
      }
    }
  }
  
  public static String serializeDate(Date date) {
    return dateFormat.format(date);
  }
  
  public static Date deserializeDate(String date) {
    try {
      return dateFormat.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
      return new Date();
    }
  }
  
  public static Date stripTime(Date date) {
    return new Date(date.getYear(), date.getMonth(), date.getDate(), 0, 0, 0);
  }
  
  public static boolean isDateInFuture(Date d1, Date d2) {
    return d1.getTime() > d2.getTime();
  }
  
  public static String formatDateForUser(Date date) {
    return dateFormatUserFriendly.format(date);
  }
  
  public static void updateWidgets(Context context) {
    Intent widgetUpdateIntent = new Intent(context, AttendanceWidget.class);
    widgetUpdateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
    int[] widgetIds =
        AppWidgetManager.getInstance(context)
            .getAppWidgetIds(new ComponentName(context, AttendanceWidget.class));
    widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);
    context.sendBroadcast(widgetUpdateIntent);
  }
  
  public static void openUrlInBrowser(String url, Context context) {
    Uri githubUri = Uri.parse(url);
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, githubUri);
    context.startActivity(browserIntent);
  }
  
  public static void showRatingsScreen(Context context, String packageName) {
    String playStoreAppUri = "market://details?id=" + packageName;
    String playStoreSiteUri = "https://play.google.com/store/apps/details?id=" + packageName;
    
    try {
      Intent playStoreAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreAppUri));
      context.startActivity(playStoreAppIntent);
    } catch (ActivityNotFoundException e) {
      Helper.openUrlInBrowser(playStoreSiteUri, context);
    }
  }
}
