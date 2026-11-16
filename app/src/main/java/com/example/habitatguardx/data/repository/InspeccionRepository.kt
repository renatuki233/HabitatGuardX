
package com.example.habitatguardx.data.repository

import com.example.habitatguardx.data.local.InspeccionDao
import com.example.habitatguardx.data.local.InspeccionEntity
import kotlinx.coroutines.flow.Flow

class InspeccionRepository(
    private val dao: InspeccionDao
) {

    fun obtenerInspecciones(): Flow<List<InspeccionEntity>> {
        return dao.obtenerTodas()
    }

    fun contarInspecciones(): Flow<Int> {
        return dao.contarInspecciones()
    }

    fun contarRiesgosAltos(): Flow<Int> {
        return dao.contarRiesgosAltos()
    }

    suspend fun guardarInspeccion(
        inspeccion: InspeccionEntity
    ) {
        dao.insertar(inspeccion)
    }
}
