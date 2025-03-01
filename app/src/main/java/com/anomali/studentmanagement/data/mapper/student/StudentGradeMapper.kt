package com.anomali.studentmanagement.data.mapper.student

import com.anomali.studentmanagement.data.mapper.toModel
import com.anomali.studentmanagement.data.model.studentRole.StudentGrade
import com.anomali.studentmanagement.data.remote.dto.response.student.GradeDataResponseDTO

fun GradeDataResponseDTO.toModel(): StudentGrade {
    return StudentGrade(
        id = id,
        student = student.toModel(),
        studentId = studentId,
        subject = subject.toModel(),
        subjectId = subjectId,
        teacher = teacher.toModel(),
        teacherId = teacherId,
        score = score,
        remarks = remarks,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}