package com.varunbarad.attendancetracker.widget.subjectselect;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.varunbarad.attendancetracker.data.model.Subject;
import com.varunbarad.attendancetracker.databinding.ListItemSubjectBinding;
import com.varunbarad.attendancetracker.util.eventlistener.ListItemClickListener;

import java.util.ArrayList;

/**
 * Creator: Varun Barad
 * Date: 09-01-2018
 * Project: AttendanceTracker
 */
public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> implements ListItemClickListener {
  private ArrayList<Subject> subjects;
  private ListItemClickListener listItemClickListener;
  
  public SubjectsAdapter(ArrayList<Subject> subjects, ListItemClickListener listItemClickListener) {
    this.subjects = subjects;
    this.listItemClickListener = listItemClickListener;
  }
  
  public ArrayList<Subject> getSubjects() {
    return this.subjects;
  }
  
  public void setSubjects(ArrayList<Subject> subjects) {
    this.subjects = subjects;
    this.notifyDataSetChanged();
  }
  
  @Override
  public SubjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ListItemSubjectBinding itemBinding =
        ListItemSubjectBinding.inflate(inflater, parent, false);
    
    return new SubjectsAdapter.ViewHolder(itemBinding);
  }
  
  @Override
  public void onBindViewHolder(SubjectsAdapter.ViewHolder holder, int position) {
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
  
  @Override
  public void onListItemClicked(int position, String data) {
    this.listItemClickListener
        .onListItemClicked(position, data);
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
      onListItemClicked(this.getAdapterPosition(), null);
    }
  }
}
