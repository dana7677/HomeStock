package com.daniel.homestock

import android.graphics.Color
import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextAlign
import com.daniel.homestock.ui.theme.GreenHomeStock
import com.daniel.homestock.ui.theme.OrangeHomeStock
import com.daniel.homestock.ui.theme.RedHomeStock
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.daniel.homestock.viewmodel.ObjectViewModel

@Composable
fun AddObjectScreen( onBackClick: () -> Unit,viewModel: ObjectViewModel = viewModel()) {


    var nombre by remember {mutableStateOf("")}
    var cantidad by remember {mutableStateOf(value="1")}
    var errorNombre by remember { mutableStateOf(value=false) }
    var errorCantidad by remember {mutableStateOf(value=false)}
    val objetos = viewModel.objetos
    var objetoEditandoId by remember { mutableStateOf<Int?>(null) }

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

          ) {
        Column(

        )
        {


            Text(
                if (objetoEditandoId == null) {
                "Añadir objeto"
            } else {
                "Editar objeto"
            })

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

            /*

            Text(
                text = "Objeto: $nombre \n Cantidad: $cantidad"
            )
            */


            Spacer (
                modifier = Modifier.height(24.dp)
            )

                Button(
                    onClick = {
                        if(validarFormulario())
                        {
                            if (objetoEditandoId != null) {
                                // Estamos editando un objeto existente.
                                // Debemos localizarlo y sustituirlo por una copia
                                // con el nuevo nombre y la nueva cantidad.
                                viewModel.editarObjeto(
                                    id = objetoEditandoId!!,
                                    nombre = nombre,
                                    cantidad = cantidad.toInt()
                                )

                                objetoEditandoId = null
                                nombre = ""
                                cantidad = "1"

                            } else {
                                // Estamos creando un objeto nuevo.
                                // Aquí irá tu código actual de creación.
                                val cantidadNumero = cantidad.toInt()

                                viewModel.añadirObjeto(
                                    nombre = nombre,
                                    cantidad = cantidadNumero
                                )

                                //Limpiar Objeto
                                nombre = ""
                                cantidad = "1"

                            }

                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                    if (objetoEditandoId == null) {
                        "Guardar objeto"
                    } else {
                        "Guardar cambios"
                    })
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
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(objetos)
            { objeto ->
                ObjectItemRow(
                    objeto = objeto,
                    objetoEditandoId= objetoEditandoId,
                    onEditClick = {

                        if(objetoEditandoId==objeto.id)
                        {
                            // Dejamos de editar
                            objetoEditandoId = null
                            nombre = ""
                            cantidad = "1"
                        }
                        else
                        {
                            nombre = objeto.nombre
                            cantidad = objeto.cantidad.toString()
                            objetoEditandoId = objeto.id
                        }

                    },
                    onDeleteClick ={
                        viewModel.eliminarObjeto(objeto.id)

                        if (objetoEditandoId == objeto.id) {
                            objetoEditandoId = null
                            nombre = ""
                            cantidad = "1"
                        }
                    },
                    onStatusChange = { nuevoEstado ->
                        viewModel.cambiarEstado(
                            id = objeto.id,
                            nuevoEstado = nuevoEstado
                        )
                    }
                )

            }
        }

    }
}

@Composable
fun ObjectItemRow(objeto: ObjectItem,
                  onDeleteClick:() -> Unit,
                  objetoEditandoId: Int?,
                  onEditClick: () -> Unit,
                  onStatusChange:(ObjectStatus)-> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                Text(
                    text = objeto.nombre,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Cantidad: ${objeto.cantidad}",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            val colorEstado = when (objeto.estado) {
                ObjectStatus.AVAILABLE -> GreenHomeStock
                ObjectStatus.LOANED -> OrangeHomeStock
                ObjectStatus.BROKEN -> RedHomeStock
            }

            Surface(
                modifier = Modifier.weight(0.5f)
                    .clickable{
                        val nuevoEstado = when (objeto.estado)
                        {
                            ObjectStatus.AVAILABLE -> ObjectStatus.LOANED
                            ObjectStatus.LOANED -> ObjectStatus.BROKEN
                            ObjectStatus.BROKEN -> ObjectStatus.AVAILABLE
                        }
                            onStatusChange(nuevoEstado)

                    },
                shape = MaterialTheme.shapes.medium,
                color = colorEstado
            ) {
                Text(
                    text = estadoTexto(objeto.estado),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(
                        horizontal = 13.dp,
                        vertical = 6.dp
                    )
                )
            }
            Spacer(
                modifier = Modifier.width(6.dp)
            )
            Column( modifier = Modifier.weight(0.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally)
            {

                Button(


                    onClick = {
                        onEditClick()
                    }
                ) {
                    Text(
                        if (objetoEditandoId == objeto.id) {
                            "Dejar de editar"
                        } else {
                            "Editar"
                        }
                    )
                }
                Button(
                    onClick = {
                        onDeleteClick()
                    }
                ){
                    Text("Eliminar")
                }
            }

        }
    }
}

fun estadoTexto(estado: ObjectStatus): String
{
    return when (estado)
    {
        ObjectStatus.AVAILABLE -> "Disponible"
        ObjectStatus.LOANED -> "Prestado"
        ObjectStatus.BROKEN -> "Roto"
    }
}

//mutableStateListOf<ObjectItem>()
fun buscarObjeto(id: Int, objetos: List<ObjectItem>):ObjectItem?
{
    return objetos.find { objeto ->
        objeto.id == id
    }
}
