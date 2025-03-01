package com.anomali.studentmanagement.data.model.studentRole

import com.anomali.studentmanagement.data.model.Classes
import com.anomali.studentmanagement.data.model.Subject
import com.anomali.studentmanagement.data.model.Teacher

data class StudentSchedule(
    val id: Int,
    val day: String,
    val startTime: String,
    val endTime: String,
    val classes: Classes,
    val classesId: Int,
    val teacher: Teacher,
    val teacherId: Int,
    val subject: Subject,
    val subjectId: Int,
    val createdAt: String,
    val updatedAt: String
)
