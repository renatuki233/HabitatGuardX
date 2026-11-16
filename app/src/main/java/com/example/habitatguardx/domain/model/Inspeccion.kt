
package com.example.habitatguardx.domain.model

data class Inspeccion(
    val id: Int = 0,
    val nombreArea: String,
    val descripcion: String,
    val fecha: String,
    val gravedad: Int,
    val probabilidad: Int,
    val nivelRiesgo: NivelRiesgo,
    val puntajeSalud: Int
)

enum class NivelRiesgo {
    BAJO,
    MEDIO,
    ALTO,
    CRITICO
}
