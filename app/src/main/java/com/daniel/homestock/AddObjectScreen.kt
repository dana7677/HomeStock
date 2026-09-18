package com.daniel.homestock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.material3.TextField

@Composable
fun AddObjectScreen( onBackClick: () -> Unit) {

    var nombre by remember {mutableStateOf(" ")}
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Añadir objeto")

        TextField(
            value = nombre,
            onValueChange = {
                nombre = it //Es una función lambda guardamos el valor que acabamos de introducir "it"
            },
            label = {
                Text("Nombre del objeto")
            }
        )

        Text(
            text = "Objeto: $nombre"
        )

        Button(
            onClick = {
                onBackClick()
            }
        )
        {
         Text("Volver")
        }


    }
}