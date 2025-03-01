package com.anomali.studentmanagement.data.mapper

import com.anomali.studentmanagement.data.model.Classes
import com.anomali.studentmanagement.data.model.Schedule
import com.anomali.studentmanagement.data.model.Subject
import com.anomali.studentmanagement.data.model.Teacher
import com.anomali.studentmanagement.data.model.User
import com.anomali.studentmanagement.data.remote.dto.response.ScheduleDTO

fun ScheduleDTO.toModel(): Schedule {
    return Schedule(
        id = id,
        classId = classId,
        classes = Classes(
            id = classes.id,
            name = classes.name,
            createdAt = classes.createdAt,
            updatedAt = classes.updatedAt
        ),
        teacherId = teacherId,
        teacher = Teacher(
            id = teacher.id,
            nip = teacher.nip,
            createdAt = teacher.createdAt,
            updatedAt = teacher.updatedAt,
            user = User(
                id = teacher.user.id,
                name = teacher.user.name,
                email = teacher.user.email,
                createdAt = teacher.user.createdAt,
                role = teacher.user.role,
                updatedAt = teacher.user.updatedAt
            )
        ),
        subjectId = subjectId,
        subject = Subject(
            id = subject.id,
            name = subject.name,
            createdAt = subject.createdAt,
            updatedAt = subject.updatedAt
        ),
        day = day,
        startTime = startTime,
        endTime = endTime,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}