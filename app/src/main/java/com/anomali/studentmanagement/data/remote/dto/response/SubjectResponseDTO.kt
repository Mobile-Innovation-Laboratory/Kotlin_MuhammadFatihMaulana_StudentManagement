package com.anomali.studentmanagement.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class SubjectResponseDTO(
    @SerializedName("message") val message: String,
    @SerializedName("subject") val subject: SubjectDTO
)

data class SubjectDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)
