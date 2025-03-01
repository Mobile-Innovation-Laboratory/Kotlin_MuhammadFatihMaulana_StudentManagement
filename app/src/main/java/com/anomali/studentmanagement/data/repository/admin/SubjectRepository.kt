package com.anomali.studentmanagement.data.repository.admin

import android.content.Context
import com.anomali.studentmanagement.data.mapper.toModel
import com.anomali.studentmanagement.data.model.Subject
import com.anomali.studentmanagement.data.remote.dto.request.SubjectRequest
import com.anomali.studentmanagement.data.remote.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

interface SubjectRepository {
    suspend fun getSubjects(): Response<List<Subject>>
    suspend fun getSubjectById(id: Int): Response<Subject>
    suspend fun createSubject(name: String): Response<Subject>
    suspend fun updateSubject(id: Int, name: String): Response<Subject>
    suspend fun deleteSubject(id: Int): Response<Unit>
}

class SubjectRepositoryImpl @Inject constructor(
    private val context: Context,
) : SubjectRepository {
    init {
        RetrofitInstance.initRetrofit(context)
    }

    private val subjectService = RetrofitInstance.getSubjectService()

    override suspend fun getSubjects(): Response<List<Subject>> {
        try {
            val response = subjectService.getSubject()
            if (response.isSuccessful) {
                val subjects = response.body()?.map {
                    it.toModel()
                }
                return Response.success(subjects)
            } else {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }

    override suspend fun getSubjectById(id: Int): Response<Subject> {
        try {
            val response = subjectService.getSubjectById(id)
            return if (response.isSuccessful) {
                Response.success(response.body()?.toModel())
            } else {
                Response.error(response.code(), response.errorBody()!!)
            }
        } catch (e: Exception) {
            return Response.error(500, "Error: ${e.message}".toResponseBody(null))
        }
    }

    override suspend fun createSubject(name: String): Response<Subject> {
        return withContext(Dispatchers.IO) {
            try {
                val request = SubjectRequest(name)
                val response = subjectService.createSubject(request)
                if (response.isSuccessful) {
                    Response.success(response.body()?.toModel())
                } else {
                    Response.error(response.code(), response.errorBody()!!)
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
                Response.error(500, "Error: ${e.message}".toResponseBody(null))
            }
        }
    }

    override suspend fun updateSubject(id: Int, name: String): Response<Subject> {
        val request = SubjectRequest(name)
        val response = subjectService.updateSubject(id, request)
        return if (response.isSuccessful) {
            Response.success(response.body()!!.toModel())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun deleteSubject(id: Int): Response<Unit> {
        return subjectService.deleteSubject(id)
    }
}