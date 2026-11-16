
package com.example.habitatguardx.domain.repository

import com.example.habitatguardx.domain.model.Inspeccion
import kotlinx.coroutines.flow.Flow

interface InspeccionRepositoryContract {

    fun obtenerInspecciones(): Flow<List<Inspeccion>>

    fun contarInspecciones(): Flow<Int>

    fun contarRiesgosAltos(): Flow<Int>

    suspend fun guardarInspeccion(
        inspeccion: Inspeccion
    )
}
