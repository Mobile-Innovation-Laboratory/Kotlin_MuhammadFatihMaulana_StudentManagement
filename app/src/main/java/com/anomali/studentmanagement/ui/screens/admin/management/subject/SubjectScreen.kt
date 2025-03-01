package com.anomali.studentmanagement.ui.screens.admin.management.subject

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
import androidx.compose.foundation.lazy.items
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
import com.anomali.studentmanagement.core.routes.AppRoutes
import com.anomali.studentmanagement.data.model.Subject
import com.anomali.studentmanagement.data.repository.admin.SubjectRepository
import com.anomali.studentmanagement.ui.components.EditButton
import com.anomali.studentmanagement.ui.navigations.TopNavigation

@Composable
fun SubjectScreen(navController: NavController, subjectRepository: SubjectRepository) {
    var subjects by remember { mutableStateOf<List<Subject>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = Unit) {
        isLoading = true
        try {
            val response = subjectRepository.getSubjects()
            if (response.isSuccessful) {
                subjects = response.body() ?: emptyList()
            } else {
                errorMessage = "Error: ${response.message()}"
            }
        } catch (e: Exception) {
            errorMessage = "Error: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = { TopNavigation(navController) },
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
                Text(text = errorMessage!!, color = Color.Red)
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(subjects) { subject ->
                        SubjectItem(subject = subject, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun SubjectItem(subject: Subject, navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = subject.name,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            )
        )
        EditButton(onClick = { navController.navigate(
            AppRoutes.DetailSubjectScreen.subjectCreateRoute(subject.id)
        ) })
    }
}

//class DummySubjectRepository : SubjectRepository {
//    override suspend fun getSubjects(): Response<List<Subject>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getSubjectById(id: Int): Response<Subject> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun createSubject(name: String): Response<Subject> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun updateSubject(id: Int, name: String): Response<Subject> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun deleteSubject(id: Int): Response<Unit> {
//        TODO("Not yet implemented")
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SubjectScreenPreview() {
//    val navController = rememberNavController()
//    val subjectRepository = DummySubjectRepository()
//    SubjectScreen(navController = navController, subjectRepository = subjectRepository)
//}
