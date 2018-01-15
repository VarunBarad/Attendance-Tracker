package com.varunbarad.attendancetracker.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.varunbarad.attendancetracker.R;
import com.varunbarad.attendancetracker.data.DatabaseHelper;
import com.varunbarad.attendancetracker.data.model.Attendance;
import com.varunbarad.attendancetracker.data.model.Subject;
import com.varunbarad.attendancetracker.util.Helper;
import com.varunbarad.attendancetracker.util.PreferenceHelper;
import com.varunbarad.attendancetracker.widget.subjectselect.SubjectSelectActivity;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class AttendanceWidget extends AppWidgetProvider {
  
  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
    // Construct the RemoteViews object
    RemoteViews views = AttendanceWidget.getWidgetRemoteViews(context);
    
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);
  }
  
  private static RemoteViews getWidgetRemoteViews(Context context) {
    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.attendance_widget);
    
    String subjectsLagging =
        context
            .getResources()
            .getString(
                R.string.message_widget_lagging,
                AttendanceWidget.getNumberOfSubjectsLagging(context)
            );
    remoteViews.setTextViewText(R.id.textView_widget_classesLagging, subjectsLagging);
    
    remoteViews.setOnClickPendingIntent(R.id.button_widget_attend, AttendanceWidget.getAttendedPendingIntent(context));
    remoteViews.setOnClickPendingIntent(R.id.button_widget_skip, AttendanceWidget.getSkippedPendingIntent(context));
    remoteViews.setOnClickPendingIntent(R.id.button_widget_cancel, AttendanceWidget.getCancelledPendingIntent(context));
    
    return remoteViews;
  }
  
  private static int getNumberOfSubjectsLagging(Context context) {
    ArrayList<Subject> allSubjects = DatabaseHelper.getCurrentSubjects(context);
    
    int count = 0;
    final boolean countCancelled = PreferenceHelper.countCancelledAsSkipped(context);
    for (Subject s : allSubjects) {
      int numberOfClassesToAttend;
      if (countCancelled) {
        numberOfClassesToAttend = Helper.calculateAttendanceRequirement(
            s.getAttended().size(),
            s.getSkipped().size() + s.getCancelled().size(),
            s.getThreshold()
        );
      } else {
        numberOfClassesToAttend = Helper.calculateAttendanceRequirement(
            s.getAttended().size(),
            s.getSkipped().size(),
            s.getThreshold()
        );
      }
      
      if (numberOfClassesToAttend >= 0) {
        count++;
      }
    }
    
    return count;
  }
  
  private static PendingIntent getAttendedPendingIntent(Context context) {
    Intent intent = SubjectSelectActivity.getStarterIntent(context, Attendance.ATTEND);
    PendingIntent pendingIntent =
        PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );
    return pendingIntent;
  }
  
  private static PendingIntent getSkippedPendingIntent(Context context) {
    Intent intent = SubjectSelectActivity.getStarterIntent(context, Attendance.SKIP);
    PendingIntent pendingIntent =
        PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );
    return pendingIntent;
  }
  
  private static PendingIntent getCancelledPendingIntent(Context context) {
    Intent intent = SubjectSelectActivity.getStarterIntent(context, Attendance.CANCEL);
    PendingIntent pendingIntent =
        PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );
    return pendingIntent;
  }
  
  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId);
    }
  }
  
  @Override
  public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
  }
  
  @Override
  public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }
}

