package com.anomali.studentmanagement.data.mapper

import com.anomali.studentmanagement.data.local.entity.StudentEntity
import com.anomali.studentmanagement.data.model.Classes
import com.anomali.studentmanagement.data.model.User
import com.anomali.studentmanagement.data.model.student.Student
import com.anomali.studentmanagement.data.remote.dto.response.StudentListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.StudentResponseDTO

fun StudentResponseDTO.toModel(): Student {
    return Student(
        id = id,
        nis = nis,
        nisn = nisn,
        dateOfBirth = dateOfBirth,
        placeOfBirth = placeOfBirth,
        classId = classes.toModel(),
        gender = gender,
        user = user.toModel(),
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}


fun StudentListResponseDTO.toStudentEntity(): StudentEntity {
    return StudentEntity(
        id = this.id,
        nis = this.nis,
        nisn = this.nisn,
        dateOfBirth = this.dateOfBirth,
        placeOfBirth = this.placeOfBirth,
        gender = this.gender,
        classId = Classes(
            id = this.classes.id,
            name = this.classes.name,
            createdAt = this.classes.createdAt,
            updatedAt = this.classes.updatedAt
        ),
        user = User(
            id = this.user.id,
            name = this.user.name,
            email = this.user.email,
            role = this.user.role,
            createdAt = this.user.createdAt,
            updatedAt = this.user.updatedAt
        ),
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun StudentEntity.toModel(): Student {
    return Student(
        id = id,
        nis = nis,
        nisn = nisn,
        dateOfBirth = dateOfBirth,
        placeOfBirth = placeOfBirth,
        gender = gender,
        classId = classId,
        user = user,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
