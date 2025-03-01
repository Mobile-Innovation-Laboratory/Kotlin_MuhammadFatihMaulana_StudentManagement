package com.anomali.studentmanagement.data.remote.dto.response.teacher

import com.anomali.studentmanagement.data.remote.dto.response.StudentResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.SubjectDTO
import com.anomali.studentmanagement.data.remote.dto.response.TeacherResponseDTO
import com.google.gson.annotations.SerializedName

data class GradeDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("student") val student: StudentResponseDTO,
    @SerializedName("subject_id") val subjectId: Int,
    @SerializedName("subject") val subject: SubjectDTO,
    @SerializedName("teacher_id") val teacherId: Int,
    @SerializedName("teacher") val teacher: TeacherResponseDTO,
    @SerializedName("score") val score: Int,
    @SerializedName("remark") val remark: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class GradeCreateUpdateDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("subject_id") val subjectId: Int,
    @SerializedName("teacher_id") val teacherId: Int,
    @SerializedName("score") val score: Int,
    @SerializedName("remark") val remark: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class GradeResponseDTO(
    @SerializedName("message") val message: String,
    @SerializedName("data") val grade: GradeCreateUpdateDTO
)

data class GradeListResponseDTO(
    @SerializedName("data") val grades: List<GradeDTO>
)