package com.anomali.studentmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.anomali.studentmanagement.ui.navigations.AppNavigation
import com.anomali.studentmanagement.ui.theme.StudentManagementTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentManagementTheme {
                AppNavigation()
            }
        }
    }
}
