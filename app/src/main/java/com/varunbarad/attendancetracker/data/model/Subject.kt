package com.varunbarad.attendancetracker.data.model

import com.varunbarad.attendancetracker.util.Helper
import java.util.*

data class Subject @JvmOverloads constructor(
        val id: Long = -1,
        var name: String,
        var threshold: Int,
        var archived: Boolean = false,
        var attendances: ArrayList<Attendance> = arrayListOf()
) {
    fun getAttended(): List<Attendance> = this.attendances.filter { it.attendanceStatus == Attendance.ATTEND }

    fun getSkipped(): List<Attendance> = this.attendances.filter { it.attendanceStatus == Attendance.SKIP }

    fun getCancelled(): List<Attendance> = this.attendances.filter { it.attendanceStatus == Attendance.CANCEL }

    fun addAttendance(attendance: Attendance) {
        val existingAttendanceIndex = this.attendances.indexOfFirst { it.classDate == attendance.classDate }
        if (existingAttendanceIndex != -1) {
            this.attendances[existingAttendanceIndex] = attendance
        } else {
            this.attendances.add(attendance)
        }
    }

    fun getAttendanceOnDate(date: Date): Attendance? {
        return this.attendances.firstOrNull { it.classDate == Helper.stripTime(date) }
    }
}
