
package com.example.habitatguardx.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inspecciones")
data class InspeccionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreArea: String,
    val descripcion: String,
    val fecha: String,
    val gravedad: Int,
    val probabilidad: Int,
    val nivelRiesgo: String,
    val puntajeSalud: Int
)
