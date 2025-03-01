package com.anomali.studentmanagement.data.remote.api

import com.anomali.studentmanagement.data.remote.dto.request.ScheduleRequest
import com.anomali.studentmanagement.data.remote.dto.response.ScheduleDTO
import com.anomali.studentmanagement.data.remote.dto.response.ScheduleListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.ScheduleResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ScheduleService {
    @GET("api/schedules")
    suspend fun getSchedules(): Response<ScheduleListResponseDTO>

    @GET("api/schedules/{id}")
    suspend fun getScheduleById(@Path("id") id: Int): Response<ScheduleDTO>

    @POST("api/schedules")
    suspend fun createSchedule(@Body request: ScheduleRequest): Response<ScheduleResponseDTO>

    @PATCH("api/schedules/{id}")
    suspend fun updateSchedule(@Path("id") id: Int, @Body request: ScheduleRequest): Response<ScheduleResponseDTO>

    @DELETE("api/schedules/{id}")
    suspend fun deleteSchedule(@Path("id") id: Int): Response<Unit>
}