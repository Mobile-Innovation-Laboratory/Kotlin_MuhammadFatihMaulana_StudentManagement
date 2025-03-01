package com.anomali.studentmanagement.data.repository.admin

import android.content.Context
import android.util.Log
import com.anomali.studentmanagement.data.local.dao.StudentDao
import com.anomali.studentmanagement.data.mapper.toModel
import com.anomali.studentmanagement.data.mapper.toStudentEntity
import com.anomali.studentmanagement.data.model.student.Student
import com.anomali.studentmanagement.data.remote.dto.request.StudentRequest
import com.anomali.studentmanagement.data.remote.dto.response.StudentCreateResponse
import com.anomali.studentmanagement.data.remote.dto.response.StudentsListResponse
import com.anomali.studentmanagement.data.remote.network.RetrofitInstance
import com.google.gson.Gson
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

interface StudentRepository {
    suspend fun getStudents(): Response<StudentsListResponse>
    suspend fun getStudentById(id: Int): Response<Student>
    suspend fun createStudent(request: StudentRequest): Response<StudentCreateResponse>
    suspend fun updateStudent(id: Int, request: StudentRequest): Response<StudentCreateResponse>
    suspend fun deleteStudent(id: Int): Response<Unit>
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)
}

class StudentRepositoryImpl @Inject constructor(
    private val context: Context,
    private val studentDao: StudentDao
) : StudentRepository {
    init {
        RetrofitInstance.initRetrofit(context)
    }

    private val studentService = RetrofitInstance.getStudentService()

    override suspend fun getStudents(): Response<StudentsListResponse> {
        try {
            val response = studentService.getStudents()
            if (response.isSuccessful) {
                val subjects = response.body()
                subjects?.students?.forEach {
                    studentDao.insertStudent(it.toStudentEntity())
                }
                return Response.success(subjects)
            } else {
                throw Exception("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }

    override suspend fun getStudentById(id: Int): Response<Student> {
        val response = studentService.getStudentById(id)
        return if (response.isSuccessful) {
            Response.success(response.body()?.toModel())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun createStudent(request: StudentRequest): Response<StudentCreateResponse> {
        val response = studentService.createStudent(request)
        return if (response.isSuccessful) {
            Log.d("CreateStudent", "Response: ${response.body()}")
            Response.success(response.body())
        } else {
            Log.e("CreateStudent", "Error: ${response.code()} - ${response.errorBody()}")
            Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
        }
    }

    override suspend fun updateStudent(id: Int, request: StudentRequest): Response<StudentCreateResponse> {
        val response = studentService.updateStudent(id, request)

        if (response.isSuccessful) {
            Log.d("UpdateStudent", "Response: ${response.body()}")
            Log.d("UpdateStudent", "Request: ${Gson().toJson(request)}")
            return Response.success(response.body())
        } else {
            Log.e("UpdateStudent", "Error: ${response.code()} - ${response.errorBody()}")
            return Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun deleteStudent(id: Int): Response<Unit> {
        return studentService.deleteStudent(id)
    }

    override suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        Log.d("StudentRepository", "Updating student $id favorite status to $isFavorite")
        studentDao.updateFavoriteStatus(id, isFavorite) // Memanggil DAO langsung
    }

}