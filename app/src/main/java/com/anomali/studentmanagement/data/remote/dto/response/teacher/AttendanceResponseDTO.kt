package com.anomali.studentmanagement.data.remote.dto.response.teacher

import com.anomali.studentmanagement.data.remote.dto.response.ScheduleDTO
import com.anomali.studentmanagement.data.remote.dto.response.StudentResponseDTO
import com.google.gson.annotations.SerializedName

data class AttendanceDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("student") val student: StudentResponseDTO,
    @SerializedName("schedule_id") val scheduleId: Int,
    @SerializedName("schedule") val schedule: ScheduleDTO,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class AttendanceResponseDTO(
    @SerializedName("message") val message: String,
    @SerializedName("data") val attendance: AttendanceCreateUpdateDTO
)

data class AttendanceCreateUpdateDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("schedule_id") val scheduleId: Int,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class AttendanceListResponseDTO(
    @SerializedName("data") val attendances: List<AttendanceDTO>
)