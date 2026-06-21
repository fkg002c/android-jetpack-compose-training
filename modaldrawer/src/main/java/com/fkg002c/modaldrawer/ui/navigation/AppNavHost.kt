package com.fkg002c.modaldrawer.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fkg002c.modaldrawer.ui.screens.LocalFilesStorageScreen
import com.fkg002c.modaldrawer.ui.screens.WorkersInfoScreen
import com.fkg002c.modaldrawer.ui.screens.SettingsScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navHostController,
        startDestination = Destination.Settings.route
    ) {
        composable(route = Destination.Settings.route) { SettingsScreen(padding) }
        composable(route = Destination.WorkersInfo.route) { WorkersInfoScreen(padding) }
        composable(route = Destination.LocalFilesStorage.route) { LocalFilesStorageScreen(padding) }
    }
}
