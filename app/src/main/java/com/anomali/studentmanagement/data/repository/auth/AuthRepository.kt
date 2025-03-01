package com.anomali.studentmanagement.data.repository.auth

import android.content.Context
import com.anomali.studentmanagement.core.utils.PreferencesUtils
import com.anomali.studentmanagement.data.mapper.toModel
import com.anomali.studentmanagement.data.model.User
import com.anomali.studentmanagement.data.remote.dto.request.LoginRequest
import com.anomali.studentmanagement.data.remote.dto.request.RegisterRequest
import com.anomali.studentmanagement.data.remote.dto.response.auth.LoginResponseDTO
import com.anomali.studentmanagement.data.remote.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginResponseDTO
    suspend fun getUserData(token: String): User?
    suspend fun logout(token: String)
    suspend fun register(name: String, email: String, password: String, confirmPassword: String)
}

class AuthRepositoryImpl(
    private val context: Context
) : AuthRepository {
    init {
        RetrofitInstance.initRetrofit(context)
    }

    private val authService = RetrofitInstance.getAuthService()

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        return withContext(Dispatchers.IO) {
            try {
                val request = RegisterRequest(name, email, password, confirmPassword)
                val response = authService.register(request)
                if (response.isSuccessful) {
                    val registerResponse = response.body()?.token
                    registerResponse?.let {
                        PreferencesUtils.saveTokenToPreferences(context, it)
                    }
                } else {
                    throw Exception("Registration failed: ${response.message()}")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    override suspend fun login(email: String, password: String): LoginResponseDTO {
        return withContext(Dispatchers.IO) {
            val response = authService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    PreferencesUtils.saveTokenToPreferences(context, loginResponse.token)
                    return@let loginResponse
                } ?: throw Exception("Empty response body")
            } else {
                throw Exception("Login failed: ${response.code()} ${response.message()}")
            }
        }
    }

    override suspend fun getUserData(token: String): User? {
        return withContext(Dispatchers.IO) {
            try {
                val response = authService.getUser("Bearer $token")
                if (response.isSuccessful) {
                    response.body()?.let { userDTO ->
                        val user = userDTO.toModel()
                        PreferencesUtils.saveUserToPreferences(context, user)
                        user
                    }
                } else {
                    println("Error: ${response.message()}")
                    null
                }
            } catch (e: Exception) {
                println("Exception: ${e.message}")
                null
            }
        }
    }

    override suspend fun logout(token: String) {
        withContext(Dispatchers.IO) {
            try {
                PreferencesUtils.clearTokenFromPreferences(context)
                val response = authService.logout("Bearer $token")
                if (!response.isSuccessful) {
                    throw Exception("Logout gagal: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                throw Exception("Error saat logout: ${e.localizedMessage}")
            }
        }
    }

    class UnauthorizedException : Exception("Token expired or unauthorized access")
}