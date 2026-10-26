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
        startDestination = Routes.LOCATIONS
    ) {

        composable(Routes.HOME) {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                onAddObjectClick = {
                    navController.navigate("add_object")
                }
            )
        }

        composable(Routes.ADD_OBJECT) {
            AddObjectScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.LOCATIONS) {
            LocationScreen(
                onUbicacionClick = { ubicacion ->
                    // Más adelante navegaremos al interior
                    // de la ubicación seleccionada.
                }
            )
        }
    }
}