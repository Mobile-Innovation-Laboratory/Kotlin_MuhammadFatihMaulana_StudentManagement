package com.anomali.studentmanagement.data.model.teacherRole

import com.anomali.studentmanagement.data.model.Schedule
import com.anomali.studentmanagement.data.model.student.Student

data class Attendance(
    val id: Int,
    val studentId: Int,
    val student: Student,
    val scheduleId: Int,
    val schedule: Schedule,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)
