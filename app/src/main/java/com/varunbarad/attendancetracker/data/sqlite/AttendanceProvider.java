package com.varunbarad.attendancetracker.data.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Creator: Varun Barad
 * Date: 11-01-2018
 * Project: AttendanceTracker
 */
public class AttendanceProvider extends ContentProvider {
  public static final int CODE_SUBJECT = 100;
  public static final int CODE_ATTENDANCE = 200;
  private static final UriMatcher uriMatcher = AttendanceProvider.buildUriMatcher();
  private AttendanceSQLiteHelper databaseHelper;
  
  private static UriMatcher buildUriMatcher() {
    final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    final String authority = AttendanceContract.CONTENT_AUTHORITY;
    
    matcher.addURI(authority, AttendanceContract.PATH_SUBJECT, CODE_SUBJECT);
    matcher.addURI(authority, AttendanceContract.PATH_ATTENDANCE, CODE_ATTENDANCE);
    
    return matcher;
  }
  
  @Override
  public boolean onCreate() {
    this.databaseHelper = new AttendanceSQLiteHelper(this.getContext());
    return true;
  }
  
  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    Cursor cursor;
    
    switch (AttendanceProvider.uriMatcher.match(uri)) {
      case AttendanceProvider.CODE_SUBJECT:
        cursor = this.databaseHelper.getReadableDatabase().query(
            AttendanceContract.Subject.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        );
        break;
      case AttendanceProvider.CODE_ATTENDANCE:
        cursor = this.databaseHelper.getReadableDatabase().query(
            AttendanceContract.Attendance.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        );
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    
    cursor.setNotificationUri(this.getContext().getContentResolver(), uri);
    return cursor;
  }
  
  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    throw new RuntimeException("getType is not implemented in Attendance Tracker.");
  }
  
  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    final SQLiteDatabase database = this.databaseHelper.getWritableDatabase();
    
    switch (AttendanceProvider.uriMatcher.match(uri)) {
      case AttendanceProvider.CODE_SUBJECT: {
        database.beginTransaction();
        try {
          database.insert(
              AttendanceContract.Subject.TABLE_NAME,
              null,
              values
          );
          database.setTransactionSuccessful();
        } finally {
          database.endTransaction();
        }
        
        break;
      }
      case AttendanceProvider.CODE_ATTENDANCE: {
        database.beginTransaction();
        try {
          database.insert(
              AttendanceContract.Attendance.TABLE_NAME,
              null,
              values
          );
          database.setTransactionSuccessful();
        } finally {
          database.endTransaction();
        }
        
        break;
      }
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    
    return uri;
  }
  
  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
    int numberOfRowsDeleted = 0;
    
    final SQLiteDatabase database = this.databaseHelper.getWritableDatabase();
    switch (AttendanceProvider.uriMatcher.match(uri)) {
      case AttendanceProvider.CODE_SUBJECT:
        database.beginTransaction();
        try {
          numberOfRowsDeleted = database.delete(
              AttendanceContract.Subject.TABLE_NAME,
              selection,
              selectionArgs
          );
          database.setTransactionSuccessful();
        } finally {
          database.endTransaction();
        }
        
        break;
      case AttendanceProvider.CODE_ATTENDANCE:
        database.beginTransaction();
        try {
          numberOfRowsDeleted = database.delete(
              AttendanceContract.Attendance.TABLE_NAME,
              selection,
              selectionArgs
          );
          database.setTransactionSuccessful();
        } finally {
          database.endTransaction();
        }
        
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    
    return numberOfRowsDeleted;
  }
  
  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
    int numberOfRowsUpdated = 0;
    
    final SQLiteDatabase database = this.databaseHelper.getWritableDatabase();
    switch (AttendanceProvider.uriMatcher.match(uri)) {
      case AttendanceProvider.CODE_SUBJECT:
        database.beginTransaction();
        try {
          numberOfRowsUpdated = database.update(
              AttendanceContract.Subject.TABLE_NAME,
              values,
              selection,
              selectionArgs
          );
          database.setTransactionSuccessful();
        } finally {
          database.endTransaction();
        }
        
        break;
      case AttendanceProvider.CODE_ATTENDANCE:
        database.beginTransaction();
        try {
          numberOfRowsUpdated = database.update(
              AttendanceContract.Attendance.TABLE_NAME,
              values,
              selection,
              selectionArgs
          );
          database.setTransactionSuccessful();
        } finally {
          database.endTransaction();
        }
        
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    
    return numberOfRowsUpdated;
  }
}
