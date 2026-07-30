package com.fkg002c.modaldrawer.ui.element

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingTextRow(
    modifier: Modifier = Modifier,
    title: String,
    value: String?,
    valueRegex: Regex,
    valueMaxLength: Int,
    focusManager: FocusManager,
    isValid: Boolean,
    onValueChange: (String?) -> Unit
) {
    OutlinedTextField(
        value = value ?: "",
        onValueChange = {
            // Remove non-numeric characters.
            val stripped = valueRegex.replace(it, "")
            if (it.length < valueMaxLength) onValueChange(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 6.dp),
        shape = RoundedCornerShape(12.dp),
        textStyle = MaterialTheme.typography.headlineSmall,
        placeholder = {
            Text(
                text = if (value == null) "(null)" else "(empty)",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Gray
            )
        },
        singleLine = true,
        leadingIcon = null,
        trailingIcon = {
            IconButton(onClick = {
                focusManager.clearFocus()
            }) { Icon(imageVector = Icons.Filled.CheckCircle, tint = MaterialTheme.colorScheme.primary, contentDescription = "Save field icon") }
        },
        isError = !isValid,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone
        ),
        prefix = {
            Text(
                text = title.plus(": "),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        suffix = {
            IconButton(
                modifier = Modifier
                    .padding(0.dp)
                    .size(24.dp),
                onClick = {
                    onValueChange(null)
                },

                ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear field icon",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp) // Уменьшите размер самой иконки
                )
            }
        },
    )
}

@Preview
@Composable
fun SettingTextRowPreview() {
    SettingTextRow(
        modifier = Modifier,
        title = "IMEI",
        value = "000000123456789",
        valueRegex = Regex(""),
        valueMaxLength = 16,
        focusManager = LocalFocusManager.current,
        isValid = true,
        onValueChange = {})
}