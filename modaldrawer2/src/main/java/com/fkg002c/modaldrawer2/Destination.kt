package com.fkg002c.modaldrawer2

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
)

val menuItems = listOf(
    NavigationItem(
        title = "Settings",
        icon = Icons.Filled.Settings,
        route = Screen.SETTINGS.name,
    ),
    NavigationItem(
        title = "Workers info",
        icon = Icons.Filled.Home,
        route = Screen.WORKERS_INFO.name,
    ),
    NavigationItem(
        title = "Local Files storage",
        icon = Icons.Filled.Info,
        route = Screen.LOCAL_FILES_STORAGE.name,
    ),
)
