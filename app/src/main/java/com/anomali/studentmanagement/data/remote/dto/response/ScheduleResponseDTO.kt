package com.anomali.studentmanagement.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class ScheduleResponseDTO(
    @SerializedName("message") val message: String,
    @SerializedName("schedule") val schedule: ScheduleCreateUpdateDTO
)

data class ScheduleListResponseDTO(
    @SerializedName("schedule") val schedule: List<ScheduleDTO>
)

data class ScheduleCreateUpdateDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("day") val day: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("class_id") val classId: Int,
    @SerializedName("teacher_id") val teacherId: Int,
    @SerializedName("subject_id") val subjectId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class ScheduleDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("day") val day: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("class_id") val classId: Int,
    @SerializedName("classes") val classes: ClassesDTO,
    @SerializedName("teacher_id") val teacherId: Int,
    @SerializedName("teacher") val teacher: TeacherResponseDTO,
    @SerializedName("subject_id") val subjectId: Int,
    @SerializedName("subject") val subject: SubjectDTO,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)