package com.anomali.studentmanagement.ui.screens.admin.management.teacher

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.anomali.studentmanagement.data.remote.dto.request.TeacherRequest
import com.anomali.studentmanagement.data.remote.dto.request.UserDataRequest
import com.anomali.studentmanagement.data.remote.dto.request.UserRequest
import com.anomali.studentmanagement.data.repository.admin.TeacherRepository
import com.anomali.studentmanagement.ui.components.LabeledInputField
import com.anomali.studentmanagement.ui.navigations.TopNavigationNoIcon
import kotlinx.coroutines.launch

@Composable
fun CreateTeacherScreen(navController: NavController, teacherRepository: TeacherRepository) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var teacherName by remember { mutableStateOf("") }
    var teacherNIP by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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
            Button(
                onClick = {
                    coroutineScope.launch {
                        val missingFields = mutableListOf<String>()
                        if (teacherName.isEmpty()) missingFields.add("Nama Lengkap")
                        if (teacherNIP.isEmpty()) missingFields.add("NIP")
                        if (email.isEmpty()) missingFields.add("Email")
                        if (password.isEmpty()) missingFields.add("Password")
                        if (confirmPassword.isEmpty()) missingFields.add("Konfirmasi Password")
                        if (password != confirmPassword) {
                            Toast.makeText(context, "Password tidak cocok", Toast.LENGTH_LONG)
                                .show()
                            return@launch
                        }

                        try {
                            val teacher = TeacherRequest(
                                user = UserDataRequest(
                                    name = teacherName,
                                    email = email,
                                    password = password
                                ),
                                nip = teacherNIP
                            )

                            val response = teacherRepository.createTeacher(teacher)
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Guru berhasil ditambahkan",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigateUp()
                            } else {
                                val errorResponse = response.errorBody()?.string()
                                Toast.makeText(
                                    context,
                                    "Gagal menambahkan guru: $errorResponse",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                },
            ) {
                Text(text = "Tambahkan Guru")
            }
        }
    }
}