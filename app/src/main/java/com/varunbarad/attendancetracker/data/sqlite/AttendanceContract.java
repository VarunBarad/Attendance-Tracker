package com.varunbarad.attendancetracker.data.sqlite;

/**
 * Creator: Varun Barad
 * Date: 09-01-2018
 * Project: AttendanceTracker
 */
public final class AttendanceContract {
  public static final class Subject {
    public static final String TABLE_NAME = "subject";
    
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_THRESHOLD = "threshold";
    public static final String COLUMN_ARCHIVED = "archived";
  }
  
  public static final class Attendance {
    public static final String TABLE_NAME = "attendance";
    
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_ATTENDANCE = "attendance";
    public static final String COLUMN_SUBJECT_ID = "subject_id";
  }
}
