package com.anomali.studentmanagement.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class ScheduleRequest(
    @SerializedName("class_id") val classId: Int,
    @SerializedName("teacher_id") val teacherId: Int,
    @SerializedName("subject_id") val subjectId: Int,
    @SerializedName("day") val day: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String
)