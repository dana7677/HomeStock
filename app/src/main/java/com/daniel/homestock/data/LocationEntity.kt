package com.daniel.homestock.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ubicaciones",
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["ubicacionPadreId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index("ubicacionPadreId")
    ]
)
data class LocationEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val tipo: String,

    val ubicacionPadreId: Int? = null
)