package com.daniel.homestock

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                onAddObjectClick = {
                    navController.navigate("add_object")
                }
            )
        }

        composable("add_object") {
            AddObjectScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}