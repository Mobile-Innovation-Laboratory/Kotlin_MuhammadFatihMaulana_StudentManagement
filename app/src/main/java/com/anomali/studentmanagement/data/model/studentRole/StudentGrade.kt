package com.anomali.studentmanagement.data.model.studentRole

import com.anomali.studentmanagement.data.model.Subject
import com.anomali.studentmanagement.data.model.Teacher
import com.anomali.studentmanagement.data.model.student.Student

data class StudentGrade(
    val id: Int,
    val student: Student,
    val studentId: Int,
    val subject: Subject,
    val subjectId: Int,
    val teacher: Teacher,
    val teacherId: Int,
    val score: Int,
    val remarks: String?,
    val createdAt: String,
    val updatedAt: String,
)
