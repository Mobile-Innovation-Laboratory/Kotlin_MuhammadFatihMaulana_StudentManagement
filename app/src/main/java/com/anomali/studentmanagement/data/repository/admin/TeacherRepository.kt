package com.anomali.studentmanagement.data.repository.admin

import android.content.Context
import com.anomali.studentmanagement.data.mapper.toModel
import com.anomali.studentmanagement.data.model.Teacher
import com.anomali.studentmanagement.data.remote.dto.request.TeacherRequest
import com.anomali.studentmanagement.data.remote.dto.response.TeacherCreateResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.TeacherListResponseDTO
import com.anomali.studentmanagement.data.remote.network.RetrofitInstance
import retrofit2.Response
import javax.inject.Inject

interface TeacherRepository {
    suspend fun getTeachers(): Response<TeacherListResponseDTO>
    suspend fun getTeacherById(id: Int): Response<Teacher>
    suspend fun createTeacher(request: TeacherRequest): Response<TeacherCreateResponseDTO>
    suspend fun updateTeacher(id: Int, request: TeacherRequest): Response<TeacherCreateResponseDTO>
    suspend fun deleteTeacher(id: Int): Response<Unit>
}

class TeacherRepositoryImpl @Inject constructor(
    context: Context
) : TeacherRepository {
    init {
        RetrofitInstance.initRetrofit(context)
    }

    private val teacherService = RetrofitInstance.getTeacherService()

    override suspend fun getTeachers(): Response<TeacherListResponseDTO> {
        try {
            val response = teacherService.getTeachers()
            if (response.isSuccessful) {
                val subjects = response.body()
                return Response.success(subjects)
            } else {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }

    override suspend fun getTeacherById(id: Int): Response<Teacher> {
        val response = teacherService.getTeacherById(id)
        return if (response.isSuccessful) {
            Response.success(response.body()?.toModel())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun createTeacher(request: TeacherRequest): Response<TeacherCreateResponseDTO> {
        val response = teacherService.createTeacher(request)
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun updateTeacher(
        id: Int,
        request: TeacherRequest
    ): Response<TeacherCreateResponseDTO> {
        val response = teacherService.updateTeacher(id, request)
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun deleteTeacher(id: Int): Response<Unit> {
        return teacherService.deleteTeacher(id)
    }
}