package com.anomali.studentmanagement.ui.screens.admin.management.student

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.anomali.studentmanagement.data.model.Classes
import com.anomali.studentmanagement.data.model.student.Student
import com.anomali.studentmanagement.data.remote.dto.request.StudentRequest
import com.anomali.studentmanagement.data.remote.dto.request.UserRequest
import com.anomali.studentmanagement.data.repository.admin.ClassesRepository
import com.anomali.studentmanagement.data.repository.admin.StudentRepository
import com.anomali.studentmanagement.ui.components.ClassDropdown
import com.anomali.studentmanagement.ui.components.DatePickerField
import com.anomali.studentmanagement.ui.components.LabeledInputField
import com.anomali.studentmanagement.ui.navigations.TopNavigationNoIcon
import kotlinx.coroutines.launch

@Composable
fun DetailStudentScreen(
    navController: NavController,
    studentRepository: StudentRepository,
    studentId: Int,
    classesRepository: ClassesRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val selectedClass = remember { mutableStateOf<Int?>(null) }
    val classList = remember { mutableStateOf(emptyList<Classes>()) }
    var student by remember { mutableStateOf<Student?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    var studentName by remember { mutableStateOf("") }
    var studentNIS by remember { mutableStateOf("") }
    var studentNISN by remember { mutableStateOf("") }
    val studentDateOfBirth = remember { mutableStateOf("") }
    var studentPlaceOfBirth by remember { mutableStateOf("") }
    var studentGender by remember { mutableStateOf("") }
    var studentFatherName by remember { mutableStateOf("") }
    var studentFatherPhoneNumber by remember { mutableStateOf("") }
    val studentFatherDateOfBirth = remember { mutableStateOf("") }
    var studentFatherPlaceOfBirth by remember { mutableStateOf("") }
    var studentFatherOccupation by remember { mutableStateOf("") }
    var studentFatherAddress by remember { mutableStateOf("") }
    var studentMotherName by remember { mutableStateOf("") }
    var studentMotherPhoneNumber by remember { mutableStateOf("") }
    val studentMotherDateOfBirth = remember { mutableStateOf("") }
    var studentMotherPlaceOfBirth by remember { mutableStateOf("") }
    var studentMotherOccupation by remember { mutableStateOf("") }
    var studentMotherAddress by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val studentClass by remember {
        derivedStateOf {
            val selectedClassName = classList.value.find { it.id == selectedClass.value }
            selectedClassName?.name ?: "Pilih Kelas"
        }
    }

    LaunchedEffect(studentId) {
        try {
            val studentResponse = studentRepository.getStudentById(studentId)
            if (studentResponse.isSuccessful) {
                val responseBody = studentResponse.body()
                student = responseBody
                student?.let {
                    studentName = it.user.name
                    studentNIS = it.nis
                    studentNISN = it.nisn
                    studentDateOfBirth.value = it.dateOfBirth
                    studentPlaceOfBirth = it.placeOfBirth
                    studentGender = it.gender
//                    studentFatherName = it.father.name
//                    studentFatherPhoneNumber = it.father.phoneNumber
//                    studentFatherDateOfBirth.value = it.father.bornDate
//                    studentFatherPlaceOfBirth = it.father.bornPlace
//                    studentFatherOccupation = it.father.occupation
//                    studentFatherAddress = it.father.address
//                    studentMotherName = it.mother.name
//                    studentMotherPhoneNumber = it.mother.phoneNumber
//                    studentMotherDateOfBirth.value = it.mother.bornDate
//                    studentMotherPlaceOfBirth = it.mother.bornPlace
//                    studentMotherOccupation = it.mother.occupation
//                    studentMotherAddress = it.mother.address
                    email = it.user.email
                }
                selectedClass.value = student?.classId?.id
            } else {
                Log.e("StudentData", "Error: ${studentResponse.message()}")
                Toast.makeText(context, "Error: ${studentResponse.message()}", Toast.LENGTH_SHORT)
                    .show()
            }

            val classesResponse = classesRepository.getClasses()
            if (classesResponse.isSuccessful) {
                classList.value = classesResponse.body() ?: emptyList()
            }
        } catch (e: Exception) {
            Log.e("StudentData", "Exception: ${e.message}", e)
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

//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Text(text = "Data Ayah", fontWeight = FontWeight.Bold)
//
//                    LabeledInputField(
//                        title = "Nama Ayah",
//                        placeholder = "Masukkan nama ayah",
//                        value = studentFatherName,
//                        onValueChange = { studentFatherName = it },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                        isPasswordTextField = false
//                    )
//
//                    LabeledInputField(
//                        title = "Nomor Telepon Ayah",
//                        placeholder = "Masukkan nomor telepon",
//                        value = studentFatherPhoneNumber,
//                        onValueChange = { studentFatherPhoneNumber = it },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//                        isPasswordTextField = false
//                    )
//
//                    DatePickerField(
//                        label = "Tanggal Lahir Ayah",
//                        selectedDate = studentFatherDateOfBirth,
//                    )
//
//                    LabeledInputField(
//                        title = "Tempat Lahir Ayah",
//                        placeholder = "Masukkan tempat lahir ayah",
//                        value = studentFatherPlaceOfBirth,
//                        onValueChange = { studentFatherPlaceOfBirth = it },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                        isPasswordTextField = false
//                    )
//
//                    LabeledInputField(
//                        title = "Pekerjaan Ayah",
//                        placeholder = "Masukkan pekerjaan ayah",
//                        value = studentFatherOccupation,
//                        onValueChange = { studentFatherOccupation = it },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                        isPasswordTextField = false
//                    )
//
//                    LabeledInputField(
//                        title = "Alamat Ayah",
//                        placeholder = "Masukkan alamat ayah",
//                        value = studentFatherAddress,
//                        onValueChange = { studentFatherAddress = it },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                        isPasswordTextField = false
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Text(text = "Data Ibu", fontWeight = FontWeight.Bold)
//
//                    LabeledInputField(
//                        title = "Nama Ibu",
//                        placeholder = "Masukkan nama ibu",
//                        value = studentMotherName,
//                        onValueChange = { studentMotherName = it },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                        isPasswordTextField = false
//                    )
//
//                    LabeledInputField(
//                        title = "Nomor Telepon Ibu",
//                        placeholder = "Masukkan nomor telepon",
//                        value = studentMotherPhoneNumber,
//                        onValueChange = { studentMotherPhoneNumber = it },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//                        isPasswordTextField = false
//                    )
//
//                    DatePickerField(
//                        label = "Tanggal Lahir Ibu",
//                        selectedDate = studentMotherDateOfBirth,
//                    )
//
//                    LabeledInputField(
//                        title = "Tempat Lahir Ibu",
//                        placeholder = "Masukkan tempat lahir ibu",
//                        value = studentMotherPlaceOfBirth,
//                        onValueChange = { studentMotherPlaceOfBirth = it },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                        isPasswordTextField = false
//                    )
//
//                    LabeledInputField(
//                        title = "Pekerjaan Ibu",
//                        placeholder = "Masukkan pekerjaan ibu",
//                        value = studentMotherOccupation,
//                        onValueChange = { studentMotherOccupation = it },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                        isPasswordTextField = false
//                    )
//
//                    LabeledInputField(
//                        title = "Alamat Ibu",
//                        placeholder = "Masukkan alamat ibu",
//                        value = studentMotherAddress,
//                        onValueChange = { studentMotherAddress = it },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                        isPasswordTextField = false
//                    )
//
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
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                val response = studentRepository.deleteStudent(studentId)
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Pelajaran berhasil dihapus!",
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
                        text = "Hapus Pelajaran",
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
                        if (studentName.isNotEmpty()) {
                            coroutineScope.launch {
                                try {
                                    val response = studentRepository.updateStudent(
                                        studentId,
                                        request = StudentRequest(
                                            nis = studentNIS,
                                            nisn = studentNISN,
                                            classId = selectedClass.value ?: -1,
                                            dateOfBirth = studentDateOfBirth.value,
                                            placeOfBirth = studentPlaceOfBirth,
                                            gender = studentGender,
//                                            father = FatherRequest(
//                                                name = studentFatherName,
//                                                phoneNumber = studentFatherPhoneNumber,
//                                                bornDate = studentFatherDateOfBirth.value,
//                                                bornPlace = studentFatherPlaceOfBirth,
//                                                occupation = studentFatherOccupation,
//                                                address = studentFatherAddress
//                                            ),
//                                            mother = MotherRequest(
//                                                name = studentMotherName,
//                                                phoneNumber = studentMotherPhoneNumber,
//                                                bornDate = studentMotherDateOfBirth.value,
//                                                bornPlace = studentMotherPlaceOfBirth,
//                                                occupation = studentMotherOccupation,
//                                                address = studentMotherAddress
//                                            ),
                                            user = UserRequest(
                                                name = studentName,
                                                email = email,
                                                password = password.ifEmpty { "" }
                                            )
                                        )
                                    )

                                    Log.d(
                                        "SelectedClass",
                                        "Selected class ID: ${selectedClass.value}"
                                    )
                                    if (response.isSuccessful) {
                                        val updatedStudent =
                                            studentRepository.getStudentById(studentId)
                                        if (updatedStudent.isSuccessful) {
                                            student = updatedStudent.body()
                                        }

                                        Toast.makeText(
                                            context,
                                            "Data siswa berhasil diubah!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.popBackStack()
                                    } else {
                                        errorMessage = "Failed to update student"
                                    }
                                } catch (e: Exception) {
                                    errorMessage = "Error: ${e.message}"
                                    Log.e("UpdateStudent", "Error: ${e.message}")
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
                        text = "Ubah Pelajaran",
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