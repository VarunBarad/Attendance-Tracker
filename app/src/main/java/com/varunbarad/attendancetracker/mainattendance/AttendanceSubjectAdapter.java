package com.varunbarad.attendancetracker.mainattendance;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Creator: Varun Barad
 * Date: 04-01-2018
 * Project: AttendanceTracker
 */
public class AttendanceSubjectAdapter extends RecyclerView.Adapter<AttendanceSubjectAdapter.ViewHolder> {
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }
  
  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
  
  }
  
  @Override
  public int getItemCount() {
    return 0;
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
