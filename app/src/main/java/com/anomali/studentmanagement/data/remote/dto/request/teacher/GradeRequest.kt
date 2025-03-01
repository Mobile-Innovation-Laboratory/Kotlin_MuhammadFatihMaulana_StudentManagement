package com.anomali.studentmanagement.data.remote.dto.request.teacher

data class GradeRequest(
    val studentId: Int,
    val subjectId: Int,
    val teacherId: Int,
    val score: Int,
    val remark: String
)
