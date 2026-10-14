package com.daniel.homestock.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "objetos")
data class ObjectEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val cantidad: Int,

    val estado: String
)