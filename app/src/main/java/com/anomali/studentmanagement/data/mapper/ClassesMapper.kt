package com.anomali.studentmanagement.data.mapper

import com.anomali.studentmanagement.data.model.Classes
import com.anomali.studentmanagement.data.remote.dto.response.ClassesDTO
import com.anomali.studentmanagement.data.remote.dto.response.ClassesResponseDTO

fun ClassesResponseDTO.toModel(): Classes {
    return Classes(
        id = this.classes.id,
        name = this.classes.name,
        createdAt = this.classes.createdAt,
        updatedAt = this.classes.updatedAt
    )
}

fun ClassesDTO.toModel(): Classes {
    return Classes(
        id = this.id,
        name = this.name,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}