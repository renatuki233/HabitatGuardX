
package com.example.habitatguardx.domain.model

data class Inspeccion(
    val id: Int,
    val nombreArea: String,
    val descripcion: String,
    val fecha: String,
    val nivelRiesgo: NivelRiesgo,
    val puntajeSalud: Int
)

enum class NivelRiesgo {
    BAJO,
    MEDIO,
    ALTO,
    CRITICO
}
