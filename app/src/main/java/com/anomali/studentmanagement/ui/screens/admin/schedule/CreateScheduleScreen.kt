@file:OptIn(ExperimentalMaterial3Api::class)

package com.anomali.studentmanagement.ui.screens.admin.schedule

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.anomali.studentmanagement.data.model.Classes
import com.anomali.studentmanagement.data.model.Subject
import com.anomali.studentmanagement.data.remote.dto.request.ScheduleRequest
import com.anomali.studentmanagement.data.remote.dto.response.TeacherResponseDTO
import com.anomali.studentmanagement.data.repository.admin.ClassesRepository
import com.anomali.studentmanagement.data.repository.admin.ScheduleRepository
import com.anomali.studentmanagement.data.repository.admin.SubjectRepository
import com.anomali.studentmanagement.data.repository.admin.TeacherRepository
import com.anomali.studentmanagement.ui.components.LabeledInputField
import com.anomali.studentmanagement.ui.navigations.TopNavigationNoIcon
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScheduleScreen(
    navController: NavController,
    scheduleRepository: ScheduleRepository,
    classRepository: ClassesRepository,
    subjectRepository: SubjectRepository,
    teacherRepository: TeacherRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var selectedClass by remember { mutableStateOf<Classes?>(null) }
    var selectedSubject by remember { mutableStateOf<Subject?>(null) }
    var selectedTeacher by remember { mutableStateOf<TeacherResponseDTO?>(null) }

    val classList = remember { mutableStateOf(emptyList<Classes>()) }
    val subjectList = remember { mutableStateOf(emptyList<Subject>()) }
    val teacherList = remember { mutableStateOf(emptyList<TeacherResponseDTO>()) }

    val expandedClass = remember { mutableStateOf(false) }
    val expandedSubject = remember { mutableStateOf(false) }
    val expandedTeacher = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val classResponse = classRepository.getClasses()
            if (classResponse.isSuccessful) {
                classList.value = classResponse.body() ?: emptyList()
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

    // State untuk time dan day picker
    val scheduleDay = remember { mutableStateOf("") }
    val scheduleStartTime = remember { mutableStateOf("") }
    val scheduleEndTime = remember { mutableStateOf("") }

    // Day Picker options
    val daysOfWeek =
        listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val expandedDay = remember { mutableStateOf(false) }

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
                        expanded = expandedClass.value,
                        onExpandedChange = { expandedClass.value = it }
                    ) {
                        OutlinedTextField(
                            value = classList.value.find { it.id == selectedClass?.id }?.name
                                ?: "Select Class",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = if (expandedClass.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedClass.value,
                            onDismissRequest = { expandedClass.value = false }
                        ) {
                            classList.value.forEach { classItem ->
                                DropdownMenuItem(
                                    text = { Text(classItem.name) },
                                    onClick = {
                                        selectedClass = classItem
                                        expandedClass.value = false
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

                    // Day Picker (Dropdown)
                    ExposedDropdownMenuBox(
                        expanded = expandedDay.value,
                        onExpandedChange = { expandedDay.value = it }
                    ) {
                        OutlinedTextField(
                            value = scheduleDay.value.ifEmpty { "Select Day" },
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = if (expandedDay.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedDay.value,
                            onDismissRequest = { expandedDay.value = false }
                        ) {
                            daysOfWeek.forEach { day ->
                                DropdownMenuItem(
                                    text = { Text(day) },
                                    onClick = {
                                        scheduleDay.value = day
                                        expandedDay.value = false
                                    }
                                )
                            }
                        }
                    }

                    LabeledInputField(
                        title = "Start Time",
                        placeholder = "Masukkan waktu mulai",
                        value = scheduleStartTime.value,
                        onValueChange = { scheduleStartTime.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        isPasswordTextField = false
                    )

                    LabeledInputField(
                        title = "End Time",
                        placeholder = "Masukkan waktu selesai",
                        value = scheduleEndTime.value,
                        onValueChange = { scheduleEndTime.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    )

                    Button(onClick = {
                        coroutineScope.launch {
                            val classId = selectedClass?.id ?: return@launch Toast.makeText(
                                context,
                                "Pilih kelas terlebih dahulu",
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
                                val schedule = ScheduleRequest(
                                    classId = classId,
                                    teacherId = teacherId,
                                    subjectId = subjectId,
                                    day = scheduleDay.value,
                                    startTime = scheduleStartTime.value,
                                    endTime = scheduleEndTime.value
                                )

                                val response = scheduleRepository.createSchedule(schedule)
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Jadwal berhasil ditambahkan",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.popBackStack()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Gagal menambahkan jadwal",
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
