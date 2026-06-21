package com.fkg002c.modaldrawer.ui.element

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSelectorPreference(
    title: String,
    indexToValueMap: Map<Int, String>,
    selectedIndex: Int,
    onIndexSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .padding(end = 6.dp),
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .width(180.dp)
                    .menuAnchor(
                        type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                        enabled = true
                    ),
                value = "$selectedIndex — ${indexToValueMap.get(selectedIndex)}",
                textStyle = MaterialTheme.typography.bodyLarge,
                onValueChange = {},
                shape = RoundedCornerShape(12.dp),
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(190.dp)
            ) {
                indexToValueMap.forEach { (index, name) ->
                    val isDisabled = index == 7
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "$index — $name",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            onIndexSelected(index)
                            expanded = false
                        },
                        colors = if (isDisabled) {
                            MenuDefaults.itemColors(
                                textColor = Color.Red,          // Красный цвет текста
                                leadingIconColor = Color.Red,   // Красный цвет иконки слева (если есть)
                                trailingIconColor = Color.Red   // Красный цвет иконки справа (если есть)
                            )
                        } else {
                            MenuDefaults.itemColors() // Цвета по умолчанию из текущей темы
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
fun MapSelectorPreferencePreview() {
    MapSelectorPreference(
        title = "Logging level",
        indexToValueMap = mapOf(2 to "VERBOSE"),
        selectedIndex = 7,
        onIndexSelected = {})
}