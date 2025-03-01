package com.anomali.studentmanagement.ui.navigations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun TopNavigationNoIcon(navController: NavController) {
    var currentTitle by remember { mutableStateOf("Manajemen") }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    currentTitle = when (currentRoute) {
        "guru" -> "Manajemen Guru"
        AppRoutes.CreateStudentScreen.route -> "Manajemen Siswa"
        AppRoutes.DetailStudentScreen.route -> "Manajemen Siswa"
        "kelas" -> "Manajemen Kelas"
        "pelajaran" -> "Manajemen Pelajaran"
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
                modifier = Modifier.clickable {
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
    }
}