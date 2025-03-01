package com.anomali.studentmanagement.data.repository.admin

import android.content.Context
import android.util.Log
import com.anomali.studentmanagement.data.mapper.toModel
import com.anomali.studentmanagement.data.model.Schedule
import com.anomali.studentmanagement.data.remote.dto.request.ScheduleRequest
import com.anomali.studentmanagement.data.remote.dto.response.ScheduleDTO
import com.anomali.studentmanagement.data.remote.dto.response.ScheduleListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.ScheduleResponseDTO
import com.anomali.studentmanagement.data.remote.network.RetrofitInstance
import retrofit2.Response
import javax.inject.Inject

interface ScheduleRepository {
    suspend fun getSchedules(): Response<ScheduleListResponseDTO>
    suspend fun getScheduleById(id: Int): Response<Schedule>
    suspend fun createSchedule(request: ScheduleRequest): Response<ScheduleResponseDTO>
    suspend fun updateSchedule(id: Int, request: ScheduleRequest): Response<ScheduleResponseDTO>
    suspend fun deleteSchedule(id: Int): Response<Unit>
}

class ScheduleRepositoryImpl @Inject constructor(
    private val context: Context
) : ScheduleRepository {
    init {
        RetrofitInstance.initRetrofit(context)
    }

    private val scheduleService = RetrofitInstance.getScheduleService()

    override suspend fun getSchedules(): Response<ScheduleListResponseDTO> {
        try {
            val response = scheduleService.getSchedules()
            Log.d("ScheduleResponse", "Schedule: ${response.body()}")
            if (response.isSuccessful) {
                val schedules = response.body()
                return Response.success(schedules)
            } else {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }

    override suspend fun getScheduleById(id: Int): Response<Schedule> {
        val response = scheduleService.getScheduleById(id)
        return if (response.isSuccessful) {
            Response.success(response.body()?.toModel())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun createSchedule(request: ScheduleRequest): Response<ScheduleResponseDTO> {
        val response = scheduleService.createSchedule(request)
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun updateSchedule(
        id: Int,
        request: ScheduleRequest
    ): Response<ScheduleResponseDTO> {
        val response = scheduleService.updateSchedule(id, request)
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun deleteSchedule(id: Int): Response<Unit> {
        return scheduleService.deleteSchedule(id)
    }
}