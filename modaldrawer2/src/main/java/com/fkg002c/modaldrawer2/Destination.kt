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
        title = "Logs schedule",
        icon = Icons.Filled.Home,
        route = Screen.LOGS_SCHEDULE.name,
    ),
    NavigationItem(
        title = "Local DTR Storage",
        icon = Icons.Filled.Info,
        route = Screen.DTR_STORAGE.name,
    ),
)
