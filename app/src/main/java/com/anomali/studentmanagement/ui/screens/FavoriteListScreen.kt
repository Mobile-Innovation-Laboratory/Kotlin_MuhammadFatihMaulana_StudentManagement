    package com.anomali.studentmanagement.ui.screens.favorites

//@Composable
//fun FavoriteListScreen(
//    navController: NavHostController,
//    studentRepository: StudentRepository,
//    studentDao: StudentDao
//) {
//    var favoriteStudents by remember { mutableStateOf<List<StudentEntity>>(emptyList()) }
//
//    LaunchedEffect(true) {
//        CoroutineScope(Dispatchers.IO).launch {
//            favoriteStudents = studentDao.getAllStudents().filter { it.isFavorite }
//        }
//    }
//
//    Scaffold(
//        bottomBar = { BottomNavigation(navController, 2) }
//    ) { paddingValues ->
//        Column(
//            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
//            horizontalAlignment = Alignment.Start,
//            modifier = Modifier
//                .padding(paddingValues)
//                .padding(top = 32.dp)
//                .padding(horizontal = 24.dp)
//        ) {
//            Text(
//                text = "Daftar Siswa Favorit",
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight(500),
//                    color = Color(0xFF000000),
//                    textAlign = TextAlign.Center,
//                )
//            )
//            Column(
//                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
//                horizontalAlignment = Alignment.Start,
//            ) {
//                if (favoriteStudents.isNotEmpty()) {
//                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//                        items(favoriteStudents) { studentEntity ->
//                            val student = studentEntity.toModel()
//                            StudentListItem(
//                                student = student,
//                                isFavorite = true,
//                                onFavoriteChanged = { newIsFavorite ->
//                                    CoroutineScope(Dispatchers.IO).launch {
//                                        studentRepository.updateFavoriteStatus(
//                                            student.id,
//                                            newIsFavorite
//                                        )
//                                        favoriteStudents = studentDao.getAllStudents().filter { it.isFavorite }
//                                    }
//                                },
//                                studentRepository = studentRepository
//                            )
//                        }
//                    }
//                } else {
//                    Text(text = "Tidak ada siswa favorit.")
//                }
//            }
//        }
//    }
//}