package com.anomali.studentmanagement.data.remote.dto.response.student

import com.anomali.studentmanagement.data.remote.dto.response.ClassesDTO
import com.anomali.studentmanagement.data.remote.dto.response.SubjectDTO
import com.anomali.studentmanagement.data.remote.dto.response.TeacherResponseDTO
import com.google.gson.annotations.SerializedName

data class ScheduleDataResponseDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("class_id") val classId: Int,
    @SerializedName("teacher_id") val teacherId: Int,
    @SerializedName("subject_id") val subjectId: Int,
    @SerializedName("day") val day: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("classes") val classes: ClassesDTO,
    @SerializedName("teacher") val teacher: TeacherResponseDTO,
    @SerializedName("subject") val subject: SubjectDTO,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class ScheduleDataListResponseDTO(
    @SerializedName("schedules") val schedules: List<ScheduleDataResponseDTO>
)