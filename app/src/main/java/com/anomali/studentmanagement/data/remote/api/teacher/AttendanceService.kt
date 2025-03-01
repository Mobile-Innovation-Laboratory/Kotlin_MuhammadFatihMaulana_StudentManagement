package com.anomali.studentmanagement.data.remote.api.teacher

import com.anomali.studentmanagement.data.remote.dto.request.teacher.AttendanceRequest
import com.anomali.studentmanagement.data.remote.dto.response.teacher.AttendanceDTO
import com.anomali.studentmanagement.data.remote.dto.response.teacher.AttendanceListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.teacher.AttendanceResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AttendanceService {
    @GET("api/attendance")
    suspend fun getAttendance(): Response<AttendanceListResponseDTO>

    @GET("api/attendance/{id}")
    suspend fun getAttendanceById(@Path("id") id: Int): Response<AttendanceDTO>

    @POST("api/attendance")
    suspend fun createAttendance(@Body request: AttendanceRequest): Response<AttendanceResponseDTO>

    @PATCH("api/attendance/{id}")
    suspend fun updateAttendance(@Path("id") id: Int, @Body request: AttendanceRequest): Response<AttendanceResponseDTO>

    @DELETE("api/attendance/{id}")
    suspend fun deleteAttendance(@Path("id") id: Int): Response<Unit>
}