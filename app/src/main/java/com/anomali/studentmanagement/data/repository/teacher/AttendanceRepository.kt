package com.anomali.studentmanagement.data.repository.teacher

import android.content.Context
import com.anomali.studentmanagement.data.remote.dto.request.teacher.AttendanceRequest
import com.anomali.studentmanagement.data.remote.dto.response.teacher.AttendanceDTO
import com.anomali.studentmanagement.data.remote.dto.response.teacher.AttendanceListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.teacher.AttendanceResponseDTO
import com.anomali.studentmanagement.data.remote.network.RetrofitInstance
import retrofit2.Response
import javax.inject.Inject

interface AttendanceRepository {
    suspend fun getAttendance(): Response<AttendanceListResponseDTO>
    suspend fun getAttendanceById(id: Int): Response<AttendanceDTO>
    suspend fun createAttendance(request: AttendanceRequest): Response<AttendanceResponseDTO>
    suspend fun updateAttendance(
        id: Int,
        request: AttendanceRequest
    ): Response<AttendanceResponseDTO>

    suspend fun deleteAttendance(id: Int): Response<Unit>
}

class AttendanceRepositoryImpl @Inject constructor(
    private val context: Context
) : AttendanceRepository {
    init {
        RetrofitInstance.initRetrofit(context)
    }

    private val attendanceService = RetrofitInstance.getAttendanceService()

    override suspend fun getAttendance(): Response<AttendanceListResponseDTO> {
        try {
            val response = attendanceService.getAttendance()
            if (response.isSuccessful) {
                val attendance = response.body()
                return Response.success(attendance)
            } else {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }

    override suspend fun getAttendanceById(id: Int): Response<AttendanceDTO> {
        val response = attendanceService.getAttendanceById(id)
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun createAttendance(request: AttendanceRequest): Response<AttendanceResponseDTO> {
        val response = attendanceService.createAttendance(request)
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun updateAttendance(
        id: Int,
        request: AttendanceRequest
    ): Response<AttendanceResponseDTO> {
        val response = attendanceService.updateAttendance(id, request)
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun deleteAttendance(id: Int): Response<Unit> {
        return attendanceService.deleteAttendance(id)
    }
}