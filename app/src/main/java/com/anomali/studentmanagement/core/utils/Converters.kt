package com.anomali.studentmanagement.core.utils

import androidx.room.TypeConverter
import com.anomali.studentmanagement.data.model.Classes
import com.anomali.studentmanagement.data.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    // Convert Classes object to String (JSON format)
    @TypeConverter
    fun fromClasses(classes: Classes): String {
        return Gson().toJson(classes)
    }

    // Convert String (JSON format) back to Classes object
    @TypeConverter
    fun toClasses(classesString: String): Classes {
        val type = object : TypeToken<Classes>() {}.type
        return Gson().fromJson(classesString, type)
    }

    // Convert User object to String (JSON format)
    @TypeConverter
    fun fromUser(user: User): String {
        return Gson().toJson(user)
    }

    // Convert String (JSON format) back to User object
    @TypeConverter
    fun toUser(userString: String): User {
        val type = object : TypeToken<User>() {}.type
        return Gson().fromJson(userString, type)
    }
}