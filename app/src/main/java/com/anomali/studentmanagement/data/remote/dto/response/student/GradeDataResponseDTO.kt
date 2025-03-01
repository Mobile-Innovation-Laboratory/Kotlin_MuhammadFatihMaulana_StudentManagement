package com.anomali.studentmanagement.data.remote.dto.response.student

import com.anomali.studentmanagement.data.remote.dto.response.StudentResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.SubjectDTO
import com.anomali.studentmanagement.data.remote.dto.response.TeacherResponseDTO
import com.google.gson.annotations.SerializedName

data class GradeDataResponseDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("student") val student: StudentResponseDTO,
    @SerializedName("subject_id") val subjectId: Int,
    @SerializedName("subject") val subject: SubjectDTO,
    @SerializedName("teacher_id") val teacherId: Int,
    @SerializedName("teacher") val teacher: TeacherResponseDTO,
    @SerializedName("score") val score: Int,
    @SerializedName("remarks") val remarks: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class GradeDataListResponseDTO(
    @SerializedName("grades") val gradesData: List<GradeDataResponseDTO>

)