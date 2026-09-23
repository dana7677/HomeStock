package com.daniel.homestock.model

data class ObjectItem(
    val id: Int,
    val nombre: String,
    val cantidad: Int,
    val estado: ObjectStatus,

)