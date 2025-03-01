package com.anomali.studentmanagement.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anomali.studentmanagement.data.model.Classes
import com.anomali.studentmanagement.data.model.User

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nis: String,
    val nisn: String,
    val dateOfBirth: String,
    val placeOfBirth: String,
    val gender: String,
    val classId: Classes,
    val user: User,
    val createdAt: String,
    val updatedAt: String,
    var isFavorite: Boolean = false
)
