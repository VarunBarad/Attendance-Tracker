package com.varunbarad.attendancetracker.subject.subjectdetails;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.stacktips.view.CalendarListener;
import com.stacktips.view.DayDecorator;
import com.varunbarad.attendancetracker.R;
import com.varunbarad.attendancetracker.data.DatabaseHelper;
import com.varunbarad.attendancetracker.data.model.Attendance;
import com.varunbarad.attendancetracker.data.model.Subject;
import com.varunbarad.attendancetracker.databinding.ActivitySubjectDetailsBinding;
import com.varunbarad.attendancetracker.subject.editsubject.EditSubjectActivity;
import com.varunbarad.attendancetracker.util.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SubjectDetailsActivity extends AppCompatActivity {
  private static final String KEY_SUBJECT_ID = "subject_id";
  private ActivitySubjectDetailsBinding dataBinding;
  private Subject subject;
  private Menu menu;
  
  public static void start(Context context, long subjectId) {
    Intent starter = new Intent(context, SubjectDetailsActivity.class);
    starter.putExtra(KEY_SUBJECT_ID, subjectId);
    context.startActivity(starter);
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_subject_details);
  
    this.setSupportActionBar(this.dataBinding.toolbar);
    this.getSupportActionBar()
        .setDisplayHomeAsUpEnabled(true);
  }
  
  @Override
  protected void onStart() {
    super.onStart();
  
    this.refreshData();
  
    this.dataBinding
        .calendar
        .setCalendarListener(new CalendarListener() {
          @Override
          public void onDateSelected(Date date) {
            if (Helper.isDateInFuture(Helper.stripTime(date), Helper.stripTime(new Date()))) {
              Snackbar
                  .make(
                      SubjectDetailsActivity.this.dataBinding.getRoot(),
                      R.string.message_futureDate,
                      Snackbar.LENGTH_SHORT
                  ).show();
            } else {
              SubjectDetailsActivity
                  .this
                  .showSetEditAttendanceDialogForDate(date);
            }
          }
        
          @Override
          public void onMonthChanged(Date date) {
          
          }
        });
  }
  
  private void showSetEditAttendanceDialogForDate(final Date date) {
    final Context context = this;
    String[] attendanceLabels = new String[]{
        context.getString(R.string.label_attend),
        context.getString(R.string.label_skip),
        context.getString(R.string.label_cancel)
    };
    final int[] attendanceValues = new int[]{
        Attendance.ATTEND,
        Attendance.SKIP,
        Attendance.CANCEL
    };
    
    Attendance existingAttendance = SubjectDetailsActivity.this.subject.getAttendanceOnDate(date);
    
    int existingIndex = -1;
    if (existingAttendance != null) {
      int existingStatus = existingAttendance.getAttendanceStatus();
      for (int i = 0; i < attendanceValues.length; i++) {
        if (attendanceValues[i] == existingStatus) {
          existingIndex = i;
          break;
        }
      }
    }
    MaterialDialog attendanceDialog = new MaterialDialog.Builder(SubjectDetailsActivity.this)
        .title(R.string.label_setAttendance)
        .content(R.string.message_setEditAttendance, Helper.formatDateForUser(date))
        .negativeText(R.string.label_removeAttendance)
        .onNegative(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            Attendance attendanceToDelete = new Attendance(
                SubjectDetailsActivity.this.subject.getId(),
                Helper.stripTime(date),
                -1
            );
            DatabaseHelper.deleteAttendance(context, attendanceToDelete);
        
            SubjectDetailsActivity
                .this
                .refreshData();
          }
        })
        .items(Arrays.asList(attendanceLabels))
        .itemsCallbackSingleChoice(existingIndex, new MaterialDialog.ListCallbackSingleChoice() {
          @Override
          public boolean onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
            Attendance newAttendance = new Attendance(
                SubjectDetailsActivity.this.subject.getId(),
                Helper.stripTime(date),
                attendanceValues[position]
            );
            DatabaseHelper.addAttendance(context, newAttendance);
            
            SubjectDetailsActivity
                .this
                .refreshData();
            
            return true;
          }
        })
        .build();
    
    attendanceDialog.show();
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    this
        .getMenuInflater()
        .inflate(R.menu.subject_details, menu);
    this.menu = menu;
  
    if (this.subject.isArchived()) {
      Drawable iconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_unarchive);
      this.menu.findItem(R.id.action_archive).setIcon(iconDrawable);
    }
    
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      this.onBackPressed();
    } else if (id == R.id.action_edit) {
      EditSubjectActivity.start(this, this.subject.getId());
    } else if (id == R.id.action_archive) {
      this.toggleArchive();
    } else {
      return super.onOptionsItemSelected(item);
    }
    
    return true;
  }
  
  private long getSubjectIdPassedToActivity() {
    return this.getIntent().getLongExtra(KEY_SUBJECT_ID, -1);
  }
  
  private void toggleArchive() {
    Drawable iconDrawable;
    if (this.subject.isArchived()) {
      this.subject.setArchived(false);
      
      iconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_archive);
    } else {
      this.subject.setArchived(true);
      
      iconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_unarchive);
    }
    
    this.menu.findItem(R.id.action_archive).setIcon(iconDrawable);
    DatabaseHelper.editSubject(this, this.subject);
  }
  
  private void refreshData() {
    this.subject = DatabaseHelper.getSingleSubject(this, this.getSubjectIdPassedToActivity());
    
    this.getSupportActionBar()
        .setTitle(this.subject.getName());
    
    //ToDo: Extract this format to strings.xml
    this.dataBinding
        .valueThreshold
        .setText(String.format(Locale.getDefault(), "%d%% Required", this.subject.getThreshold()));
    
    this.dataBinding
        .valueAttended
        .setText(String.valueOf(this.subject.getAttended().size()));
    
    this.dataBinding
        .valueSkipped
        .setText(String.valueOf(this.subject.getSkipped().size()));
    
    this.dataBinding
        .valueCancelled
        .setText(String.valueOf(this.subject.getCancelled().size()));
    
    {
      ArrayList<Attendance> attended = this.subject.getAttended();
      ArrayList<Date> attendedDates = new ArrayList<>(attended.size());
      for (Attendance a : attended) {
        attendedDates.add(a.getClassDate());
      }
      ArrayList<Attendance> skipped = this.subject.getSkipped();
      ArrayList<Date> skippedDates = new ArrayList<>(skipped.size());
      for (Attendance a : skipped) {
        skippedDates.add(a.getClassDate());
      }
      ArrayList<Attendance> cancelled = this.subject.getCancelled();
      ArrayList<Date> cancelledDates = new ArrayList<>(cancelled.size());
      for (Attendance a : cancelled) {
        cancelledDates.add(a.getClassDate());
      }
      List<DayDecorator> dayDecorators = new ArrayList<>(3);
      dayDecorators.add(new CalendarDayDecorator(this, attendedDates, R.color.colorAttend));
      dayDecorators.add(new CalendarDayDecorator(this, skippedDates, R.color.colorSkip));
      dayDecorators.add(new CalendarDayDecorator(this, cancelledDates, R.color.colorCancel));
      
      this.dataBinding
          .calendar
          .setDecorators(dayDecorators);
    }
    
    this.dataBinding
        .calendar
        .refreshCalendar(Calendar.getInstance(Locale.getDefault()));
  }
}
