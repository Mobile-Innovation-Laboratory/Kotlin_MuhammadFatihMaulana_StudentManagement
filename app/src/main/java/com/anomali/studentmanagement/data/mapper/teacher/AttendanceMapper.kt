package com.anomali.studentmanagement.data.mapper.teacher

import com.anomali.studentmanagement.data.mapper.toModel
import com.anomali.studentmanagement.data.model.teacherRole.Attendance
import com.anomali.studentmanagement.data.remote.dto.response.teacher.AttendanceDTO

fun AttendanceDTO.toModel(): Attendance {
    return Attendance(
        id = id,
        studentId = studentId,
        student = student.toModel(),
        scheduleId = scheduleId,
        schedule = schedule.toModel(),
        status = status,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}