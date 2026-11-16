
package com.example.habitatguardx

import com.example.habitatguardx.domain.model.NivelRiesgo
import com.example.habitatguardx.domain.usecase.EvaluarRiesgoUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class EvaluarRiesgoUseCaseTest {

    private val evaluarRiesgo = EvaluarRiesgoUseCase()

    @Test
    fun riesgoBajo() {
        val resultado = evaluarRiesgo(2, 2)
        assertEquals(NivelRiesgo.BAJO, resultado)
    }

    @Test
    fun riesgoMedio() {
        val resultado = evaluarRiesgo(3, 3)
        assertEquals(NivelRiesgo.MEDIO, resultado)
    }

    @Test
    fun riesgoAlto() {
        val resultado = evaluarRiesgo(4, 4)
        assertEquals(NivelRiesgo.ALTO, resultado)
    }

    @Test
    fun riesgoCritico() {
        val resultado = evaluarRiesgo(5, 5)
        assertEquals(NivelRiesgo.CRITICO, resultado)
    }

    @Test
    fun gravedadInvalida() {
        assertThrows(IllegalArgumentException::class.java) {
            evaluarRiesgo(0, 3)
        }
    }

    @Test
    fun probabilidadInvalida() {
        assertThrows(IllegalArgumentException::class.java) {
            evaluarRiesgo(3, 6)
        }
    }
}
