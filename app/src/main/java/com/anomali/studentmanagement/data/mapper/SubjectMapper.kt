package com.anomali.studentmanagement.data.mapper

import com.anomali.studentmanagement.data.model.Subject
import com.anomali.studentmanagement.data.remote.dto.response.SubjectDTO
import com.anomali.studentmanagement.data.remote.dto.response.SubjectResponseDTO

fun SubjectResponseDTO.toModel(): Subject {
    return Subject(
        id = this.subject.id,
        name = this.subject.name,
        createdAt = this.subject.createdAt,
        updatedAt = this.subject.updatedAt
    )
}

fun SubjectDTO.toModel(): Subject {
    return Subject(
        id = this.id,
        name = this.name,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}