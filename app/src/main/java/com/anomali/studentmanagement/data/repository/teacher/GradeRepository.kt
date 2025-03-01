package com.anomali.studentmanagement.data.repository.teacher

import android.content.Context
import com.anomali.studentmanagement.data.remote.dto.request.teacher.GradeRequest
import com.anomali.studentmanagement.data.remote.dto.response.teacher.GradeDTO
import com.anomali.studentmanagement.data.remote.dto.response.teacher.GradeListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.teacher.GradeResponseDTO
import com.anomali.studentmanagement.data.remote.network.RetrofitInstance
import retrofit2.Response
import javax.inject.Inject

interface GradeRepository {
    suspend fun getGrades(): Response<GradeListResponseDTO>
    suspend fun getGradeById(id: Int): Response<GradeDTO>
    suspend fun createGrade(request: GradeRequest): Response<GradeResponseDTO>
    suspend fun updateGrade(id: Int, request: GradeRequest): Response<GradeResponseDTO>
    suspend fun deleteGrade(id: Int): Response<Unit>
}

class GradeRepositoryImpl @Inject constructor(
    private val context: Context
) : GradeRepository {
    init {
        RetrofitInstance.initRetrofit(context)
    }

    private val gradeService = RetrofitInstance.getGradeService()

    override suspend fun getGrades(): Response<GradeListResponseDTO> {
        try {
            val response = gradeService.getGrades()
            if (response.isSuccessful) {
                val grades = response.body()
                return Response.success(grades)
            } else {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }

    override suspend fun getGradeById(id: Int): Response<GradeDTO> {
        val response = gradeService.getGradeById(id)
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun createGrade(request: GradeRequest): Response<GradeResponseDTO> {
        val response = gradeService.createGrade(request)
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun updateGrade(id: Int, request: GradeRequest): Response<GradeResponseDTO> {
        val response = gradeService.updateGrade(id, request)
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun deleteGrade(id: Int): Response<Unit> {
        return gradeService.deleteGrade(id)
    }
}