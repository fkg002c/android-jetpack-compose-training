package com.fkg002c.modaldrawer.ui.temporary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fkg002c.modaldrawer.ui.element.LogLevelPreference


@Composable
fun SettingsScreen(
    padding: PaddingValues,
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
) {
    val username by viewModel.username.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsStateWithLifecycle()
    val logLevel by viewModel.logLevel.collectAsStateWithLifecycle()

    // Вызываем чистую UI-функцию
    SettingsScreenContent(
        padding = padding,
        username = username,
        isDarkMode = isDarkMode,
        notificationsEnabled = notificationsEnabled,
        logLevel = logLevel,
        onUsernameChange = { viewModel.updateUsername(it) },
        onDarkModeChange = { viewModel.updateDarkMode(it) },
        onNotificationsChange = { viewModel.updateNotifications(it) },
        onLogLevelChange = { viewModel.updateLogLevel(it) }
    )
}

// Вынесли весь UI сюда (чистая функция без ViewModel)
@Composable
fun SettingsScreenContent(
    padding: PaddingValues,
    username: String?,
    isDarkMode: Boolean,
    notificationsEnabled: Boolean,
    logLevel: Int,
    onUsernameChange: (String) -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    onNotificationsChange: (Boolean) -> Unit,
    onLogLevelChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
        /*.padding(16.dp)*/,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            "Настройки", style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
        )

        // --- TextField (EditText) ---
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge,
            value = username ?: "",
            onValueChange = { onUsernameChange(it) },
            label = { Text("Имя пользователя") },
            singleLine = true
        )

        // --- Switch ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable { onDarkModeChange(!isDarkMode) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Темная тема",
                style = MaterialTheme.typography.bodyLarge,
            )
            Switch(
                checked = isDarkMode,
                onCheckedChange = { onDarkModeChange(it) }
            )
        }

        HorizontalDivider()

        // --- Checkbox ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable { onNotificationsChange(!notificationsEnabled) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Включить уведомления",
                style = MaterialTheme.typography.bodyLarge,
            )
            Checkbox(
                checked = notificationsEnabled,
                onCheckedChange = { onNotificationsChange(it) }
            )
        }
        HorizontalDivider()
        LogLevelPreference(
            selectedLevel = logLevel,
            onLevelSelected = { onLogLevelChange(it) }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SettingsScreenPreview() {
    SettingsScreenContent(
        padding = PaddingValues(),
        username = "Иван",
        isDarkMode = true,
        notificationsEnabled = false,
        logLevel = 4,
        onUsernameChange = {},
        onDarkModeChange = {},
        onNotificationsChange = {},
        onLogLevelChange = {}
    )
}