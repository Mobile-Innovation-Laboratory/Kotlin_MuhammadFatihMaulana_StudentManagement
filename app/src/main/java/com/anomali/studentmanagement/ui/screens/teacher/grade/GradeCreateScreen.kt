package com.anomali.studentmanagement.ui.screens.teacher.grade

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.anomali.studentmanagement.data.model.Subject
import com.anomali.studentmanagement.data.remote.dto.request.ScheduleRequest
import com.anomali.studentmanagement.data.remote.dto.request.teacher.GradeRequest
import com.anomali.studentmanagement.data.remote.dto.response.StudentListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.StudentsListResponse
import com.anomali.studentmanagement.data.remote.dto.response.TeacherResponseDTO
import com.anomali.studentmanagement.data.repository.admin.StudentRepository
import com.anomali.studentmanagement.data.repository.admin.SubjectRepository
import com.anomali.studentmanagement.data.repository.admin.TeacherRepository
import com.anomali.studentmanagement.data.repository.teacher.GradeRepository
import com.anomali.studentmanagement.ui.components.LabeledInputField
import com.anomali.studentmanagement.ui.navigations.TopNavigationNoIcon
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeCreateScreen(
    navController: NavController,
    gradeRepository: GradeRepository,
    studentRepository: StudentRepository,
    subjectRepository: SubjectRepository,
    teacherRepository: TeacherRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var selectedStudent by remember { mutableStateOf<StudentListResponseDTO?>(null) }
    var selectedSubject by remember { mutableStateOf<Subject?>(null) }
    var selectedTeacher by remember { mutableStateOf<TeacherResponseDTO?>(null) }

    val grade = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    val studentList = remember { mutableStateOf(emptyList<StudentListResponseDTO>()) }
    val subjectList = remember { mutableStateOf(emptyList<Subject>()) }
    val teacherList = remember { mutableStateOf(emptyList<TeacherResponseDTO>()) }

    val expandedStudent = remember { mutableStateOf(false) }
    val expandedSubject = remember { mutableStateOf(false) }
    val expandedTeacher = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val studentResponse = studentRepository.getStudents()
            if (studentResponse.isSuccessful) {
                studentList.value = studentResponse.body()?.students ?: emptyList()
            }

            val subjectResponse = subjectRepository.getSubjects()
            if (subjectResponse.isSuccessful) {
                subjectList.value = subjectResponse.body() ?: emptyList()
            }

            val teacherResponse = teacherRepository.getTeachers()
            if (teacherResponse.isSuccessful) {
                teacherList.value = teacherResponse.body()?.teachers ?: emptyList()
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
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
                    // Class Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expandedStudent.value,
                        onExpandedChange = { expandedStudent.value = it }
                    ) {
                        OutlinedTextField(
                            value = studentList.value.find { it.id == selectedStudent?.id }?.user?.name
                                ?: "Select Class",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = if (expandedStudent.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedStudent.value,
                            onDismissRequest = { expandedStudent.value = false }
                        ) {
                            studentList.value.forEach { studentItem ->
                                DropdownMenuItem(
                                    text = { Text(studentItem.user.name) },
                                    onClick = {
                                        selectedStudent = studentItem
                                        expandedStudent.value = false
                                    }
                                )
                            }
                        }
                    }

                    // Subject Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expandedSubject.value,
                        onExpandedChange = { expandedSubject.value = it }
                    ) {
                        OutlinedTextField(
                            value = subjectList.value.find { it.id == selectedSubject?.id }?.name
                                ?: "Select Subject",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = if (expandedSubject.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedSubject.value,
                            onDismissRequest = { expandedSubject.value = false }
                        ) {
                            subjectList.value.forEach { subject ->
                                DropdownMenuItem(
                                    text = { Text(subject.name) },
                                    onClick = {
                                        selectedSubject = subject
                                        expandedSubject.value = false
                                    }
                                )
                            }
                        }
                    }

                    // Teacher Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expandedTeacher.value,
                        onExpandedChange = { expandedTeacher.value = it }
                    ) {
                        OutlinedTextField(
                            value = teacherList.value.find { it.id == selectedTeacher?.id }?.user?.name
                                ?: "Select Teacher",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = if (expandedTeacher.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedTeacher.value,
                            onDismissRequest = { expandedTeacher.value = false }
                        ) {
                            teacherList.value.forEach { teacher ->
                                DropdownMenuItem(
                                    text = { Text(teacher.user.name) },
                                    onClick = {
                                        selectedTeacher = teacher
                                        expandedTeacher.value = false
                                    }
                                )
                            }
                        }
                    }

                    LabeledInputField(
                        title = "Nilai",
                        placeholder = "Masukkan nilai",
                        value = grade.value,
                        onValueChange = { grade.value = it }
                    )
                    LabeledInputField(
                        title = "Deskripsi",
                        placeholder = "Masukkan deskripsi",
                        value = description.value,
                        onValueChange = { description.value = it }
                    )

                    Button(onClick = {
                        coroutineScope.launch {
                            val studentId = selectedStudent?.id ?: return@launch Toast.makeText(
                                context,
                                "Pilih siswa terlebih dahulu",
                                Toast.LENGTH_SHORT
                            ).show()
                            val teacherId = selectedTeacher?.id ?: return@launch Toast.makeText(
                                context,
                                "Pilih guru terlebih dahulu",
                                Toast.LENGTH_SHORT
                            ).show()
                            val subjectId = selectedSubject?.id ?: return@launch Toast.makeText(
                                context,
                                "Pilih mata pelajaran terlebih dahulu",
                                Toast.LENGTH_SHORT
                            ).show()

                            try {
                                val gradeRequest = GradeRequest(
                                    studentId = studentId,
                                    teacherId = teacherId,
                                    subjectId = subjectId,
                                    score = grade.value.toInt(),
                                    remark = description.value
                                )

                                val response = gradeRepository.createGrade(gradeRequest)
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Nilai berhasil ditambahkan",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.popBackStack()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Gagal menambahkan nilai",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }) {
                        Text("Tambahkan Jadwal")
                    }
                }
            }
        }
    }
}