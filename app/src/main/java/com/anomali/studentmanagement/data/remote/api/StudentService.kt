package com.anomali.studentmanagement.data.remote.api

import com.anomali.studentmanagement.data.remote.dto.request.StudentRequest
import com.anomali.studentmanagement.data.remote.dto.response.StudentCreateResponse
import com.anomali.studentmanagement.data.remote.dto.response.StudentResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.StudentsListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface StudentService {

    @GET("api/students")
    suspend fun getStudents(): Response<StudentsListResponse>

    @GET("api/students/{id}")
    suspend fun getStudentById(@Path("id") id: Int): Response<StudentResponseDTO>

    @POST("api/students")
    suspend fun createStudent(@Body request: StudentRequest): Response<StudentCreateResponse>

    @PATCH("api/students/{id}")
    suspend fun updateStudent(@Path("id") id: Int, @Body request: StudentRequest): Response<StudentCreateResponse>

    @DELETE("api/students/{id}")
    suspend fun deleteStudent(@Path("id") id: Int): Response<Unit>
}