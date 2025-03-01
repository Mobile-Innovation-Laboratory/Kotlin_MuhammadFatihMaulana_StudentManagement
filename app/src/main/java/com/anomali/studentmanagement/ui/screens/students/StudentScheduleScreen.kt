package com.anomali.studentmanagement.ui.screens.students

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.anomali.studentmanagement.data.remote.dto.response.student.ScheduleDataResponseDTO
import com.anomali.studentmanagement.data.repository.student.StudentAcademicRepository
import com.anomali.studentmanagement.ui.navigations.BottomNavigation
import com.anomali.studentmanagement.ui.navigations.TopNavigation

@Composable
fun StudentScheduleScreen(navController: NavController, studentAcademicRepository: StudentAcademicRepository) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var schedule by remember { mutableStateOf<List<ScheduleDataResponseDTO>>(emptyList()) }

    LaunchedEffect(Unit) {
        isLoading = true
        try {
            val response = studentAcademicRepository.getStudentScheduleData()
            if (response.isSuccessful) {
                schedule = response.body()?.schedules ?: emptyList()
            } else {
                Log.e("ScheduleScreen", "Error: ${response.message()}")
                errorMessage = "Error: ${response.message()}"
            }
        } catch (e: Exception) {
            Log.e("ScheduleScreen", "Exception: ${e.message}")
            errorMessage = "Error: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        bottomBar = { BottomNavigation(navController) },
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(32.dp)
                .fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (errorMessage != null) {
                Text(text = errorMessage ?: "Unknown error occurred")
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(schedule.size) { index ->
                        ScheduleItem(schedule[index])
                    }
                }
            }
        }
    }
}

@Composable
fun ScheduleItem(scheduleDataResponseDTO: ScheduleDataResponseDTO) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = scheduleDataResponseDTO.subject.name,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = scheduleDataResponseDTO.teacher.user.name,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    )
                )
                Text(
                    text = scheduleDataResponseDTO.classes.name,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                    )
                )
            }
        }
    }
}