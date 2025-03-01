package com.anomali.studentmanagement.data.model

data class Teacher(
    val id: Int,
    val nip: String,
    val user: User?,
    val createdAt: String,
    val updatedAt: String
)
