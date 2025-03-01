package com.anomali.studentmanagement.ui.screens.admin.management.teacher

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.anomali.studentmanagement.data.model.Teacher
import com.anomali.studentmanagement.data.remote.dto.request.TeacherRequest
import com.anomali.studentmanagement.data.remote.dto.request.UserDataRequest
import com.anomali.studentmanagement.data.repository.admin.TeacherRepository
import com.anomali.studentmanagement.ui.components.LabeledInputField
import com.anomali.studentmanagement.ui.navigations.TopNavigationNoIcon
import kotlinx.coroutines.launch

@Composable
fun DetailTeacherScreen(
    navController: NavController,
    teacherRepository: TeacherRepository,
    teacherId: Int
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var teacher by remember { mutableStateOf<Teacher?>(null) }

    var teacherName by remember { mutableStateOf("") }
    var teacherNIP by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    LaunchedEffect(teacherId) {
        try {
            val response = teacherRepository.getTeacherById(teacherId)
            if (response.isSuccessful) {
                val responseBody = response.body()
                responseBody?.let {
                    teacher = it
                    teacherName = it.user?.name ?: ""
                    teacherNIP = it.nip
                    email = it.user?.email ?: ""
                }
            } else {
                Log.e("Teacher Data", "Error: ${response.message()}")
                Toast.makeText(context, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("Teacher Data", "Exception: ${e.message}", e)
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        topBar = { TopNavigationNoIcon(navController) },
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(paddingValues)
                .padding(32.dp)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start,
                ) {
                    LabeledInputField(
                        title = "Nama Lengkap",
                        placeholder = "Masukkan nama lengkap",
                        value = teacherName,
                        onValueChange = { teacherName = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        isPasswordTextField = false
                    )
                    LabeledInputField(
                        title = "NIP",
                        placeholder = "Masukkan NIP",
                        value = teacherNIP,
                        onValueChange = { teacherNIP = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isPasswordTextField = false
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Autentikasi",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF1E3A8A),
                    )
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start,
                ) {
                    LabeledInputField(
                        title = "Email",
                        placeholder = "Masukkan email",
                        value = email,
                        onValueChange = { email = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isPasswordTextField = false
                    )
                    LabeledInputField(
                        title = "Password",
                        placeholder = "Masukkan password",
                        value = password,
                        onValueChange = { password = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isPasswordTextField = true
                    )
                    LabeledInputField(
                        title = "Konfirmasi Password",
                        placeholder = "Masukkan konfirmasi password",
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isPasswordTextField = true
                    )
                }
            }
            Log.e("DetailTeacherScreen", "errorMessage: $errorMessage")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                val response = teacherRepository.deleteTeacher(teacherId)
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Data guru berhasil dihapus!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.popBackStack()
                                } else {
                                    errorMessage = "Error: ${response.message()}"
                                }
                            } catch (e: Exception) {
                                errorMessage = "Error: ${e.message}"
                            }
                        }
                    },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = "Hapus Data Guru",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Button(
                    onClick = {
                        if (teacherName.isNotEmpty()) {
                            coroutineScope.launch {
                                try {
                                    val response = teacherRepository.updateTeacher(
                                        teacherId,
                                        request = TeacherRequest(
                                            user = UserDataRequest(
                                                name = teacherName,
                                                email = email,
                                                password = password
                                            ),
                                            nip = teacherNIP
                                        ),
                                    )

                                    if (response.isSuccessful) {
                                        val updatedTeacher =
                                            teacherRepository.getTeacherById(teacherId)
                                        if (updatedTeacher.isSuccessful) {
                                            teacher = updatedTeacher.body()
                                        }

                                        Toast.makeText(
                                            context,
                                            "Data guru berhasil diubah!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.popBackStack()
                                    } else {
                                        errorMessage = "Failed to update student"
                                    }
                                } catch (e: Exception) {
                                    errorMessage = "Error: ${e.message}"
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF16A34A),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = "Ubah data guru",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
    }
}