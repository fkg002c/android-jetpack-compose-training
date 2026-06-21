package com.fkg002c.modaldrawer.ui.screens

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fkg002c.modaldrawer.ui.element.MapSelectorPreference
import com.fkg002c.modaldrawer.ui.element.SettingCheckboxRow
import com.fkg002c.modaldrawer.ui.element.SwitchRowPreference
import com.fkg002c.modaldrawer.ui.element.TextRowPreference
import com.fkg002c.modaldrawer.ui.viewmodel.SettingsViewModel
import kotlin.collections.mapOf

@Composable
fun SettingsScreen(
    padding: PaddingValues,
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
) {
    val isFeatureEnabled by viewModel.isFeatureEnabled.collectAsStateWithLifecycle()
    val isDialogEnabled by viewModel.isDialogEnabled.collectAsStateWithLifecycle()
    val isExtDialogEnabled by viewModel.isExtDialogEnabled.collectAsStateWithLifecycle()
    val mdn by viewModel.mdn.collectAsStateWithLifecycle()
    val imei by viewModel.imei.collectAsStateWithLifecycle()
    val logLevel by viewModel.logLevel.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
    ) {
        SettingScreenContent(
            isFeatureEnabled = isFeatureEnabled,
            isDialogEnabled = isDialogEnabled,
            isExtDialogEnabled = isExtDialogEnabled,
            mdn = mdn,
            imei = imei,
            logLevel = logLevel,
            onFeatureChanged = { viewModel.updateFeatureEnabled(it) },
            onDialogChanged = { viewModel.updateDialogEnabled(it) },
            onExtDialogChanged = { viewModel.updateExtDialogEnabled(it) },
            onMdnChanged = { viewModel.updateMdn(it) },
            onImeiChanged = { newImei ->
                viewModel.updateImei(newImei)
                Log.d("SettingsScreen", "IMEI changed to $newImei")
            },
            onLogLevelChanged = { viewModel.updateLogLevel(it) },
            onRunCommand = { command ->
                when (command) {
                    Command.Command1 -> {}
                    Command.Command2 -> {}
                    Command.Command3 -> {}
                }
            }
        )
    }
}

@Composable
fun SettingScreenContent(
    isFeatureEnabled: Boolean,
    isDialogEnabled: Boolean,
    isExtDialogEnabled: Boolean,
    mdn: String?,
    imei: String?,
    logLevel: Int,
    onFeatureChanged: (Boolean) -> Unit,
    onDialogChanged: (Boolean) -> Unit,
    onExtDialogChanged: (Boolean) -> Unit,
    onMdnChanged: (String?) -> Unit,
    onImeiChanged: (String?) -> Unit,
    onLogLevelChanged: (Int) -> Unit,
    onRunCommand: (Command) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        item {
            SwitchRowPreference(
                title = "Enable Feature",
                checked = isFeatureEnabled
            ) { onFeatureChanged(it) }
        }
        item {
            SettingCheckboxRow(
                title = "Show User Dialog",
                checked = isDialogEnabled
            ) { onDialogChanged(it) }
        }
        item {
            SettingCheckboxRow(
                title = "Show User extra Dialog",
                checked = isExtDialogEnabled,
            ) { onExtDialogChanged(it) }
        }
        item {
            TextRowPreference(
                title = "MDN",
                value = mdn,
                valueMaxLength = 14,
                focusManager = focusManager,
                inputRegex = Regex("^\\+?\\d{10,11}$"),
                keyboardType = KeyboardType.Phone,
                onValueChange = { onMdnChanged(it) }
            )
        }
        item {
            TextRowPreference(
                title = "IMEI",
                value = imei,
                valueMaxLength = 16,
                focusManager = focusManager,
                useLuhnCheck = true,
                keyboardType = KeyboardType.Text,
            ) { onImeiChanged(it) }
        }

        item {
            MapSelectorPreference(
                title = "Logging level",
                indexToValueMap = mapOf(
                    7 to "DISABLED",
                    2 to "VERBOSE",
                    3 to "DEBUG",
                    4 to "INFO",
                    5 to "WARN",
                    6 to "ERROR",
                ),
                selectedIndex = logLevel,
            ) { onLogLevelChanged(it) }
        }

        item {
            HorizontalDivider(
                modifier = Modifier.padding(12.dp)
            )
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Execute command", style = MaterialTheme.typography.bodyLarge)
                Row {
                    Button(onClick = { onRunCommand(Command.Command1) }, Modifier.padding(horizontal = 4.dp)) { Text("Cmd 1") }
                    Button(onClick = { onRunCommand(Command.Command2) }) { Text("Cmd 2") }
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { onRunCommand(Command.Command3) }) { Text("Cmd 3") }
            }
        }
    }
}

enum class Command {
    Command1,
    Command2,
    Command3,
}

@Preview(showSystemUi = true)
@Composable
fun SettingScreenContentPreview() {
    Scaffold { systemPadding ->
        Box(
            modifier = Modifier.padding(systemPadding)
        ) {
            SettingScreenContent(
                isFeatureEnabled = true,
                isDialogEnabled = false,
                isExtDialogEnabled = true,
                mdn = "+79991112233",
                imei = "123456789012345",
                logLevel = 2,
                onFeatureChanged = {},
                onDialogChanged = {},
                onExtDialogChanged = {},
                onMdnChanged = {},
                onImeiChanged = {},
                onLogLevelChanged = {},
                onRunCommand = {}
            )
        }
    }
}
