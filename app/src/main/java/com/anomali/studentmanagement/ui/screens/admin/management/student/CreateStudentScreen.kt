package com.anomali.studentmanagement.ui.screens.admin.management.student

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.anomali.studentmanagement.data.model.Classes
import com.anomali.studentmanagement.data.remote.dto.request.StudentRequest
import com.anomali.studentmanagement.data.remote.dto.request.UserRequest
import com.anomali.studentmanagement.data.repository.admin.ClassesRepository
import com.anomali.studentmanagement.data.repository.admin.StudentRepository
import com.anomali.studentmanagement.ui.components.ClassDropdown
import com.anomali.studentmanagement.ui.components.DatePickerField
import com.anomali.studentmanagement.ui.components.LabeledInputField
import com.anomali.studentmanagement.ui.navigations.TopNavigationNoIcon
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun CreateStudentScreen(
    navController: NavController,
    studentRepository: StudentRepository,
    classesRepository: ClassesRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val selectedClass = remember { mutableStateOf<Int?>(null) }
    val classList = remember { mutableStateOf(emptyList<Classes>()) }
    val context = LocalContext.current

    var studentName by remember { mutableStateOf("") }
    var studentNIS by remember { mutableStateOf("") }
    var studentNISN by remember { mutableStateOf("") }
    val studentDateOfBirth = remember { mutableStateOf("") }
    var studentPlaceOfBirth by remember { mutableStateOf("") }
    var studentGender by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val studentClass by remember {
        derivedStateOf {
            classList.value.find { it.id == selectedClass.value }?.name ?: "Pilih Kelas"
        }
    }

    LaunchedEffect(Unit) {
        try {
            val response = classesRepository.getClasses()
            if (response.isSuccessful) {
                classList.value = response.body() ?: emptyList()
            }
        } catch (e: Exception) {
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
                .verticalScroll(rememberScrollState()),
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
                        title = "Nama Siswa",
                        placeholder = "Masukkan nama siswa",
                        value = studentName,
                        onValueChange = { studentName = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        isPasswordTextField = false
                    )

                    LabeledInputField(
                        title = "NIS",
                        placeholder = "Masukkan NIS",
                        value = studentNIS,
                        onValueChange = { studentNIS = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isPasswordTextField = false
                    )

                    LabeledInputField(
                        title = "NISN",
                        placeholder = "Masukkan NISN",
                        value = studentNISN,
                        onValueChange = { studentNISN = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isPasswordTextField = false
                    )

                    ClassDropdown(
                        classRepository = classesRepository,
                        selectedClassId = selectedClass,
                    )

                    DatePickerField(
                        label = "Tanggal Lahir",
                        selectedDate = studentDateOfBirth,
                    )

                    LabeledInputField(
                        title = "Tempat Lahir",
                        placeholder = "Masukkan tempat lahir",
                        value = studentPlaceOfBirth,
                        onValueChange = { studentPlaceOfBirth = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        isPasswordTextField = false
                    )

                    LabeledInputField(
                        title = "Jenis Kelamin",
                        placeholder = "Laki-laki / Perempuan",
                        value = studentGender,
                        onValueChange = { studentGender = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        isPasswordTextField = false
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Informasi Akun", fontWeight = FontWeight.Bold)

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
                        placeholder = "Masukkan kembali password",
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

                        if (studentName.isEmpty()) missingFields.add("Nama Siswa")
                        if (studentNIS.isEmpty()) missingFields.add("NIS")
                        if (studentNISN.isEmpty()) missingFields.add("NISN")
                        if (studentClass.isEmpty()) missingFields.add("Kelas")
                        if (studentDateOfBirth.value.isEmpty()) missingFields.add("Tanggal Lahir")
                        if (studentPlaceOfBirth.isEmpty()) missingFields.add("Tempat Lahir")
                        if (studentGender.isEmpty()) missingFields.add("Jenis Kelamin")
                        if (email.isEmpty()) missingFields.add("Email")
                        if (password.isEmpty()) missingFields.add("Password")
                        if (confirmPassword.isEmpty()) missingFields.add("Konfirmasi Password")
                        if (password != confirmPassword) {
                            Toast.makeText(context, "Password tidak cocok", Toast.LENGTH_LONG).show()
                            return@launch
                        }

                        Log.d("Missing Fields", missingFields.toString())
                        if (missingFields.isNotEmpty()) {
                            Log.e(
                                "Validation",
                                "Field yang belum diisi: ${missingFields.joinToString(", ")}"
                            )

                            Toast.makeText(
                                context,
                                "Harap isi semua data yang wajib diisi:\n${
                                    missingFields.joinToString(
                                        ", "
                                    )
                                }",
                                Toast.LENGTH_LONG
                            ).show()
                            return@launch
                        }

                        try {
                            val student = StudentRequest(
                                nis = studentNIS,
                                nisn = studentNISN,
                                classId = selectedClass.value ?: -1,
                                dateOfBirth = studentDateOfBirth.value,
                                placeOfBirth = studentPlaceOfBirth,
                                gender = studentGender,
                                user = UserRequest(
                                    name = studentName,
                                    email = email,
                                    password = password
                                )
                            )

                            val response = studentRepository.createStudent(student)
                            Log.d("Response", response.toString())
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Siswa berhasil ditambahkan",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.popBackStack()
                            } else {
                                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                                Toast.makeText(
                                    context,
                                    "Gagal menambahkan siswa: $errorMessage",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.e("Error", errorMessage)
                            }
                            Log.d("Request Data", student.toString())
                            Log.d("Request JSON", Gson().toJson(student))
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("Request Error", e.toString())
                        }
                    }
                }
            ) {
                Text("Simpan")
            }
        }
    }
}

