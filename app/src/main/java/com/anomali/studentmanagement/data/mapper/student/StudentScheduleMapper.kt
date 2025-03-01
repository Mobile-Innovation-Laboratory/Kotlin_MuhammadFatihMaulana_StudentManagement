package com.anomali.studentmanagement.data.mapper.student

import com.anomali.studentmanagement.data.mapper.toModel
import com.anomali.studentmanagement.data.model.studentRole.StudentSchedule
import com.anomali.studentmanagement.data.remote.dto.response.student.ScheduleDataResponseDTO

fun ScheduleDataResponseDTO.toModel(): StudentSchedule {
    return StudentSchedule(
        id = id,
        day = day,
        startTime = startTime,
        endTime = endTime,
        classes = classes.toModel(),
        classesId = classId,
        teacher = teacher.toModel(),
        teacherId = teacherId,
        subject = subject.toModel(),
        subjectId = subjectId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}