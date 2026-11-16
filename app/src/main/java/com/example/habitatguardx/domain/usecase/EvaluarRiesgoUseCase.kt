
package com.example.habitatguardx.domain.usecase

import com.example.habitatguardx.domain.model.NivelRiesgo

class EvaluarRiesgoUseCase {

    operator fun invoke(
        gravedad: Int,
        probabilidad: Int
    ): NivelRiesgo {

        require(gravedad in 1..5) {
            "La gravedad debe estar entre 1 y 5"
        }

        require(probabilidad in 1..5) {
            "La probabilidad debe estar entre 1 y 5"
        }

        val puntaje = gravedad * probabilidad

        return when (puntaje) {
            in 1..4 -> NivelRiesgo.BAJO
            in 5..9 -> NivelRiesgo.MEDIO
            in 10..16 -> NivelRiesgo.ALTO
            else -> NivelRiesgo.CRITICO
        }
    }
}
