package com.anomali.studentmanagement.data.repository.admin

import android.content.Context
import com.anomali.studentmanagement.data.mapper.toModel
import com.anomali.studentmanagement.data.model.Classes
import com.anomali.studentmanagement.data.remote.dto.request.ClassesRequest
import com.anomali.studentmanagement.data.remote.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

interface ClassesRepository {
    suspend fun getClasses(): Response<List<Classes>>
    suspend fun getClassById(id: Int): Response<Classes>
    suspend fun createClass(name: String): Response<Classes>
    suspend fun updateClass(id: Int, name: String): Response<Classes>
    suspend fun deleteClass(id: Int): Response<Unit>
}

class ClassesRepositoryImpl @Inject constructor(
    context: Context
) : ClassesRepository {
    init {
        RetrofitInstance.initRetrofit(context)
    }

    private val classesService = RetrofitInstance.getClassService()

    override suspend fun getClasses(): Response<List<Classes>> {
        try {
            val response = classesService.getClasses()
            if (response.isSuccessful) {
                val classes = response.body()?.map {
                    it.toModel()
                }
                return Response.success(classes)
            } else {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }

    override suspend fun getClassById(id: Int): Response<Classes> {
        try {
            val response = classesService.getClassById(id)
            return if (response.isSuccessful) {
                Response.success(response.body()?.toModel())
            } else {
                Response.error(response.code(), response.errorBody()!!)
            }
        } catch (e: Exception) {
            return Response.error(500, "Error: ${e.message}".toResponseBody(null))
        }
    }

    override suspend fun createClass(name: String): Response<Classes> {
        return withContext(Dispatchers.IO) {
            try {
                val request = ClassesRequest(name)
                val response = classesService.createClass(request)
                if (response.isSuccessful) {
                    Response.success(response.body()?.toModel())
                } else {
                    Response.error(response.code(), response.errorBody()!!)
                }
            } catch (e: Exception) {
                Response.error(500, "Error: ${e.message}".toResponseBody(null))
            }
        }
    }

    override suspend fun updateClass(id: Int, name: String): Response<Classes> {
        try {
            val request = ClassesRequest(name)
            val response = classesService.updateClass(id, request)
            return if (response.isSuccessful) {
                Response.success(response.body()?.toModel())
            } else {
                Response.error(response.code(), response.errorBody()!!)
            }
        } catch (e: Exception) {
            return Response.error(500, "Error: ${e.message}".toResponseBody(null))
        }
    }

    override suspend fun deleteClass(id: Int): Response<Unit> {
        return  classesService.deleteClass(id)
    }



}

