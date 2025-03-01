package com.anomali.studentmanagement.data.model.teacherRole

import com.anomali.studentmanagement.data.model.Subject
import com.anomali.studentmanagement.data.model.Teacher
import com.anomali.studentmanagement.data.model.student.Student

data class Grade(
    val id: Int,
    val studentId: Int,
    val student: Student,
    val subjectId: Int,
    val subject: Subject,
    val teacherId: Int,
    val teacher: Teacher,
    val score: Int,
    val remark: String,
    val createdAt: String,
    val updatedAt: String
)
