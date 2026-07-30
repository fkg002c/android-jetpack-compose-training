package com.fkg002c.modaldrawer.ui.temporary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsManager = SettingsManager(application)

    // Оборачиваем значения SharedPreferences в StateFlow для реактивного обновления UI
    private val _username = MutableStateFlow(settingsManager.getString("username"))
    val username: StateFlow<String?> = _username.asStateFlow()

    private val _isDarkMode = MutableStateFlow(settingsManager.getBoolean("dark_mode"))
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _notificationsEnabled = MutableStateFlow(settingsManager.getBoolean("notifications"))
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()

    private val _logLevel = MutableStateFlow(settingsManager.getInt("log_level", 2))
    val logLevel: StateFlow<Int> = _logLevel.asStateFlow()

    fun updateUsername(name: String) {
        settingsManager.setString("username", name)
        _username.value = name
    }

    fun updateDarkMode(enabled: Boolean) {
        settingsManager.setBoolean("dark_mode", enabled)
        _isDarkMode.value = enabled
    }

    fun updateNotifications(enabled: Boolean) {
        settingsManager.setBoolean("notifications", enabled)
        _notificationsEnabled.value = enabled
    }

    fun updateLogLevel(level: Int) {
        settingsManager.setInt("log_level", level)
        _logLevel.value = level
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                SettingsViewModel(application)
            }
        }
    }
}
