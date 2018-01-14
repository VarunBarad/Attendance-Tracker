package com.varunbarad.attendancetracker.data.model;

import java.util.ArrayList;

/**
 * Creator: Varun Barad
 * Date: 03-01-2018
 * Project: AttendanceTracker
 */
public final class Subject {
  private long id;
  private String name;
  private int threshold;
  private boolean archived;
  private ArrayList<Attendance> attendances;
  
  public Subject(long id, String name, int threshold, boolean archived, ArrayList<Attendance> attendances) {
    this.id = id;
    this.name = name;
    this.threshold = threshold;
    this.archived = archived;
    if (attendances != null) {
      this.attendances = attendances;
    } else {
      this.attendances = new ArrayList<>(0);
    }
  }
  
  public Subject(String name, int threshold, boolean archived, ArrayList<Attendance> attendances) {
    this(-1, name, threshold, archived, attendances);
  }
  
  public Subject(String name, int threshold, ArrayList<Attendance> attendances) {
    this(name, threshold, false, attendances);
  }
  
  public Subject(String name, int threshold) {
    this(name, threshold, null);
  }
  
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getThreshold() {
    return threshold;
  }
  
  public void setThreshold(int threshold) {
    this.threshold = threshold;
  }
  
  public boolean isArchived() {
    return archived;
  }
  
  public void setArchived(boolean archived) {
    this.archived = archived;
  }
  
  public ArrayList<Attendance> getAttendances() {
    return attendances;
  }
  
  public void setAttendances(ArrayList<Attendance> attendances) {
    this.attendances = attendances;
  }
  
  public ArrayList<Attendance> getAttended() {
    ArrayList<Attendance> attended = new ArrayList<>();
    for (Attendance a : this.attendances) {
      if (a.getAttendanceStatus() == Attendance.ATTEND) {
        attended.add(a);
      }
    }
    return attended;
  }
  
  public ArrayList<Attendance> getSkipped() {
    ArrayList<Attendance> skipped = new ArrayList<>();
    for (Attendance a : this.attendances) {
      if (a.getAttendanceStatus() == Attendance.SKIP) {
        skipped.add(a);
      }
    }
    return skipped;
  }
  
  public ArrayList<Attendance> getCancelled() {
    ArrayList<Attendance> cancelled = new ArrayList<>();
    for (Attendance a : this.attendances) {
      if (a.getAttendanceStatus() == Attendance.CANCEL) {
        cancelled.add(a);
      }
    }
    return cancelled;
  }
  
  public void addAttendance(Attendance attendance) {
    int length = this.attendances.size();
    boolean attendanceExists = false;
    for (int i = 0; i < length; i++) {
      if (this.attendances.get(i).getClassDate().equals(attendance.getClassDate())) {
        attendanceExists = true;
        this
            .attendances
            .set(i, attendance);
        break;
      }
    }
  
    if (!attendanceExists) {
      this.attendances.add(attendance);
    }
  }
}
