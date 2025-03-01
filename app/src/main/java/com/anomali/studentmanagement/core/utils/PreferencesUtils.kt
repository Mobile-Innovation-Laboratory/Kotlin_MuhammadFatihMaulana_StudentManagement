package com.anomali.studentmanagement.core.utils

import android.content.Context
import com.anomali.studentmanagement.data.model.User
import com.google.gson.Gson

object PreferencesUtils {
    private const val PREFS_NAME = "app_prefs"
    private const val KEY_AUTH_TOKEN = "auth_token"
    private const val KEY_USER = "user"
    private val gson = Gson()

    fun getTokenFromPreferences(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_AUTH_TOKEN, "") ?: ""
    }

    fun saveTokenToPreferences(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun clearTokenFromPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(KEY_AUTH_TOKEN)
            .remove(KEY_USER)
            .apply()
    }

    fun getUserFromPreferences(context: Context): User? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val userJson = sharedPreferences.getString(KEY_USER, null)
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            null
        }
    }

    fun saveUserToPreferences(context: Context, user: User) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val userJson = gson.toJson(user)
        sharedPreferences.edit().putString(KEY_USER, userJson).apply()
    }

    fun getUserRoleFromPreferences(context: Context): String {
        val user = getUserFromPreferences(context)
        return user?.role ?: "siswa"
    }
}