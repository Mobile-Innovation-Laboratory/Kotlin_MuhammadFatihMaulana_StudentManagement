package com.anomali.studentmanagement.data.model.studentRole

import com.anomali.studentmanagement.data.model.student.Student

data class StudentAttendance(
    val id: Int,
    val student: Student,
    val studentId: Int,
    val schedule: StudentSchedule,
    val scheduleId: Int,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)