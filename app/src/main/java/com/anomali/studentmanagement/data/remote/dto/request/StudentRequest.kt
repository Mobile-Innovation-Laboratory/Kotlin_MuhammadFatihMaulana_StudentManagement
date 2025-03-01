package com.anomali.studentmanagement.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class StudentRequest(
    @SerializedName("nis") val nis: String,
    @SerializedName("nisn") val nisn: String,
    @SerializedName("class_id") val classId: Int,
    @SerializedName("date_of_birth") val dateOfBirth: String,
    @SerializedName("place_of_birth") val placeOfBirth: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("user") val user: UserRequest
)

data class UserRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
