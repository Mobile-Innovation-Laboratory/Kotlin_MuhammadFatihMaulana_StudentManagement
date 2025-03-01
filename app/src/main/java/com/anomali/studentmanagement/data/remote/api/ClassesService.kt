package com.anomali.studentmanagement.data.remote.api

import com.anomali.studentmanagement.data.remote.dto.request.ClassesRequest
import com.anomali.studentmanagement.data.remote.dto.response.ClassesDTO
import com.anomali.studentmanagement.data.remote.dto.response.ClassesResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClassesService {
    @GET("api/classes")
    suspend fun getClasses(): Response<List<ClassesDTO>>

    @GET("api/classes/{id}")
    suspend fun getClassById(@Path("id") id: Int): Response<ClassesDTO>

    @POST("api/classes")
    suspend fun createClass(@Body request: ClassesRequest): Response<ClassesResponseDTO>

    @PUT("api/classes/{id}")
    suspend fun updateClass(@Path("id") id: Int, @Body request: ClassesRequest): Response<ClassesResponseDTO>

    @DELETE("api/classes/{id}")
    suspend fun deleteClass(@Path("id") id: Int): Response<Unit>
}