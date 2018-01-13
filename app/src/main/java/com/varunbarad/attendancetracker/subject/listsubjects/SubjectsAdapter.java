package com.varunbarad.attendancetracker.subject.listsubjects;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.varunbarad.attendancetracker.data.model.Subject;
import com.varunbarad.attendancetracker.databinding.ListItemSubjectBinding;
import com.varunbarad.attendancetracker.subject.subjectdetails.SubjectDetailsActivity;

import java.util.ArrayList;

/**
 * Creator: Varun Barad
 * Date: 05-01-2018
 * Project: AttendanceTracker
 */
public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {
  private Context context;
  private ArrayList<Subject> subjects;
  
  public SubjectsAdapter(Context context, ArrayList<Subject> subjects) {
    this.context = context;
    this.subjects = subjects;
  }
  
  public void setSubjects(ArrayList<Subject> subjects) {
    this.subjects = subjects;
    this.notifyDataSetChanged();
  }
  
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ListItemSubjectBinding itemBinding =
        ListItemSubjectBinding.inflate(inflater, parent, false);
    
    return new SubjectsAdapter.ViewHolder(itemBinding);
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
    private ListItemSubjectBinding itemBinding;
    
    public ViewHolder(ListItemSubjectBinding itemBinding) {
      super(itemBinding.getRoot());
      
      this.itemBinding = itemBinding;
      
      this.itemBinding
          .getRoot()
          .setOnClickListener(this);
    }
    
    private void bind(Subject subject) {
      this.itemBinding
          .subjectName
          .setText(subject.getName());
    }
    
    @Override
    public void onClick(View view) {
      //ToDo: Handle click on subject
      SubjectDetailsActivity.start(context, subjects.get(getAdapterPosition()).getId());
    }
  }
}
