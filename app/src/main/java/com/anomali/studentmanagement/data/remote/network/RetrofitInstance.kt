package com.anomali.studentmanagement.data.remote.network

import android.content.Context
import com.anomali.studentmanagement.core.utils.AuthInterceptor
import com.anomali.studentmanagement.data.remote.api.AuthService
import com.anomali.studentmanagement.data.remote.api.ClassesService
import com.anomali.studentmanagement.data.remote.api.ScheduleService
import com.anomali.studentmanagement.data.remote.api.StudentService
import com.anomali.studentmanagement.data.remote.api.SubjectService
import com.anomali.studentmanagement.data.remote.api.TeacherService
import com.anomali.studentmanagement.data.remote.api.student.StudentAcademicService
import com.anomali.studentmanagement.data.remote.api.teacher.AttendanceService
import com.anomali.studentmanagement.data.remote.api.teacher.GradeService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.18.26:8000/"
//    private const val BASE_URL = "http://192.168.150.47:8000/"

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private lateinit var retrofit: Retrofit

    fun initRetrofit(context: Context) {
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getAuthService(): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    fun getStudentService(): StudentService {
        return retrofit.create(StudentService::class.java)
    }

    fun getSubjectService(): SubjectService {
        return retrofit.create(SubjectService::class.java)
    }

    fun getClassService(): ClassesService {
        return retrofit.create(ClassesService::class.java)
    }

    fun getTeacherService(): TeacherService {
        return retrofit.create(TeacherService::class.java)
    }

    fun getScheduleService(): ScheduleService {
        return retrofit.create(ScheduleService::class.java)
    }

    fun getGradeService(): GradeService {
        return retrofit.create(GradeService::class.java)
    }

    fun getAttendanceService(): AttendanceService {
        return retrofit.create(AttendanceService::class.java)
    }

    fun getStudentAcademeService(): StudentAcademicService {
        return retrofit.create(StudentAcademicService::class.java)
    }
}