package com.anomali.studentmanagement.ui.screens.admin.schedule

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.anomali.studentmanagement.data.mapper.toModel
import com.anomali.studentmanagement.data.model.Classes
import com.anomali.studentmanagement.data.model.Schedule
import com.anomali.studentmanagement.data.model.Subject
import com.anomali.studentmanagement.data.model.Teacher
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
fun DetailScheduleScreen(
    navController: NavController,
    scheduleRepository: ScheduleRepository,
    classRepository: ClassesRepository,
    subjectRepository: SubjectRepository,
    teacherRepository: TeacherRepository,
    scheduleId: Int
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var schedule by remember { mutableStateOf<Schedule?>(null) }

    var selectedClass by remember { mutableStateOf<Classes?>(null) }
    var selectedSubject by remember { mutableStateOf<Subject?>(null) }
    var selectedTeacher by remember { mutableStateOf<Teacher?>(null) }

    val classList = remember { mutableStateOf(emptyList<Classes>()) }
    val subjectList = remember { mutableStateOf(emptyList<Subject>()) }
    val teacherList = remember { mutableStateOf(emptyList<TeacherResponseDTO>()) }

    val expandedClass = remember { mutableStateOf(false) }
    val expandedSubject = remember { mutableStateOf(false) }
    val expandedTeacher = remember { mutableStateOf(false) }

    // State untuk time dan day picker
    val scheduleDay = remember { mutableStateOf("") }
    val scheduleStartTime = remember { mutableStateOf("") }
    val scheduleEndTime = remember { mutableStateOf("") }

    // Day Picker options
    val daysOfWeek =
        listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val expandedDay = remember { mutableStateOf(false) }

    LaunchedEffect(scheduleId) {
        try {
            val scheduleResponse = scheduleRepository.getScheduleById(scheduleId)
            if (scheduleResponse.isSuccessful) {
                val responseBody = scheduleResponse.body()
                schedule = responseBody
                schedule?.let {
                    selectedClass = it.classes
                    selectedSubject = it.subject
                    selectedTeacher = it.teacher
                    scheduleDay.value = it.day
                    scheduleStartTime.value = it.startTime
                    scheduleEndTime.value = it.endTime
                    expandedDay.value = true
                    expandedClass.value = true
                    expandedSubject.value = true
                    expandedTeacher.value = true
                }
            }

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
                            value = selectedClass?.name ?: "Select Class",
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
                            value = selectedSubject?.name ?: "Select Subject",
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
                            value = selectedTeacher?.user?.name ?: "Select Teacher",
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
                                        selectedTeacher = teacher.toModel()
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
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                val response = scheduleRepository.deleteSchedule(scheduleId)
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Jadwal berhasil dihapus!",
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
                        text = "Hapus Jadwal",
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
                        if (selectedClass == null || selectedSubject == null || selectedTeacher == null) {
                            errorMessage = "Please select valid class, subject, and teacher."
                        } else if (selectedClass?.id == 0 || selectedSubject?.id == 0 || selectedTeacher?.id == 0) {
                            errorMessage = "Invalid class, subject, or teacher ID."
                        } else {
                            // Proceed with the update
                            val updatedSchedule = ScheduleRequest(
                                classId = selectedClass?.id ?: -1,  // Ensure IDs are set to valid values
                                subjectId = selectedSubject?.id ?: -1,
                                teacherId = selectedTeacher?.id ?: -1,
                                day = scheduleDay.value,
                                startTime = scheduleStartTime.value,
                                endTime = scheduleEndTime.value
                            )

                            coroutineScope.launch {
                                val response = scheduleRepository.updateSchedule(scheduleId, updatedSchedule)
                                if (response.isSuccessful) {
                                    Toast.makeText(context, "Jadwal berhasil diperbarui", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                } else {
                                    errorMessage = "Failed to update schedule."
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
                        text = "Ubah Jadwal",
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
