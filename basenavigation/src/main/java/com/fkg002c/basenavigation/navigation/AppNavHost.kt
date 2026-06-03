package com.fkg002c.basenavigation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fkg002c.basenavigation.FirstScreen
import com.fkg002c.basenavigation.SecondScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Destination.First.route
    ) {
        composable(route = Destination.First.route) {
            FirstScreen(onNextClick = {
                navHostController.navigate(Destination.Second.route)
            })
        }

        composable(route = Destination.Second.route) {
            SecondScreen(onNextClick = {
//                navHostController.navigate(Destination.First.route)
                navHostController.popBackStack()
            })
        }
    }
}
