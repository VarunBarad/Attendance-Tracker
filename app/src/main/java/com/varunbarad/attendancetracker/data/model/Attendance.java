package com.varunbarad.attendancetracker.data.model;

import java.util.Date;

/**
 * Creator: Varun Barad
 * Date: 03-01-2018
 * Project: AttendanceTracker
 */
public final class Attendance {
  public static final int ATTEND = 0;
  public static final int SKIP = 1;
  public static final int CANCEL = 2;
  
  private long subjectId;
  private Date classDate;
  private int attendanceStatus;
  
  public Attendance(long subjectId, Date classDate, int attendanceStatus) {
    this.subjectId = subjectId;
    this.classDate = classDate;
    this.attendanceStatus = attendanceStatus;
  }
  
  public long getSubjectId() {
    return subjectId;
  }
  
  public void setSubjectId(long subjectId) {
    this.subjectId = subjectId;
  }
  
  public Date getClassDate() {
    return classDate;
  }
  
  public void setClassDate(Date classDate) {
    this.classDate = classDate;
  }
  
  public int getAttendanceStatus() {
    return attendanceStatus;
  }
  
  public void setAttendanceStatus(int attendanceStatus) {
    this.attendanceStatus = attendanceStatus;
  }
}
