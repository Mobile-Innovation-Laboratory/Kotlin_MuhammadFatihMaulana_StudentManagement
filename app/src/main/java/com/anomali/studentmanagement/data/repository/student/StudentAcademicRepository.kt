package com.anomali.studentmanagement.data.repository.student

import android.content.Context
import com.anomali.studentmanagement.data.remote.dto.response.student.AttendanceDataListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.student.AttendanceDataResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.student.GradeDataListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.student.GradeDataResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.student.ScheduleDataListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.student.ScheduleDataResponseDTO
import com.anomali.studentmanagement.data.remote.network.RetrofitInstance
import retrofit2.Response
import javax.inject.Inject

interface StudentAcademicRepository {
    suspend fun getStudentAttendanceData(): Response<AttendanceDataListResponseDTO>
    suspend fun getStudentGradeData(): Response<GradeDataListResponseDTO>
    suspend fun getStudentScheduleData(): Response<ScheduleDataListResponseDTO>
}

class StudentAcademicRepositoryImpl @Inject constructor(
    private val context: Context
) : StudentAcademicRepository {
    init {
        RetrofitInstance.initRetrofit(context)
    }

    private val studentAcademicService = RetrofitInstance.getStudentAcademeService()

    override suspend fun getStudentAttendanceData(): Response<AttendanceDataListResponseDTO> {
        try {
            val response = studentAcademicService.getStudentAttendance()
            if (response.isSuccessful) {
                val attendanceData = response.body()
                return Response.success(attendanceData)
                } else {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }

    override suspend fun getStudentGradeData(): Response<GradeDataListResponseDTO> {
        try {
            val response = studentAcademicService.getStudentGrade()
            if (response.isSuccessful) {
                val gradeData = response.body()
                return Response.success(gradeData)
                } else {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }

    override suspend fun getStudentScheduleData(): Response<ScheduleDataListResponseDTO> {
        try {
            val response = studentAcademicService.getStudentSchedule()
            if (response.isSuccessful) {
                val scheduleData = response.body()
                return Response.success(scheduleData)
                } else {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }
}