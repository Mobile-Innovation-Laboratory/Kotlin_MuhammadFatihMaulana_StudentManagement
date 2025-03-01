package com.anomali.studentmanagement.data.model

data class Schedule(
    val id: Int,
    val classId: Int,
    val classes: Classes,
    val teacher: Teacher,
    val teacherId: Int,
    val subject: Subject,
    val subjectId: Int,
    val day: String,
    val startTime: String,
    val endTime: String,
    val createdAt: String,
    val updatedAt: String
)
