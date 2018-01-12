package com.varunbarad.attendancetracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.varunbarad.attendancetracker.data.model.Attendance;
import com.varunbarad.attendancetracker.data.model.Subject;
import com.varunbarad.attendancetracker.data.sqlite.AttendanceContract;
import com.varunbarad.attendancetracker.util.Helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

/**
 * Creator: Varun Barad
 * Date: 09-01-2018
 * Project: AttendanceTracker
 */
public final class DatabaseHelper {
  private static Subject readOneSubject(Cursor cursor) {
    long id = cursor.getLong(cursor.getColumnIndex(AttendanceContract.Subject.COLUMN_ID));
    String name = cursor.getString(cursor.getColumnIndex(AttendanceContract.Subject.COLUMN_NAME));
    double threshold = cursor.getDouble(cursor.getColumnIndex(AttendanceContract.Subject.COLUMN_THRESHOLD));
    boolean isArchived = (cursor.getInt(cursor.getColumnIndex(AttendanceContract.Subject.COLUMN_ARCHIVED)) == 1);
    
    return new Subject(id, name, threshold, isArchived, null);
  }
  
  private static Attendance readOneAttendance(Cursor cursor) {
    long subjectId = cursor.getLong(cursor.getColumnIndex(AttendanceContract.Attendance.COLUMN_SUBJECT_ID));
    Date date = Helper.deserializeDate(cursor.getString(cursor.getColumnIndex(AttendanceContract.Attendance.COLUMN_DATE)));
    int status = cursor.getInt(cursor.getColumnIndex(AttendanceContract.Attendance.COLUMN_ATTENDANCE));
    
    return new Attendance(subjectId, date, status);
  }
  
  private static ArrayList<Attendance> getAttendance(Context context, final long subjectId) {
    final String SELECTION;
    final String[] SELECTION_ARGS;
    if (subjectId == -1) {
      SELECTION = null;
      SELECTION_ARGS = null;
    } else {
      SELECTION = AttendanceContract.Attendance.COLUMN_SUBJECT_ID + " = ?";
      SELECTION_ARGS = new String[]{String.valueOf(subjectId)};
    }
    
    Cursor cursor = context.getContentResolver().query(
        AttendanceContract.Attendance.CONTENT_URI,
        null,
        SELECTION,
        SELECTION_ARGS,
        null
    );
    
    ArrayList<Attendance> attendances;
    
    if (cursor != null) {
      if (cursor.getCount() < 1) {
        attendances = new ArrayList<>(cursor.getCount());
        
        cursor.moveToFirst();
        do {
          attendances.add(DatabaseHelper.readOneAttendance(cursor));
        } while (cursor.moveToNext());
      } else {
        attendances = new ArrayList<>(0);
      }
      
      cursor.close();
    } else {
      attendances = new ArrayList<>(0);
    }
    
    return attendances;
  }
  
  public static ArrayList<Attendance> getAttendanceForSubject(Context context, final Subject subject) {
    return DatabaseHelper.getAttendance(context, subject.getId());
  }
  
  private static ArrayList<Subject> getSubjects(Context context, final String selectionCriteria) {
    Cursor cursor = context.getContentResolver().query(
        AttendanceContract.Subject.CONTENT_URI,
        null,
        selectionCriteria,
        null,
        null
    );
    
    ArrayList<Subject> subjects;
    if (cursor != null) {
      if (cursor.getCount() < 1) {
        Hashtable<Long, Subject> subjectsTable = new Hashtable<>(cursor.getCount());
        
        cursor.moveToFirst();
        do {
          Subject s = DatabaseHelper.readOneSubject(cursor);
          subjectsTable.put(s.getId(), s);
        } while (cursor.moveToNext());
        
        ArrayList<Attendance> attendances = DatabaseHelper.getAttendance(context, -1);
        for (Attendance a : attendances) {
          subjectsTable.get(a.getSubjectId()).addAttendance(a);
        }
        
        subjects = new ArrayList<>(subjectsTable.values());
      } else {
        subjects = new ArrayList<>(0);
      }
      
      cursor.close();
    } else {
      subjects = new ArrayList<>(0);
    }
    
    return subjects;
  }
  
  public static ArrayList<Subject> getAllSubjects(Context context) {
    return DatabaseHelper.getSubjects(context, null);
  }
  
  public static ArrayList<Subject> getCurrentSubjects(Context context) {
    final String SELECTION_CURRENT_SUBJECTS = AttendanceContract.Subject.COLUMN_ARCHIVED + " = 0";
    return DatabaseHelper.getSubjects(context, SELECTION_CURRENT_SUBJECTS);
  }
  
  public static ArrayList<Subject> getArchivedSubjects(Context context) {
    final String SELECTION_ARCHIVED_SUBJECTS = AttendanceContract.Subject.COLUMN_ARCHIVED + " = 1";
    return DatabaseHelper.getSubjects(context, SELECTION_ARCHIVED_SUBJECTS);
  }
  
  public static void addAttendance(Context context, Attendance attendance) {
    context.getContentResolver().insert(
        AttendanceContract.Attendance.CONTENT_URI,
        DatabaseHelper.toAttendanceContentValues(attendance)
    );
  }
  
  private static void addAllAttendance(Context context, ArrayList<Attendance> attendances) {
    ContentValues[] values = new ContentValues[attendances.size()];
    for (int i = 0; i < values.length; i++) {
      values[i] = DatabaseHelper.toAttendanceContentValues(attendances.get(i));
    }
    
    context.getContentResolver().bulkInsert(
        AttendanceContract.Attendance.CONTENT_URI,
        values
    );
  }
  
  public static void addSubject(Context context, Subject subject) {
    context.getContentResolver().insert(
        AttendanceContract.Subject.CONTENT_URI,
        DatabaseHelper.toSubjectContentValues(subject)
    );
    
    DatabaseHelper.addAllAttendance(context, subject.getAttendances());
  }
  
  public static ContentValues toSubjectContentValues(Subject subject) {
    ContentValues subjectValues = new ContentValues();
    
    subjectValues.put(AttendanceContract.Subject.COLUMN_ID, subject.getId());
    subjectValues.put(AttendanceContract.Subject.COLUMN_NAME, subject.getName());
    subjectValues.put(AttendanceContract.Subject.COLUMN_THRESHOLD, subject.getThreshold());
    subjectValues.put(AttendanceContract.Subject.COLUMN_ARCHIVED, (subject.isArchived() ? 1 : 0));
    
    return subjectValues;
  }
  
  public static ContentValues toAttendanceContentValues(Attendance attendance) {
    ContentValues attendanceValues = new ContentValues();
    
    attendanceValues.put(AttendanceContract.Attendance.COLUMN_SUBJECT_ID, attendance.getSubjectId());
    attendanceValues.put(AttendanceContract.Attendance.COLUMN_ATTENDANCE, attendance.getAttendanceStatus());
    attendanceValues.put(AttendanceContract.Attendance.COLUMN_DATE, Helper.serializeDate(attendance.getClassDate()));
    
    return attendanceValues;
  }
}
