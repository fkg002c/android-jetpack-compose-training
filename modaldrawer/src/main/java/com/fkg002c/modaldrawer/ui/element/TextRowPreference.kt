package com.fkg002c.modaldrawer.ui.element

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextRowPreference(
    title: String,
    value: String?,
    valueMaxLength: Int,
    focusManager: FocusManager,
    inputRegex: Regex? = null,
    useLuhnCheck: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String?) -> Unit
) {
    var localValue by remember(value) { mutableStateOf(value ?: "") }

    val isRegexValid = remember(localValue, inputRegex) {
        inputRegex?.matches(localValue) ?: true
    }

    val isLuhnError = remember(localValue, useLuhnCheck) {
        if (useLuhnCheck && localValue.length == 15 && localValue.all { it.isDigit() }) {
            !checkClassicLuhn(localValue)
        } else {
            false
        }
    }

    val hasError = isLuhnError || (!isRegexValid && localValue.isNotEmpty())

    OutlinedTextField(
        value = localValue,
        onValueChange = { newValue ->
            val upperCaseValue = newValue.uppercase()
            if (upperCaseValue.length <= valueMaxLength) {
                localValue = upperCaseValue
            }
        },
        modifier = Modifier
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
        trailingIcon = {
            IconButton(
                onClick = {
                    focusManager.clearFocus()
                    onValueChange(if (localValue.isEmpty() && value == null) null else localValue)
                },
                enabled = true
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    tint = if (hasError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    contentDescription = "Save field icon"
                )
            }
        },
        isError = hasError,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onValueChange(if (localValue.isEmpty() && value == null) null else localValue)
            }
        ),
        prefix = {
            Text(
                text = title.plus(": "),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        suffix = {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = {
                    localValue = ""
                    onValueChange(null)
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear field icon",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
    )
}

private fun checkClassicLuhn(imei: String): Boolean {
    if (imei.length != 15) return false

    var sum = 0
    for (i in imei.length - 1 downTo 0) {
        var num = imei[i] - '0'
        if (num !in 0..9) return false // Страховка от не-цифр

        // Удваиваем каждый второй элемент справа налево
        if ((imei.length - 1 - i) % 2 == 1) {
            num *= 2
            if (num > 9) {
                num = num % 10 + num / 10
            }
        }
        sum += num
    }
    return sum % 10 == 0
}

@Preview
@Composable
private fun TextRowPreferencePreview() {
    TextRowPreference(
        title = "IMEI",
        value = "000000123456789",
        valueMaxLength = 16,
        focusManager = LocalFocusManager.current,
        onValueChange = {})
}