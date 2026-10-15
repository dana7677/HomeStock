package com.daniel.homestock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        ObjectEntity::class,
        LocationEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class HomeStockDatabase : RoomDatabase() {

    abstract fun objectDao(): ObjectDao

    abstract fun locationDao(): LocationDao

    companion object {

        @Volatile
        private var INSTANCE: HomeStockDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {

            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS `ubicaciones` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `nombre` TEXT NOT NULL,
                `tipo` TEXT NOT NULL,
                `ubicacionPadreId` INTEGER
            )
            """.trimIndent()
                )

                database.execSQL(
                    "ALTER TABLE `objetos` ADD COLUMN `ubicacionId` INTEGER"
                )
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {

            override fun migrate(database: SupportSQLiteDatabase) {

                // Creamos una nueva tabla con la clave foránea.
                database.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS `ubicaciones_nueva` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `nombre` TEXT NOT NULL,
                `tipo` TEXT NOT NULL,
                `ubicacionPadreId` INTEGER,
                FOREIGN KEY(`ubicacionPadreId`)
                    REFERENCES `ubicaciones_nueva`(`id`)
                    ON UPDATE NO ACTION
                    ON DELETE RESTRICT
            )
            """.trimIndent()
                )

                // Copiamos los datos de la tabla anterior.
                database.execSQL(
                    """
            INSERT INTO `ubicaciones_nueva`
                (`id`, `nombre`, `tipo`, `ubicacionPadreId`)
            SELECT
                `id`, `nombre`, `tipo`, `ubicacionPadreId`
            FROM `ubicaciones`
            """.trimIndent()
                )

                // Sustituimos la tabla anterior por la nueva.
                database.execSQL("DROP TABLE `ubicaciones`")

                database.execSQL(
                    "ALTER TABLE `ubicaciones_nueva` RENAME TO `ubicaciones`"
                )

                // Creamos el índice definido en LocationEntity.
                database.execSQL(
                    """
            CREATE INDEX IF NOT EXISTS
            `index_ubicaciones_ubicacionPadreId`
            ON `ubicaciones` (`ubicacionPadreId`)
            """.trimIndent()
                )
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {

            override fun migrate(database: SupportSQLiteDatabase) {

                // 1. Creamos la tabla con la nueva estructura.
                database.execSQL(
                    """
            CREATE TABLE `objetos_nueva` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `nombre` TEXT NOT NULL,
                `cantidad` INTEGER NOT NULL,
                `estado` TEXT NOT NULL,
                `ubicacionId` INTEGER,
                FOREIGN KEY(`ubicacionId`)
                    REFERENCES `ubicaciones`(`id`)
                    ON UPDATE NO ACTION
                    ON DELETE RESTRICT
            )
            """.trimIndent()
                )

                // 2. Copiamos todos los objetos existentes.
                database.execSQL(
                    """
            INSERT INTO `objetos_nueva`
                (`id`, `nombre`, `cantidad`, `estado`, `ubicacionId`)
            SELECT
                `id`, `nombre`, `cantidad`, `estado`, `ubicacionId`
            FROM `objetos`
            """.trimIndent()
                )

                // 3. Sustituimos la tabla antigua.
                database.execSQL("DROP TABLE `objetos`")

                database.execSQL(
                    "ALTER TABLE `objetos_nueva` RENAME TO `objetos`"
                )

                // 4. Creamos el índice de la ubicación.
                database.execSQL(
                    """
            CREATE INDEX `index_objetos_ubicacionId`
            ON `objetos` (`ubicacionId`)
            """.trimIndent()
                )
            }
        }
        fun obtenerBaseDeDatos(context: Context): HomeStockDatabase {

            return INSTANCE ?: synchronized(this) {

                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    HomeStockDatabase::class.java,
                    "homestock_database"
                )
                    .addMigrations(MIGRATION_1_2,MIGRATION_2_3,MIGRATION_3_4)
                    .build()

                INSTANCE = instancia

                instancia
            }
        }
    }
}