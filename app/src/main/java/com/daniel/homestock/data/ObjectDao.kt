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

    @Insert
    suspend fun insertar(objeto: ObjectEntity): Long

    @Update
    suspend fun actualizar(objeto: ObjectEntity)

    @Query("DELETE FROM objetos WHERE id = :id")
    suspend fun eliminarPorId(id: Int)

    @Query("UPDATE objetos SET estado = :nuevoEstado WHERE id = :id")
    suspend fun cambiarEstado(id: Int, nuevoEstado: String)
}