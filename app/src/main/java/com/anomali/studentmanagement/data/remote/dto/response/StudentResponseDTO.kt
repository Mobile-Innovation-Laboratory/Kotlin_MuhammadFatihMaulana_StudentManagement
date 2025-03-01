package com.anomali.studentmanagement.data.remote.dto.response

import com.google.gson.annotations.SerializedName

//Di sini name nya harus sama ama respon di JSON nya
data class StudentCreateResponse(
    @SerializedName("message") val message: String,
    @SerializedName("student") val student: StudentResponseDTO,
    @SerializedName("user") val user: UserDTO
)

data class StudentResponseDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("user") val user: UserDTO,
    @SerializedName("nis") val nis: String,
    @SerializedName("nisn") val nisn: String,
    @SerializedName("date_of_birth") val dateOfBirth: String,
    @SerializedName("place_of_birth") val placeOfBirth: String,
    @SerializedName("class_id") val classId: Int,
    @SerializedName("classes") val classes: ClassesDTO,
    @SerializedName("gender") val gender: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class StudentListResponseDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("user") val user: UserDTO,
    @SerializedName("nis") val nis: String,
    @SerializedName("nisn") val nisn: String,
    @SerializedName("date_of_birth") val dateOfBirth: String,
    @SerializedName("place_of_birth") val placeOfBirth: String,
    @SerializedName("class_id") val classId: Int,
    @SerializedName("classes") val classes: ClassesDTO,
    @SerializedName("gender") val gender: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class StudentListResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val students: List<StudentListResponseDTO>,
)

data class StudentsListResponse(
    @SerializedName("data") val students: List<StudentListResponseDTO>,
)
