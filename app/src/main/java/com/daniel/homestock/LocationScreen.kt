package com.daniel.homestock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.daniel.homestock.data.LocationEntity
import com.daniel.homestock.viewmodel.LocationViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun LocationScreen(
    onUbicacionClick: (LocationEntity) -> Unit,
    locationViewModel: LocationViewModel = viewModel()
) {
    val ubicaciones by locationViewModel.ubicaciones.collectAsState(
        initial = emptyList()
    )

    var ubicacionActualId by remember { mutableStateOf<Int?>(null) }
    var mostrarFormulario by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Habitación") }

    val ubicacionActual = ubicaciones.find {
        it.id == ubicacionActualId
    }

    val ubicacionesVisibles = if (ubicacionActualId == null) {
        ubicaciones.filter { it.ubicacionPadreId == null }
    } else {
        ubicaciones.filter {
            it.ubicacionPadreId == ubicacionActualId
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Mis ubicaciones",
            style = MaterialTheme.typography.headlineMedium
        )

        if (ubicacionActual != null) {
            Text(
                text = "Ubicación actual: ${ubicacionActual.nombre}",
                style = MaterialTheme.typography.titleMedium
            )

            TextButton(
                onClick = {
                    ubicacionActualId = ubicacionActual.ubicacionPadreId
                }
            ) {
                Text("← Volver")
            }
        } else {
            Text(
                text = "Ubicaciones principales",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Button(
            onClick = {
                nombre = ""
                tipo = "Habitación"
                mostrarFormulario = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("+ Nueva ubicación")
        }

        if (ubicacionesVisibles.isEmpty()) {
            Text(
                text = if (ubicacionActualId == null) {
                    "Todavía no tienes ubicaciones principales."
                } else {
                    "Esta ubicación todavía está vacía."
                }
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = ubicacionesVisibles,
                key = { it.id }
            ) { ubicacion ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            ubicacionActualId = ubicacion.id
                            onUbicacionClick(ubicacion)
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = ubicacion.nombre,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = ubicacion.tipo,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Pulsa para entrar",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }

    if (mostrarFormulario) {
        AlertDialog(
            onDismissRequest = {
                mostrarFormulario = false
            },
            title = {
                Text("Nueva ubicación")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        placeholder = { Text("Ej. Dormitorio") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Tipo de ubicación",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf(
                            "Habitación",
                            "Armario",
                            "Cajón",
                            "Caja",
                            "Estantería",
                            "Otro"
                        ).forEach { opcion ->
                            TextButton(
                                onClick = { tipo = opcion }
                            ) {
                                Text(
                                    text = if (tipo == opcion) {
                                        "✓ $opcion"
                                    } else {
                                        opcion
                                    }
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nombre.isNotBlank()) {
                            locationViewModel.crearUbicacion(
                                nombre = nombre.trim(),
                                tipo = tipo,
                                ubicacionPadreId = ubicacionActualId
                            )

                            mostrarFormulario = false
                        }
                    },
                    enabled = nombre.isNotBlank()
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarFormulario = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}