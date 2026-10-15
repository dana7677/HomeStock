package com.daniel.homestock.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    // Obtener todas las ubicaciones.
    @Query("SELECT * FROM ubicaciones ORDER BY id ASC")
    fun obtenerTodas(): Flow<List<LocationEntity>>

    // Obtener las ubicaciones principales.
    @Query("SELECT * FROM ubicaciones WHERE ubicacionPadreId IS NULL ORDER BY id ASC")
    fun obtenerPrincipales(): Flow<List<LocationEntity>>

    // Obtener las ubicaciones hijas de una ubicación.
    @Query("SELECT * FROM ubicaciones WHERE ubicacionPadreId = :padreId ORDER BY id ASC")
    fun obtenerHijas(padreId: Int): Flow<List<LocationEntity>>

    // Insertar una nueva ubicación.
    @Insert
    suspend fun insertar(ubicacion: LocationEntity): Long

    // Actualizar una ubicación existente.
    @Update
    suspend fun actualizar(ubicacion: LocationEntity)

    // Eliminar una ubicación por su identificador.
    @Query("DELETE FROM ubicaciones WHERE id = :id")
    suspend fun eliminarPorId(id: Int)

    // Comprobar si una ubicación tiene ubicaciones hijas.
    @Query("SELECT EXISTS(SELECT 1 FROM ubicaciones WHERE ubicacionPadreId = :id)")
    suspend fun tieneHijas(id: Int): Boolean

    // Comprobar si una ubicación contiene objetos.
    @Query("SELECT EXISTS(SELECT 1 FROM objetos WHERE ubicacionId = :id)")
    suspend fun tieneObjetos(id: Int): Boolean

    @Query("SELECT * FROM ubicaciones WHERE id = :id")
    suspend fun obtenerPorId(id: Int): LocationEntity?


    @Query("""
    WITH RECURSIVE descendientes(id) AS (
        SELECT id
        FROM ubicaciones
        WHERE ubicacionPadreId = :id

        UNION ALL

        SELECT u.id
        FROM ubicaciones u
        INNER JOIN descendientes d
            ON u.ubicacionPadreId = d.id
    )
    SELECT id FROM descendientes
""")
    suspend fun obtenerDescendientes(id: Int): List<Int>
}
