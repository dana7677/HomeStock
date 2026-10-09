package com.daniel.homestock.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.daniel.homestock.model.ObjectItem
import com.daniel.homestock.model.ObjectStatus


class ObjectViewModel : ViewModel()
{

    private val _objetos = mutableStateListOf<ObjectItem>()

    val objetos: List<ObjectItem>
        get() = _objetos

    private var siguienteId = 1

    //1.-Funcionalidad AñadirObjeto
    fun añadirObjeto(nombre: String, cantidad: Int) {

        val objeto = ObjectItem(
            id = siguienteId,
            nombre = nombre,
            cantidad = cantidad,
            estado = ObjectStatus.AVAILABLE
        )

        _objetos.add(objeto)

        siguienteId++
    }
    //2.-Funcionalidad Editar Objetos
    fun editarObjeto(id: Int, nombre: String, cantidad: Int) {

        val indice = _objetos.indexOfFirst { objeto ->
            objeto.id == id
        }

        if (indice != -1) {
            val objeto = _objetos[indice]

            _objetos[indice] = objeto.copy(
                nombre = nombre,
                cantidad = cantidad
            )
        }
    }

    //3.-Funcionalidad EliminarObjeto
    fun eliminarObjeto(id: Int) {
        _objetos.removeAll { objeto ->
            objeto.id == id
        }
    }

    //4.-Funcionalidad CambiarDeEstado
    fun cambiarEstado(id: Int, nuevoEstado: ObjectStatus) {

        val indice = _objetos.indexOfFirst { objeto ->
            objeto.id == id
        }

        if (indice != -1) {
            val objeto = _objetos[indice]

            _objetos[indice] = objeto.copy(
                estado = nuevoEstado
            )
        }
    }
}