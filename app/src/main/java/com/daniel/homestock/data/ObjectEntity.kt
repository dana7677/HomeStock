package com.daniel.homestock.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "objetos",
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["ubicacionId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index("ubicacionId")
    ]
)
data class ObjectEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val cantidad: Int,

    val estado: String,

    val ubicacionId: Int? = null
)
