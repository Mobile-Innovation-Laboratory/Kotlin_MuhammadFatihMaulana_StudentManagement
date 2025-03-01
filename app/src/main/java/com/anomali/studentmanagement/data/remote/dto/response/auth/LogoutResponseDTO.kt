package com.anomali.studentmanagement.data.remote.dto.response.auth

import com.google.gson.annotations.SerializedName

data class LogoutResponseDTO(
    @SerializedName("message") val message: String
)
