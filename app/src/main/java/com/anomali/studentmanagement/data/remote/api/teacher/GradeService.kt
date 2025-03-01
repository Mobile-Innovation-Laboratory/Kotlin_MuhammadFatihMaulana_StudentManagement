package com.anomali.studentmanagement.data.remote.api.teacher

import com.anomali.studentmanagement.data.remote.dto.request.teacher.GradeRequest
import com.anomali.studentmanagement.data.remote.dto.response.teacher.GradeDTO
import com.anomali.studentmanagement.data.remote.dto.response.teacher.GradeListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.teacher.GradeResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface GradeService {
    @GET("api/grades")
    suspend fun getGrades(): Response<GradeListResponseDTO>

    @GET("api/grades/{id}")
    suspend fun getGradeById(@Path("id") id: Int): Response<GradeDTO>

    @POST("api/grades")
    suspend fun createGrade(@Body request: GradeRequest): Response<GradeResponseDTO>

    @PATCH("api/grades/{id}")
    suspend fun updateGrade(@Path("id") id: Int, @Body request: GradeRequest): Response<GradeResponseDTO>

    @DELETE("api/grades/{id}")
    suspend fun deleteGrade(@Path("id") id: Int): Response<Unit>
}