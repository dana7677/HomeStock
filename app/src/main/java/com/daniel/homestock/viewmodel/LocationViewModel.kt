package com.daniel.homestock.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.homestock.data.HomeStockDatabase
import com.daniel.homestock.data.LocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val database = HomeStockDatabase.obtenerBaseDeDatos(application)
    private val locationDao = database.locationDao()

    val ubicaciones: Flow<List<LocationEntity>> =
        locationDao.obtenerTodas()

    fun crearUbicacion(
        nombre: String,
        tipo: String,
        ubicacionPadreId: Int? = null
    ) {
        viewModelScope.launch {

            val ubicacion = LocationEntity(
                nombre = nombre,
                tipo = tipo,
                ubicacionPadreId = ubicacionPadreId
            )

            locationDao.insertar(ubicacion)
        }
    }


    fun moverUbicacion(
        ubicacionId: Int,
        nuevoPadreId: Int?,
        onResultado: (Boolean) -> Unit = {}
    ) {
        viewModelScope.launch {

            // 1. Comprobar que la ubicación existe.
            val ubicacion = locationDao.obtenerPorId(ubicacionId)

            if (ubicacion == null) {
                onResultado(false)
                return@launch
            }

            // 2. Evitar que una ubicación sea su propio padre.
            if (nuevoPadreId == ubicacionId) {
                onResultado(false)
                return@launch
            }

            if (nuevoPadreId != null) {

                // 3. Comprobar que el nuevo padre existe.
                val nuevoPadre = locationDao.obtenerPorId(nuevoPadreId)

                if (nuevoPadre == null) {
                    onResultado(false)
                    return@launch
                }

                // 4. Obtener todos los descendientes de la ubicación.
                val descendientes =
                    locationDao.obtenerDescendientes(ubicacionId)

                // 5. Evitar mover una ubicación dentro de sí misma.
                if (nuevoPadreId in descendientes) {
                    onResultado(false)
                    return@launch
                }
            }

            // 6. Actualizar únicamente la ubicación padre.
            val ubicacionActualizada = ubicacion.copy(
                ubicacionPadreId = nuevoPadreId
            )

            locationDao.actualizar(ubicacionActualizada)

            onResultado(true)
        }
    }


}
