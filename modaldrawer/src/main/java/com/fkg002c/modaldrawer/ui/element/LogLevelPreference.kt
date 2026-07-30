package com.fkg002c.modaldrawer.ui.element

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogLevelPreference(
    selectedLevel: Int,
    onLevelSelected: (Int) -> Unit
) {
    // Список доступных уровней логирования от 2 до 7
    val logLevels = (2..7).toList()
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "Уровень логирования в файл",
            style = MaterialTheme.typography.bodyLarge,
        )

        ExposedDropdownMenuBox(
            modifier = Modifier.padding(end = 20.dp),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                // Отображаем текущее выбранное значение
                value = selectedLevel.toString(),
                textStyle = MaterialTheme.typography.bodyLarge,
                onValueChange = {},
                shape = RoundedCornerShape(12.dp),
                readOnly = true, // Запрещаем ручной ввод клавиатурой
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth().padding(16.dp, 6.dp)
                    .menuAnchor(), // Важно для корректного позиционирования меню
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                logLevels.forEach { level ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = level.toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            onLevelSelected(level)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun LogLevelPreferencePreview() {
    LogLevelPreference(7, {})
}