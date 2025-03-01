package com.anomali.studentmanagement.data.remote.dto.response.student

import com.anomali.studentmanagement.data.remote.dto.response.ScheduleDTO
import com.anomali.studentmanagement.data.remote.dto.response.StudentResponseDTO
import com.google.gson.annotations.SerializedName

data class AttendanceDataResponseDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("student") val student: StudentResponseDTO,
    @SerializedName("schedule_id") val scheduleId: Int,
    @SerializedName("schedule") val schedule: ScheduleDTO,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class AttendanceDataListResponseDTO(
    @SerializedName("attendances") val attendances: List<AttendanceDataResponseDTO>
)