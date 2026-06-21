package com.fkg002c.modaldrawer.ui.temporary

import android.content.Context
import androidx.core.content.edit

class SettingsManager(context: Context) {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    fun getString(key: String, defaultValue: String? = null) = preferences.getString(key, defaultValue)
    fun setString(key: String, value: String?) {
        preferences.edit(true) { value?.let { putString(key, it) } ?: run { remove(key) } }
    }

    fun getBoolean(key: String, defaultValue: Boolean = false) = preferences.getBoolean(key, defaultValue)
    fun setBoolean(key: String, value: Boolean) {
        preferences.edit { putBoolean(key, value) }
    }

    fun getInt(key: String, defaultValue: Int = 0) = preferences.getInt(key, defaultValue)
    fun setInt(key: String, value: Int) {
        preferences.edit { putInt(key, value) }
    }
}
