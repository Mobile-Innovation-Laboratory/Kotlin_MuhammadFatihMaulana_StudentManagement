package com.anomali.studentmanagement.data.remote.api

import com.anomali.studentmanagement.data.remote.dto.request.TeacherRequest
import com.anomali.studentmanagement.data.remote.dto.response.TeacherCreateResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.TeacherListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.TeacherResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TeacherService {
    @GET("api/teachers")
    suspend fun getTeachers(): Response<TeacherListResponseDTO>

    @GET("api/teachers/{id}")
    suspend fun getTeacherById(@Path("id") id: Int): Response<TeacherResponseDTO>

    @POST("api/teachers")
    suspend fun createTeacher(@Body request: TeacherRequest): Response<TeacherCreateResponseDTO>

    @PATCH("api/teachers/{id}")
    suspend fun updateTeacher(@Path("id") id: Int, @Body request: TeacherRequest): Response<TeacherCreateResponseDTO>

    @DELETE("api/teachers/{id}")
    suspend fun deleteTeacher(@Path("id") id: Int): Response<Unit>
}