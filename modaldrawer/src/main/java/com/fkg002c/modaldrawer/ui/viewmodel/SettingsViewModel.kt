package com.fkg002c.modaldrawer.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fkg002c.modaldrawer.ui.temporary.SettingsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(private val application: Application) : AndroidViewModel(application) {
    private val manager = SettingsManager(application)

    // FEATURE_ENABLED
    private val _isFeatureEnabled = MutableStateFlow(manager.getBoolean("FEATURE_ENABLED", false))
    val isFeatureEnabled: StateFlow<Boolean> = _isFeatureEnabled.asStateFlow()

    fun updateFeatureEnabled(enabled: Boolean) {
        manager.setBoolean("FEATURE_ENABLED", enabled)
        _isFeatureEnabled.value = enabled
    }

    // DIALOG_ENABLED
    private val _isDialogEnabled = MutableStateFlow(manager.getBoolean("DIALOG_ENABLED", false))
    val isDialogEnabled: StateFlow<Boolean> = _isDialogEnabled.asStateFlow()

    fun updateDialogEnabled(enabled: Boolean) {
        manager.setBoolean("DIALOG_ENABLED", enabled)
        _isDialogEnabled.value = enabled
    }

    // EXT_DIALOG_ENABLED
    private val _isExtDialogEnabled = MutableStateFlow(manager.getBoolean("EXT_DIALOG_ENABLED", false))
    val isExtDialogEnabled: StateFlow<Boolean> = _isExtDialogEnabled.asStateFlow()

    fun updateExtDialogEnabled(enabled: Boolean) {
        manager.setBoolean("EXT_DIALOG_ENABLED", enabled)
        _isExtDialogEnabled.value = enabled
    }

    // PHONE_NUMBER
    private val _mdn = MutableStateFlow(manager.getString("PHONE_NUMBER", null))
    val mdn: StateFlow<String?> = _mdn.asStateFlow()
    fun updateMdn(mdn: String?) {
        manager.setString("PHONE_NUMBER", mdn)
        _mdn.value = mdn
    }

    // IMEI
    private val _imei = MutableStateFlow(manager.getString("IMEI", null))
    val imei: StateFlow<String?> = _imei.asStateFlow()
    fun updateImei(imei: String?) {
        manager.setString("IMEI", imei)
        _imei.value = imei
    }

    // LOG_LEVEL
    private val _logLevel = MutableStateFlow(manager.getInt("LOG_LEVEL", 2))
    val logLevel: StateFlow<Int> = _logLevel.asStateFlow()

    fun updateLogLevel(level: Int) {
        manager.setInt("LOG_LEVEL", level)
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
