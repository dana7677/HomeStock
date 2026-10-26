package com.daniel.homestock.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.homestock.data.HomeStockDatabase
import com.daniel.homestock.model.ObjectItem
import com.daniel.homestock.model.ObjectStatus
import kotlinx.coroutines.flow.map
import com.daniel.homestock.data.ObjectEntity
import kotlinx.coroutines.launch
class ObjectViewModel(application: Application): AndroidViewModel(application)
{

    private val database = HomeStockDatabase.obtenerBaseDeDatos(application)
    private val objectDao = database.objectDao()


    val objetos = objectDao.obtenerTodos().map { listaEntidades ->

        listaEntidades.map { entidad ->

            ObjectItem(
                id = entidad.id,
                nombre = entidad.nombre,
                cantidad = entidad.cantidad,
                estado = ObjectStatus.valueOf(entidad.estado)
            )
        }
    }

    //1.-Funcionalidad AñadirObjeto
    fun añadirObjeto(nombre: String, cantidad: Int) {

        viewModelScope.launch {

            val objeto = ObjectEntity(
                nombre = nombre,
                cantidad = cantidad,
                estado = ObjectStatus.AVAILABLE.name
            )

            objectDao.insertar(objeto)
        }
    }
    //2.-Funcionalidad Editar Objetos
    fun editarObjeto(id: Int, nombre: String, cantidad: Int) {

        viewModelScope.launch {

            objectDao.actualizarDatos(
                id = id,
                nombre = nombre,
                cantidad = cantidad
            )
        }
    }

    //3.-Funcionalidad EliminarObjeto
// 3.- Funcionalidad EliminarObjeto
    fun eliminarObjeto(id: Int) {

        viewModelScope.launch {
            objectDao.eliminarPorId(id)
        }
    }

    //4.-Funcionalidad CambiarDeEstado
    fun cambiarEstado(id: Int, nuevoEstado: ObjectStatus) {

        viewModelScope.launch {

            objectDao.cambiarEstado(
                id = id,
                nuevoEstado = nuevoEstado.name
            )
        }
    }

    //5.-Asignamos Ubicacion al objeto
    fun asignarUbicacion(objetoId: Int, ubicacionId: Int?) {
        viewModelScope.launch {
            objectDao.asignarUbicacion(objetoId, ubicacionId)
        }
    }
}