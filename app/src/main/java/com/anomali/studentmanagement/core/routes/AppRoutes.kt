package com.anomali.studentmanagement.core.routes

sealed class AppRoutes(val route: String) {
    object LoginScreen : AppRoutes("login")
    object RegisterScreen : AppRoutes("register")
    object LogoutScreen : AppRoutes("logout")
    object ProfileScreen : AppRoutes("profile")

    object FavoriteListScreen : AppRoutes("favorite")

    object AdminDashboardScreen : AppRoutes("admin/dashboard")
    // Management
    object ManagementScreen : AppRoutes("admin/management")
    object ManagementTeacherScreen : AppRoutes("admin/management/guru")
    object ManagementStudentScreen : AppRoutes("admin/management/siswa")
    object ManagementClassScreen : AppRoutes("admin/management/kelas")
    object ManagementSubjectScreen : AppRoutes("admin/management/subject")
    object ManagementScheduleScreen : AppRoutes("admin/management/schedule")
    // Create
    object CreateTeacherScreen : AppRoutes("admin/management/guru/create")
    object CreateStudentScreen : AppRoutes("admin/management/siswa/create")
    object CreateClassScreen : AppRoutes("admin/management/kelas/create")
    object CreateSubjectScreen : AppRoutes("admin/management/subject/create")
    object CreateScheduleScreen : AppRoutes("admin/management/schedule/create")
    // Detail
    object DetailTeacherScreen : AppRoutes("admin/management/guru/detail/{teacherId}") {
        fun teacherCreateRoute(teacherId: Int) = "admin/management/guru/detail/$teacherId"
    }
    object DetailStudentScreen : AppRoutes("admin/management/siswa/detail/{studentId}") {
        fun studentCreateRoute(studentId: Int) = "admin/management/siswa/detail/$studentId"
    }
    object DetailClassScreen : AppRoutes("admin/management/kelas/detail/{classesId}") {
        fun classesCreateRoute(classesId: Int) = "admin/management/kelas/detail/$classesId"
    }
    object DetailSubjectScreen : AppRoutes("admin/management/subject/detail/{subjectId}") {
        fun subjectCreateRoute(subjectId: Int) = "admin/management/subject/detail/$subjectId"
    }
    object DetailScheduleScreen : AppRoutes("admin/management/schedule/detail/{scheduleId}") {
        fun scheduleCreateRoute(scheduleId: Int) = "admin/management/schedule/detail/$scheduleId"
    }
    // Teacher
    object TeacherDashboardScreen : AppRoutes("teacher/dashboard")
    object GradeScreen : AppRoutes("teacher/grade")
    object GradeCreateScreen : AppRoutes("teacher/grade/create")
    object AttendanceScreen : AppRoutes("teacher/attendance")
    object AttendanceCreateScreen : AppRoutes("teacher/attendance/create")

    // Student
    object StudentDashboardScreen : AppRoutes("student/dashboard")
    object StudentGradeScreen : AppRoutes("student/grade")
    object StudentScheduleScreen : AppRoutes("student/schedule")
    object StudentAttendanceScreen : AppRoutes("student/attendance")
}