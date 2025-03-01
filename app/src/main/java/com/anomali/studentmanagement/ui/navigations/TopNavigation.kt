package com.anomali.studentmanagement.ui.navigations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anomali.studentmanagement.R
import com.anomali.studentmanagement.core.routes.AppRoutes

@Composable
fun TopNavigation(navController: NavController) {
    var currentTitle by remember { mutableStateOf("Manajemen") }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    fun onSearchClicked(currentRoute: String?) {
        when (currentRoute) {
            "guru" -> {
                println("Mencari guru...")
            }

            "siswa" -> {
                println("Mencari siswa...")
            }

            "kelas" -> {
                println("Mencari kelas...")
            }

            "pelajaran" -> {
                println("Mencari pelajaran...")
            }

            else -> {
                println("Mencari...")
            }
        }
    }

    fun onAddClicked(currentRoute: String?) {
        when (currentRoute) {
            AppRoutes.ManagementTeacherScreen.route -> {
                navController.navigate(AppRoutes.CreateTeacherScreen.route)
            }

            AppRoutes.ManagementStudentScreen.route -> {
                navController.navigate(AppRoutes.CreateStudentScreen.route)
            }

            AppRoutes.ManagementClassScreen.route -> {
                navController.navigate(AppRoutes.CreateClassScreen.route)
            }

            AppRoutes.ManagementSubjectScreen.route -> {
                navController.navigate(AppRoutes.CreateSubjectScreen.route)
            }

            AppRoutes.ManagementScheduleScreen.route -> {
                navController.navigate(AppRoutes.CreateScheduleScreen.route)
            }

            AppRoutes.GradeScreen.route -> {
                navController.navigate(AppRoutes.GradeCreateScreen.route)
            }

            AppRoutes.AttendanceScreen.route -> {
                navController.navigate(AppRoutes.AttendanceCreateScreen.route)
            }

            else -> {
                println("Menambah...")
            }
        }
    }

    currentTitle = when (currentRoute) {
        AppRoutes.ManagementTeacherScreen.route -> "Manajemen Guru"
        AppRoutes.ManagementStudentScreen.route -> "Manajemen Siswa"
        AppRoutes.ManagementClassScreen.route -> "Manajemen Kelas"
        AppRoutes.ManagementSubjectScreen.route-> "Manajemen Pelajaran"
        else -> "Manajemen"
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF2563EB))
            .padding(start = 24.dp, top = 42.dp, end = 24.dp, bottom = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.chevron_backward),
                contentDescription = "image description",
                contentScale = ContentScale.None,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clickable {
                    navController.navigateUp()
                }
            )

            Text(
                text = currentTitle,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "image description",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onAddClicked(currentRoute)
                    }
            )
        }
    }
}