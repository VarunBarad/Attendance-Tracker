package com.varunbarad.attendancetracker.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Creator: Varun Barad
 * Date: 09-01-2018
 * Project: AttendanceTracker
 */
public class AttendanceSQLiteHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "attendace-tracker.db";
  
  private static final int DATABASE_VERSION = 1;
  
  public AttendanceSQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
  
  @Override
  public void onCreate(SQLiteDatabase db) {
    final String SQL_CREATE_TABLE_SUBJECT =
        "CREATE TABLE " + AttendanceContract.Subject.TABLE_NAME + " (" +
            AttendanceContract.Subject.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            AttendanceContract.Subject.COLUMN_NAME + " TEXT NOT NULL, " +
            AttendanceContract.Subject.COLUMN_THRESHOLD + " INTEGER NOT NULL, " +
            AttendanceContract.Subject.COLUMN_ARCHIVED + " INTEGER DEFAULT 0" +
            ");";
    db.execSQL(SQL_CREATE_TABLE_SUBJECT);
    
    final String SQL_CREATE_TABLE_ATTENDANCE =
        "CREATE TABLE " + AttendanceContract.Attendance.TABLE_NAME + " (" +
            AttendanceContract.Attendance.COLUMN_ATTENDANCE + " INTEGER NOT NULL, " +
            AttendanceContract.Attendance.COLUMN_DATE + " TEXT NOT NULL, " +
            AttendanceContract.Attendance.COLUMN_SUBJECT_ID + " INTEGER REFERENCES " + AttendanceContract.Subject.TABLE_NAME + "(" + AttendanceContract.Subject.COLUMN_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, " +
            "UNIQUE (" + AttendanceContract.Attendance.COLUMN_SUBJECT_ID + ", " + AttendanceContract.Attendance.COLUMN_DATE + ") ON CONFLICT REPLACE" +
            ");";
    db.execSQL(SQL_CREATE_TABLE_ATTENDANCE);
  }
  
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + AttendanceContract.Attendance.TABLE_NAME + ";");
    db.execSQL("DROP TABLE IF EXISTS " + AttendanceContract.Subject.TABLE_NAME + ";");
    this.onCreate(db);
  }
}
