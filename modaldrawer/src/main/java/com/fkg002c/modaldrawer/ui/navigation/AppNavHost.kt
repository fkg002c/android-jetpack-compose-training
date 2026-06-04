package com.fkg002c.modaldrawer.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fkg002c.modaldrawer.ui.screens.LocalDtrStorageScreen
import com.fkg002c.modaldrawer.ui.screens.LogsScheduleScreen
import com.fkg002c.modaldrawer.ui.screens.SettingScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navHostController,
        startDestination = Destination.Settings.route
    ) {
        composable(route = Destination.Settings.route) { SettingScreen(padding) }
        composable(route = Destination.LogsSchedule.route) { LogsScheduleScreen(padding) }
        composable(route = Destination.LocalDtrStorage.route) { LocalDtrStorageScreen(padding) }
    }
}
