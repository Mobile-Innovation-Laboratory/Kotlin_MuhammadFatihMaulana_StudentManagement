package com.anomali.studentmanagement.data.remote.dto.request.teacher

import com.google.gson.annotations.SerializedName

data class AttendanceRequest(
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("schedule_id") val scheduleId: Int,
    @SerializedName("status") val status: String
)
