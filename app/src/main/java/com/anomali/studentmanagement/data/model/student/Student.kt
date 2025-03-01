package com.anomali.studentmanagement.data.model.student

import com.anomali.studentmanagement.data.model.Classes
import com.anomali.studentmanagement.data.model.User

data class Student(
    val id: Int?,
    val nis: String,
    val nisn: String,
    val dateOfBirth: String,
    val placeOfBirth: String,
    val gender: String,
    val classId: Classes,
    val user: User,
    val createdAt: String,
    val updatedAt: String,
)