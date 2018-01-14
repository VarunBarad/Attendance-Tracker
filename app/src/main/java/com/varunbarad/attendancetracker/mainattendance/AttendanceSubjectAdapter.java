package com.varunbarad.attendancetracker.mainattendance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.varunbarad.attendancetracker.R;
import com.varunbarad.attendancetracker.data.model.Subject;
import com.varunbarad.attendancetracker.databinding.ListItemAttendanceBinding;
import com.varunbarad.attendancetracker.util.Helper;
import com.varunbarad.attendancetracker.util.PreferenceHelper;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Creator: Varun Barad
 * Date: 04-01-2018
 * Project: AttendanceTracker
 */
public class AttendanceSubjectAdapter extends RecyclerView.Adapter<AttendanceSubjectAdapter.ViewHolder> {
  private ArrayList<Subject> subjects;
  private Context context;
  
  public AttendanceSubjectAdapter(ArrayList<Subject> subjects, Context context) {
    this.subjects = subjects;
    this.context = context;
  }
  
  void setSubjects(ArrayList<Subject> subjects) {
    this.subjects = subjects;
    this.notifyDataSetChanged();
  }
  
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ListItemAttendanceBinding itemBinding =
        ListItemAttendanceBinding.inflate(inflater, parent, false);
  
    return new AttendanceSubjectAdapter.ViewHolder(itemBinding);
  }
  
  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(this.subjects.get(position));
  }
  
  @Override
  public int getItemCount() {
    if (this.subjects != null) {
      return this.subjects.size();
    } else {
      return 0;
    }
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ListItemAttendanceBinding itemBinding;
    
    public ViewHolder(ListItemAttendanceBinding itemBinding) {
      super(itemBinding.getRoot());
      
      this.itemBinding = itemBinding;
      
      this.itemBinding
          .buttonAttend
          .setOnClickListener(this);
      
      this.itemBinding
          .buttonSkip
          .setOnClickListener(this);
      
      this.itemBinding
          .buttonCancel
          .setOnClickListener(this);
    }
    
    private void bind(Subject subject) {
      Context context = this.itemBinding.getRoot().getContext();
      
      this.itemBinding
          .subjectName
          .setText(subject.getName());
      
      this.itemBinding
          .totalClasses
          .setText(String.valueOf(subject.getAttendances().size()));
      
      this.itemBinding
          .threshold
          .setText(String.format(Locale.getDefault(), "%d%% Required", subject.getThreshold()));
      
      this.itemBinding
          .textViewAttend
          .setText(String.valueOf(subject.getAttended().size()));
      
      this.itemBinding
          .textViewSkip
          .setText(String.valueOf(subject.getSkipped().size()));
      
      this.itemBinding
          .textViewCancel
          .setText(String.valueOf(subject.getCancelled().size()));
      
      int numberOfClassesToAttend;
      if (PreferenceHelper.countCancelledAsSkipped(context)) {
        numberOfClassesToAttend = Helper.calculateNumberOfClassesToAttend(
            subject.getAttended().size(),
            subject.getSkipped().size() + subject.getCancelled().size(),
            subject.getThreshold()
        );
      } else {
        numberOfClassesToAttend = Helper.calculateNumberOfClassesToAttend(
            subject.getAttended().size(),
            subject.getSkipped().size(),
            subject.getThreshold()
        );
      }
      
      String message;
      if (numberOfClassesToAttend < 0) {
        message = context.getResources().getString(R.string.message_attendance_canSkip, Math.abs(numberOfClassesToAttend));
      } else {
        message = context.getResources().getString(R.string.message_attendance_needAttend, numberOfClassesToAttend);
      }
      this.itemBinding
          .message
          .setText(message);
    }
    
    @Override
    public void onClick(View view) {
      int id = view.getId();
      
      if (id == this.itemBinding.buttonAttend.getId()) {
        //ToDo: Attend button pressed
      } else if (id == this.itemBinding.buttonSkip.getId()) {
        //ToDo: Skip button pressed
      } else if (id == this.itemBinding.buttonCancel.getId()) {
        //ToDo: Cancel button pressed
      }
    }
  }
}
