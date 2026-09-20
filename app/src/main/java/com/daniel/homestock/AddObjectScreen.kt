package com.daniel.homestock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.nio.file.WatchEvent

@Composable
fun AddObjectScreen( onBackClick: () -> Unit) {

    var nombre by remember {mutableStateOf(" ")}
    var cantidad by remember {mutableStateOf(value="1")}
    var errorNombre by remember { mutableStateOf(value=false) }
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
                errorNombre = false
                            },
            label = {
                Text("Nombre")
            }
        )
        if (errorNombre)
        {
            Text("El nombre es obligatorio")
        }
        Spacer (
            modifier = Modifier.height(12.dp)
        )
        TextField(
            value = cantidad,
            onValueChange = {
                cantidad = it
            },
            label = {
                Text("Cantidad")
            },
            //Le decimos a Android que este campo esta pensado para:
            //Introducir números, saldra un teclado numérico
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Text(
            text = "Objeto: $nombre \n Cantidad: $cantidad"
        )

        Button(
            onClick = {
                if(nombre.isBlank())
                {
                    errorNombre = true
                }
                else
                {
                    //Posteriormente agregarlo a la base de datos
                    println("Objeto guardado: $nombre, cantidad $cantidad")
                }
                onBackClick()
            }
        ){
            Text("Guardar objeto")
        }
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