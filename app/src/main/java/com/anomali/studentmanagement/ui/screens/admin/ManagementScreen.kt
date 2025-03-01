package com.anomali.studentmanagement.ui.screens.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.anomali.studentmanagement.R
import com.anomali.studentmanagement.core.routes.AppRoutes
import com.anomali.studentmanagement.ui.navigations.BottomNavigation

@Composable
fun ManagementScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavigation(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(32.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Manajemen",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF1E3A8A),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                ManagementMenuItem(
                    icon = R.drawable.person,
                    text = "Guru",
                    onClick = { navController.navigate(AppRoutes.ManagementTeacherScreen.route) }
                )
                ManagementMenuItem(
                    icon = R.drawable.group,
                    text = "Siswa",
                    onClick = { navController.navigate(AppRoutes.ManagementStudentScreen.route) }
                )
                ManagementMenuItem(
                    icon = R.drawable.school,
                    text = "Kelas",
                    onClick = { navController.navigate(AppRoutes.ManagementClassScreen.route) }
                )
                ManagementMenuItem(
                    icon = R.drawable.dictionary,
                    text = "Pelajaran",
                    onClick = { navController.navigate(AppRoutes.ManagementSubjectScreen.route) }
                )
            }
        }
    }
}

@Composable
fun ManagementMenuItem(icon: Int, text: String, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "image description",
                contentScale = ContentScale.None
            )

            Text(
                text = text,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF1E3A8A),
                    textAlign = TextAlign.Center,
                )
            )
        }

        Image(
            painter = painterResource(id = R.drawable.arrow_forward_ios),
            contentDescription = "image description",
            contentScale = ContentScale.None
        )
    }

    HorizontalDivider(color = Color(0xFF1E3A8A), thickness = 1.dp)
}