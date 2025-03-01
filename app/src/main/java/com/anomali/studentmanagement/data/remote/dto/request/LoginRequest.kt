package com.anomali.studentmanagement.data.remote.dto.request

data class LoginRequest(
    val email: String,
    val password: String
)