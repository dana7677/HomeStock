package com.daniel.homestock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ObjectEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HomeStockDatabase : RoomDatabase() {

    abstract fun objectDao(): ObjectDao

    companion object {

        @Volatile
        private var INSTANCE: HomeStockDatabase? = null

        fun obtenerBaseDeDatos(context: Context): HomeStockDatabase {

            return INSTANCE ?: synchronized(this) {

                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    HomeStockDatabase::class.java,
                    "homestock_database"
                ).build()

                INSTANCE = instancia

                instancia
            }
        }
    }
}