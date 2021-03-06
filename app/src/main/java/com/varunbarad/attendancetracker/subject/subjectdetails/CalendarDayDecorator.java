package com.varunbarad.attendancetracker.subject.subjectdetails;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;
import com.varunbarad.attendancetracker.R;
import com.varunbarad.attendancetracker.util.Helper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Creator: Varun Barad
 * Date: 13-01-2018
 * Project: AttendanceTracker
 */
public class CalendarDayDecorator implements DayDecorator {
  private Context context;
  private ArrayList<Date> dates;
  @ColorRes
  private int colorResourceId;
  
  public CalendarDayDecorator(Context context, ArrayList<Date> dates, @ColorRes int colorResourceId) {
    this.context = context;
    this.dates = dates;
    this.colorResourceId = colorResourceId;
  }
  
  @Override
  public void decorate(DayView dayView) {
    Date date = Helper.stripTime(dayView.getDate());
    for (Date d : dates) {
      if (d.equals(date)) {
        dayView.setBackgroundColor(ContextCompat.getColor(context, this.colorResourceId));
        dayView.setTextColor(ContextCompat.getColor(context, R.color.colorText));
        break;
      }
    }
  }
}
