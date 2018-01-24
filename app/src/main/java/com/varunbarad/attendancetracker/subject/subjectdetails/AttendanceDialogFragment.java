package com.varunbarad.attendancetracker.subject.subjectdetails;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.varunbarad.attendancetracker.R;
import com.varunbarad.attendancetracker.data.DatabaseHelper;
import com.varunbarad.attendancetracker.data.model.Attendance;
import com.varunbarad.attendancetracker.data.model.Subject;
import com.varunbarad.attendancetracker.util.Helper;

import java.util.Arrays;
import java.util.Date;

/**
 * Creator: Varun Barad
 * Date: 24-01-2018
 * Project: AttendanceTracker
 */
public class AttendanceDialogFragment extends AppCompatDialogFragment {
  private static final String KEY_SUBJECT_ID = "subject_id";
  private static final String KEY_DATE = "date";
  
  private Subject subject;
  private Date date;
  
  public AttendanceDialogFragment() {
  }
  
  public static AttendanceDialogFragment getInstance(Subject subject, Date date) {
    AttendanceDialogFragment fragment = new AttendanceDialogFragment();
    
    Bundle arguments = new Bundle();
    arguments.putLong(KEY_SUBJECT_ID, subject.getId());
    arguments.putString(KEY_DATE, Helper.serializeDate(date));
    fragment.setArguments(arguments);
    
    return fragment;
  }
  
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Context context = this.getContext();
    
    this.subject = DatabaseHelper.getSingleSubject(context, this.getArguments().getLong(KEY_SUBJECT_ID));
    this.date = Helper.deserializeDate(this.getArguments().getString(KEY_DATE));
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
    
    Attendance existingAttendance = this.subject.getAttendanceOnDate(date);
    
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
    MaterialDialog attendanceDialog = new MaterialDialog.Builder(context)
        .title(R.string.label_setAttendance)
        .content(R.string.message_setEditAttendance, Helper.formatDateForUser(date))
        .negativeText(R.string.label_removeAttendance)
        .onNegative(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            Attendance attendanceToDelete = new Attendance(
                AttendanceDialogFragment.this.subject.getId(),
                Helper.stripTime(date),
                -1
            );
            DatabaseHelper.deleteAttendance(context, attendanceToDelete);
            
            AttendanceDialogFragment
                .this
                .dismiss();
          }
        })
        .items(Arrays.asList(attendanceLabels))
        .itemsCallbackSingleChoice(existingIndex, new MaterialDialog.ListCallbackSingleChoice() {
          @Override
          public boolean onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
            Attendance newAttendance = new Attendance(
                AttendanceDialogFragment.this.subject.getId(),
                Helper.stripTime(date),
                attendanceValues[position]
            );
            DatabaseHelper.addAttendance(context, newAttendance);
            
            AttendanceDialogFragment
                .this
                .dismiss();
            
            return true;
          }
        })
        .build();
    
    return attendanceDialog;
  }
  
  @Override
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    if (this.getActivity() instanceof SubjectDetailsActivity) {
      SubjectDetailsActivity activity = (SubjectDetailsActivity) this.getActivity();
      activity.refreshData();
    }
  }
}
