
package com.example.habitatguardx.data.repository

import com.example.habitatguardx.data.local.InspeccionDao
import com.example.habitatguardx.data.mapper.toDomain
import com.example.habitatguardx.data.mapper.toEntity
import com.example.habitatguardx.domain.model.Inspeccion
import com.example.habitatguardx.domain.repository.InspeccionRepositoryContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InspeccionRepository(
    private val dao: InspeccionDao
) : InspeccionRepositoryContract {

    override fun obtenerInspecciones(): Flow<List<Inspeccion>> {
        return dao.obtenerTodas().map { entidades ->
            entidades.map { it.toDomain() }
        }
    }

    override fun contarInspecciones(): Flow<Int> {
        return dao.contarInspecciones()
    }

    override fun contarRiesgosAltos(): Flow<Int> {
        return dao.contarRiesgosAltos()
    }

    override suspend fun guardarInspeccion(
        inspeccion: Inspeccion
    ) {
        dao.insertar(inspeccion.toEntity())
    }
}
