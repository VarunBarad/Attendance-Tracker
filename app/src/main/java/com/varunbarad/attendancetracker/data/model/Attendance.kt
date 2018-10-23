package com.varunbarad.attendancetracker.data.model

import java.util.*

data class Attendance(
        val subjectId: Long,
        val classDate: Date,
        val attendanceStatus: Int
) {
    companion object {
        const val ATTEND = 0
        const val SKIP = 1
        const val CANCEL = 2
    }
}
