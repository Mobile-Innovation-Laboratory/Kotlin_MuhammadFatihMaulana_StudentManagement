package com.anomali.studentmanagement.data.remote.api.student

import com.anomali.studentmanagement.data.remote.dto.response.student.AttendanceDataListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.student.GradeDataListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.student.ScheduleDataListResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface StudentAcademicService {
    @GET("api/student/schedule")
    suspend fun getStudentSchedule(): Response<ScheduleDataListResponseDTO>

    @GET("api/student/grades")
    suspend fun getStudentGrade(): Response<GradeDataListResponseDTO>

    @GET("api/student/attendance")
    suspend fun getStudentAttendance(): Response<AttendanceDataListResponseDTO>
}