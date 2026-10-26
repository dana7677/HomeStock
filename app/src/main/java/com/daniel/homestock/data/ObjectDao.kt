package com.daniel.homestock.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ObjectDao {

    @Query("SELECT * FROM objetos ORDER BY id ASC")
    fun obtenerTodos(): Flow<List<ObjectEntity>>

    @Query("SELECT * FROM objetos WHERE ubicacionId = :ubicacionId ORDER BY id ASC")
    fun obtenerPorUbicacion(ubicacionId: Int): Flow<List<ObjectEntity>>

    @Insert
    suspend fun insertar(objeto: ObjectEntity): Long

    @Update
    suspend fun actualizar(objeto: ObjectEntity)

    @Query("DELETE FROM objetos WHERE id = :id")
    suspend fun eliminarPorId(id: Int)

    @Query("UPDATE objetos SET estado = :nuevoEstado WHERE id = :id")
    suspend fun cambiarEstado(id: Int, nuevoEstado: String)

    @Query("UPDATE objetos SET nombre = :nombre, cantidad = :cantidad WHERE id = :id")
    suspend fun actualizarDatos(id: Int, nombre: String, cantidad: Int)

    @Query("UPDATE objetos SET ubicacionId = :ubicacionId WHERE id = :objetoId")
    suspend fun asignarUbicacion(objetoId: Int, ubicacionId: Int?)
}