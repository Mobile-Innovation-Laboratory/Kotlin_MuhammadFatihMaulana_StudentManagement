package com.anomali.studentmanagement.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class TeacherRequest(
    val user: UserDataRequest,
    @SerializedName("nip") val nip: String,
)

data class UserDataRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
