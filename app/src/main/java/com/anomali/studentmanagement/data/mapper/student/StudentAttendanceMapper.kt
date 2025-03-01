package com.anomali.studentmanagement.data.mapper.student

import com.anomali.studentmanagement.data.mapper.toModel
import com.anomali.studentmanagement.data.model.studentRole.StudentAttendance
import com.anomali.studentmanagement.data.model.studentRole.StudentSchedule
import com.anomali.studentmanagement.data.remote.dto.response.student.AttendanceDataResponseDTO

fun AttendanceDataResponseDTO.toModel(): StudentAttendance {
    return StudentAttendance(
        id = id,
        student = student.toModel(),
        studentId = studentId,
        schedule = StudentSchedule(
            id = schedule.id,
            day = schedule.day,
            startTime = schedule.startTime,
            endTime = schedule.endTime,
            classes = schedule.classes.toModel(),
            classesId = schedule.classId,
            teacher = schedule.teacher.toModel(),
            teacherId = schedule.teacherId,
            subject = schedule.subject.toModel(),
            subjectId = schedule.subjectId,
            createdAt = schedule.createdAt,
            updatedAt = schedule.updatedAt
        ),
        scheduleId = scheduleId,
        status = status,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}