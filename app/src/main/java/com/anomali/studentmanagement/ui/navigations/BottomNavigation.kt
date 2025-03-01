package com.anomali.studentmanagement.ui.navigations

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anomali.studentmanagement.R
import com.anomali.studentmanagement.core.routes.AppRoutes
import com.anomali.studentmanagement.core.utils.PreferencesUtils

@Composable
fun BottomNavigation(navController: NavController) {
    val context = LocalContext.current
    val role = PreferencesUtils.getUserRoleFromPreferences(context)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val menuItems = when (role) {
        "admin" -> listOf(
            Triple(
                AppRoutes.AdminDashboardScreen,
                R.drawable.home_on,
                R.drawable.home_off
            ) to "Dashboard",
            Triple(
                AppRoutes.ManagementScreen,
                R.drawable.management_on,
                R.drawable.management_off
            ) to "Manajemen",
            Triple(
                AppRoutes.FavoriteListScreen,
                R.drawable.settings_on,
                R.drawable.settings_off
            ) to "Favorite",
            Triple(
                AppRoutes.ManagementScheduleScreen,
                R.drawable.calendar_on,
                R.drawable.calendar_off
            ) to "Jadwal",
            Triple(
                AppRoutes.ProfileScreen,
                R.drawable.person,
                R.drawable.person_on
            ) to "Profil"
        )

        "guru" -> listOf(
            Triple(
                AppRoutes.TeacherDashboardScreen,
                R.drawable.home_on,
                R.drawable.home_off
            ) to "Dashboard",
            Triple(
                AppRoutes.GradeScreen,
                R.drawable.management_on,
                R.drawable.management_off
            ) to "Penilaian",
            Triple(
                AppRoutes.AttendanceScreen,
                R.drawable.calendar_on,
                R.drawable.calendar_off
            ) to "Absen",
            Triple(
                AppRoutes.ProfileScreen,
                R.drawable.person,
                R.drawable.person_on
            ) to "Profil"
        )

        "siswa" -> listOf(
            Triple(
                AppRoutes.StudentDashboardScreen,
                R.drawable.home_on,
                R.drawable.home_off
            ) to "Dashboard",
            Triple(
                AppRoutes.StudentScheduleScreen,
                R.drawable.calendar_on,
                R.drawable.calendar_off
            ) to "Jadwal",
            Triple(
                AppRoutes.StudentGradeScreen,
                R.drawable.management_on,
                R.drawable.management_off
            ) to "Penilaian",
            Triple(
                AppRoutes.StudentAttendanceScreen,
                R.drawable.calendar_on,
                R.drawable.calendar_off
            ) to "Absen",
            Triple(
                AppRoutes.ProfileScreen,
                R.drawable.person,
                R.drawable.person_on
            ) to "Profil"
        )

        else -> emptyList()
    }

    // Mendapatkan index halaman yang aktif
    val currentPageIndex = getPageIndex(currentRoute, menuItems)

    NavigationBar(containerColor = Color.White) {
        menuItems.forEachIndexed { index, item ->
            val (routeIcon, label) = item
            val (route, fillIcon, outlineIcon) = routeIcon
            val selected = index == currentPageIndex

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = if (selected) fillIcon else outlineIcon),
                        contentDescription = label,
                        tint = Color(0xFF2563EB)
                    )
                },
                selected = selected,
                label = { Text(label, fontSize = 9.sp, color = Color(0xFF2563EB)) },
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(route.route) {
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}

fun getPageIndex(
    currentRoute: String?,
    menuItems: List<Pair<Triple<AppRoutes, Int, Int>, String>>
): Int {
    val routeList = menuItems.map { it.first.first.route }
    return routeList.indexOf(currentRoute).takeIf { it >= 0 } ?: 0
}
