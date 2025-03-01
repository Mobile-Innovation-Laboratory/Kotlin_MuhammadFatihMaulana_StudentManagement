package com.anomali.studentmanagement.ui.screens.admin.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.anomali.studentmanagement.data.local.dao.StudentDao
import com.anomali.studentmanagement.data.local.entity.StudentEntity
import com.anomali.studentmanagement.data.mapper.toModel
import com.anomali.studentmanagement.data.repository.admin.StudentRepository
import com.anomali.studentmanagement.ui.components.StudentListItem
import com.anomali.studentmanagement.ui.navigations.BottomNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FavoriteListScreen(
    navController: NavController,
    studentRepository: StudentRepository,
    studentDao: StudentDao
) {
    var favoriteStudents by remember { mutableStateOf<List<StudentEntity>>(emptyList()) }

    LaunchedEffect(Unit) {
        favoriteStudents = studentDao.getAllStudents().filter { it.isFavorite }
    }

    Scaffold(
        bottomBar = { BottomNavigation(navController) }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 32.dp)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Daftar Siswa Favorit",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start,
            ) {
                if (favoriteStudents.isNotEmpty()) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(favoriteStudents) { studentEntity ->
                            val student = studentEntity.toModel()
                            StudentListItem(
                                student = student,
                                isFavorite = studentEntity.isFavorite,
                                onFavoriteChanged = { newIsFavorite ->
                                    CoroutineScope(Dispatchers.IO).launch {
                                        studentRepository.updateFavoriteStatus(studentEntity.id, newIsFavorite)
                                        favoriteStudents = studentDao.getAllStudents().filter { it.isFavorite }
                                    }
                                },
                                studentRepository = studentRepository
                            )
                        }
                    }
                } else {
                    Text(text = "Tidak ada siswa favorit.")
                }
            }
        }
    }
}

