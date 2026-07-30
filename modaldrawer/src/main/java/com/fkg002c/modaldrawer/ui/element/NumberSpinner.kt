package com.fkg002c.modaldrawer.ui.element

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberSpinner(
    modifier: Modifier = Modifier,
    onNumberSelected: (Int) -> Unit
) {
    // Генерируем список чисел от 2 до 7
    val numberOptions = remember { (2..7).toList() }

    var expanded by remember { mutableStateOf(false) }
    var selectedNumber by remember { mutableIntStateOf(numberOptions.first()) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedNumber.toString(),
            onValueChange = {},
            label = { Text("Выберите число") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            numberOptions.forEach { number ->
                DropdownMenuItem(
                    text = { Text(text = number.toString()) },
                    onClick = {
                        selectedNumber = number
                        onNumberSelected(number) // Передаем выбранное число в родительский компонент
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}