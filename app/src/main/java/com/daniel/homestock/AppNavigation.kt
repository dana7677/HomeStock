package com.daniel.homestock

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        composable(Routes.HOME) {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                onAddObjectClick = {
                    navController.navigate(Routes.ADD_OBJECT)//Creamos la funcion onAddObject para Navegar a la siguiente ventana
                }
            )
        }
        //Dirección de la ruta "add_object" el fichero AddObjectScreen...
        composable(Routes.ADD_OBJECT) {
            AddObjectScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}