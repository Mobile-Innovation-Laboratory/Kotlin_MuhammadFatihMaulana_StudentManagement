package com.anomali.studentmanagement.data.remote.api

import com.anomali.studentmanagement.data.model.Subject
import com.anomali.studentmanagement.data.remote.dto.request.SubjectRequest
import com.anomali.studentmanagement.data.remote.dto.response.SubjectDTO
import com.anomali.studentmanagement.data.remote.dto.response.SubjectResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SubjectService {

    @GET("api/subjects")
    suspend fun getSubject(): Response<List<SubjectDTO>>

    @GET("api/subjects/{id}")
    suspend fun getSubjectById(@Path("id") id: Int): Response<SubjectDTO>

    @POST("api/subjects")
    suspend fun createSubject(@Body request: SubjectRequest): Response<SubjectResponseDTO>

    @PUT("api/subjects/{id}")
    suspend fun updateSubject(@Path("id") id: Int, @Body request: SubjectRequest): Response<SubjectResponseDTO>

    @DELETE("api/subjects/{id}")
    suspend fun deleteSubject(@Path("id") id: Int): Response<Unit>
}