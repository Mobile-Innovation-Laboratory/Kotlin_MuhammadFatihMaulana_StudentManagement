package com.anomali.studentmanagement.data.mapper

import com.anomali.studentmanagement.data.model.Teacher
import com.anomali.studentmanagement.data.model.User
import com.anomali.studentmanagement.data.remote.dto.response.TeacherResponseDTO

fun TeacherResponseDTO.toModel(): Teacher {
    return Teacher(
        id = id,
        nip = nip,
        user = User(
            id = user.id,
            name = user.name,
            email = user.email,
            role = user.role,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        ),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}