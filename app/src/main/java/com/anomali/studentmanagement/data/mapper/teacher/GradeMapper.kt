package com.anomali.studentmanagement.data.mapper.teacher

import com.anomali.studentmanagement.data.mapper.toModel
import com.anomali.studentmanagement.data.model.teacherRole.Grade
import com.anomali.studentmanagement.data.remote.dto.response.teacher.GradeDTO

fun GradeDTO.toModel(): Grade {
    return Grade(
        id = id,
        studentId = studentId,
        student = student.toModel(),
        subjectId = subjectId,
        subject = subject.toModel(),
        teacherId = teacherId,
        teacher = teacher.toModel(),
        score = score,
        remark = remark,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}