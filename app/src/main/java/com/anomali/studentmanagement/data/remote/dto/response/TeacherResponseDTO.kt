package com.anomali.studentmanagement.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class TeacherResponseDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("nip") val nip: String,
    @SerializedName("user") val user: UserDataDTO,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class UserDataDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("deleted_at") val deletedAt: String?
)

data class TeacherCreateResponseDTO(
    @SerializedName("message") val message: String,
    @SerializedName("data") val teacher: TeacherResponseDTO
)

data class TeacherListResponseDTO(
//    @SerializedName("message") val message: String,
    @SerializedName("data") val teachers: List<TeacherResponseDTO>
)