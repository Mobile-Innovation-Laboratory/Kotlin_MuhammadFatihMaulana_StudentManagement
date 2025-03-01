@file:OptIn(ExperimentalMaterial3Api::class)

package com.anomali.studentmanagement.ui.screens.teacher.attendance

import android.util.Log
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
import com.anomali.studentmanagement.data.remote.dto.request.teacher.AttendanceRequest
import com.anomali.studentmanagement.data.remote.dto.request.teacher.GradeRequest
import com.anomali.studentmanagement.data.remote.dto.response.ScheduleDTO
import com.anomali.studentmanagement.data.remote.dto.response.ScheduleListResponseDTO
import com.anomali.studentmanagement.data.remote.dto.response.StudentListResponseDTO
import com.anomali.studentmanagement.data.repository.admin.ScheduleRepository
import com.anomali.studentmanagement.data.repository.admin.StudentRepository
import com.anomali.studentmanagement.data.repository.teacher.AttendanceRepository
import com.anomali.studentmanagement.ui.navigations.TopNavigationNoIcon
import kotlinx.coroutines.launch

@Composable
fun AttendanceCreateScreen(
    navController: NavController,
    attendanceRepository: AttendanceRepository,
    studentRepository: StudentRepository,
    scheduleRepository: ScheduleRepository

) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var selectedStudent by remember { mutableStateOf<StudentListResponseDTO?>(null) }
    val studentList = remember { mutableStateOf(emptyList<StudentListResponseDTO>()) }
    val expandedStudent = remember { mutableStateOf(false) }

    var selectedSchedule by remember { mutableStateOf<ScheduleDTO?>(null) }
    val scheduleList = remember { mutableStateOf(emptyList<ScheduleDTO>()) }
    val expandedSchedule = remember { mutableStateOf(false) }

    val attendanceStatus = remember { mutableStateOf("") }
    val attendanceStatusList =
        listOf("Present", "Absent", "Sick", "Permission")
    val expandedAttendanceStatus = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val studentResponse = studentRepository.getStudents()
            if (studentResponse.isSuccessful) {
                studentList.value = studentResponse.body()?.students ?: emptyList()
            }
            val scheduleResponse = scheduleRepository.getSchedules()
            if (scheduleResponse.isSuccessful) {
                scheduleList.value = scheduleResponse.body()?.schedule ?: emptyList()
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
                    // Select Student
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
                    // Select Schedule
                    ExposedDropdownMenuBox(
                        expanded = expandedSchedule.value,
                        onExpandedChange = { expandedSchedule.value = it }
                    ) {
                        OutlinedTextField(
                            value = scheduleList.value.find { it.id == selectedSchedule?.id }?.classes?.name
                                ?: "Select Schedule",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = if (expandedSchedule.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedSchedule.value,
                            onDismissRequest = { expandedSchedule.value = false }
                        ) {
                            scheduleList.value.forEach { scheduleItem ->
                                DropdownMenuItem(
                                    text = { Text(scheduleItem.classes.name) },
                                    onClick = {
                                        selectedSchedule = scheduleItem
                                        expandedSchedule.value = false
                                    }
                                )
                            }
                        }
                    }

                    // Status Picker
                    ExposedDropdownMenuBox(
                        expanded = expandedAttendanceStatus.value,
                        onExpandedChange = { expandedAttendanceStatus.value = it }
                    ) {
                        OutlinedTextField(
                            value = attendanceStatus.value.ifEmpty { "Select Day" },
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = if (expandedAttendanceStatus.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedAttendanceStatus.value,
                            onDismissRequest = { expandedAttendanceStatus.value = false }
                        ) {
                            attendanceStatusList.forEach { status ->
                                DropdownMenuItem(
                                    text = { Text(status) },
                                    onClick = {
                                        attendanceStatus.value = status
                                        expandedSchedule.value = false
                                    }
                                )
                            }
                        }

                    }
                    Button(onClick = {
                        coroutineScope.launch {
                            val studentId = selectedStudent?.id ?: return@launch Toast.makeText(
                                context,
                                "Pilih siswa terlebih dahulu",
                                Toast.LENGTH_SHORT
                            ).show()
                            val scheduleId = selectedSchedule?.id ?: return@launch Toast.makeText(
                                context,
                                "Pilih guru terlebih dahulu",
                                Toast.LENGTH_SHORT
                            ).show()
                            val subjectId = attendanceStatus.value

                            try {
                                val gradeRequest = AttendanceRequest(
                                    studentId = studentId,
                                    scheduleId = scheduleId,
                                    status = subjectId
                                )

                                val response = attendanceRepository.createAttendance(gradeRequest)
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
                                Log.e("AttendanceCreateScreen", "Error: ${e.message}")
                                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }) {
                        Text("Tambahkan Absensi")
                    }
                }
            }
        }
    }
}