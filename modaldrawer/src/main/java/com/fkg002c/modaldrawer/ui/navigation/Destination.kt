package com.fkg002c.modaldrawer.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(val route: String) {
    data object Settings : Destination("route_settings")
    data object LogsSchedule : Destination("route_logs_schedule")
    data object LocalDtrStorage : Destination("route_local_dtr_storage")
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
)

val menuItems = listOf(
    NavigationItem(
        title = "Settings",
        icon = Icons.Filled.Settings,
        route = Destination.Settings.route,
    ),
    NavigationItem(
        title = "Logs schedule",
        icon = Icons.Filled.Home,
        route = Destination.LogsSchedule.route,
    ),
    NavigationItem(
        title = "Local DTR Storage",
        icon = Icons.Filled.Info,
        route = Destination.LocalDtrStorage.route,
    ),
)
