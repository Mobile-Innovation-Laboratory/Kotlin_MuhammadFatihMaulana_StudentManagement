package com.anomali.studentmanagement.data.remote.api

import com.anomali.studentmanagement.data.remote.dto.request.LoginRequest
import com.anomali.studentmanagement.data.remote.dto.request.RegisterRequest
import com.anomali.studentmanagement.data.remote.dto.response.auth.LoginResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.auth.LogoutResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.auth.RegisterResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponseDTO>

    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponseDTO>

    @GET("api/user")
    suspend fun getUser(@Header("Authorization") token: String): Response<UserDTO>

    @POST("api/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<LogoutResponseDTO>
}