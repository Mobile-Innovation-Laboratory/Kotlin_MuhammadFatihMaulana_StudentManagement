package com.anomali.studentmanagement.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.anomali.studentmanagement.data.model.Classes
import com.anomali.studentmanagement.data.repository.admin.ClassesRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDropdown(
    classRepository: ClassesRepository,
    selectedClassId: MutableState<Int?>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val classList = remember { mutableStateOf(emptyList<Classes>()) }
    val expanded = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val response = classRepository.getClasses()
            if (response.isSuccessful) {
                classList.value = response.body() ?: emptyList()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it }
    ) {
        OutlinedTextField(
            value = classList.value.find { it.id == selectedClassId.value }?.name ?: "Pilih Kelas",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            classList.value.forEach { classItem ->
                DropdownMenuItem(
                    text = { Text(classItem.name) },
                    onClick = {
                        selectedClassId.value = classItem.id
                        expanded.value = false
                    }
                )
            }
        }
    }

}