package com.daniel.homestock

import android.graphics.Color
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.daniel.homestock.model.ObjectItem
import com.daniel.homestock.model.ObjectStatus
import java.nio.file.WatchEvent
import androidx.compose.runtime.mutableStateListOf

@Composable
fun AddObjectScreen( onBackClick: () -> Unit) {

    var nombre by remember {mutableStateOf("")}
    var cantidad by remember {mutableStateOf(value="1")}
    var errorNombre by remember { mutableStateOf(value=false) }
    var errorCantidad by remember {mutableStateOf(value=false)}
    val objetos = remember { mutableStateListOf<ObjectItem>() }

    fun validarFormulario(): Boolean {
        errorNombre = false
        errorCantidad = false
        if(nombre.isBlank())
        {
            errorNombre = true
        }
        val cantidadNumero = cantidad.toIntOrNull()

        if (cantidadNumero == null || cantidadNumero <=0)
        {
            errorCantidad = true
        }

        return !errorNombre && !errorCantidad
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(1f)
        )
        {


            Text("Añadir objeto")

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            TextField(
                value = nombre,
                onValueChange = {
                    nombre = it //Es una función lambda guardamos el valor que acabamos de introducir "it"
                    errorNombre = false
                                },
                label = {
                    Text("Nombre")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (errorNombre)
            {
                Text(
                    text="El nombre es obligatorio",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer (
                modifier = Modifier.height(16.dp)
            )

            TextField(
                value = cantidad,
                onValueChange = {
                    cantidad = it
                    errorCantidad = false
                },
                label = {
                    Text("Cantidad")
                },
                //Le decimos a Android que este campo esta pensado para:
                //Introducir números, saldra un teclado numérico
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
            if(errorCantidad)
            {
                Text(
                    text="La cantidad debe ser un número mayor que 0",
                    color = MaterialTheme.colorScheme.error
                )

            }

            Spacer (
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Objeto: $nombre \n Cantidad: $cantidad"
            )

            Spacer (
                modifier = Modifier.height(24.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if(validarFormulario())
                        {
                            val cantidadNumero = cantidad.toInt()
                            val objeto = ObjectItem(
                                nombre = nombre,
                                cantidad = cantidadNumero,
                                estado = ObjectStatus.AVAILABLE
                            )
                            objetos.add(objeto)
                            //println(objeto)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text("Guardar objeto")
                }
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                Button(
                    onClick = {
                        onBackClick()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Text("Volver")
                }

            }

        }
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(objetos)
            { objeto ->
                ObjectItemRow(objeto = objeto)


            }
        }

    }
}
@Composable
fun ObjectItemRow(objeto: ObjectItem)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Text(
            text = objeto.nombre
        )
        Text(
            text = "Cantidad: ${objeto.cantidad}"
        )
        Text(
            text = objeto.estado.name
        )
    }
}