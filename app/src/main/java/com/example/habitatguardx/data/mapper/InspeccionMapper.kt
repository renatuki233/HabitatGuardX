
package com.example.habitatguardx.data.mapper

import com.example.habitatguardx.data.local.InspeccionEntity
import com.example.habitatguardx.domain.model.Inspeccion
import com.example.habitatguardx.domain.model.NivelRiesgo

fun InspeccionEntity.toDomain(): Inspeccion {
    return Inspeccion(
        id = id,
        nombreArea = nombreArea,
        descripcion = descripcion,
        fecha = fecha,
        gravedad = gravedad,
        probabilidad = probabilidad,
        nivelRiesgo = NivelRiesgo.valueOf(nivelRiesgo),
        puntajeSalud = puntajeSalud
    )
}

fun Inspeccion.toEntity(): InspeccionEntity {
    return InspeccionEntity(
        id = id,
        nombreArea = nombreArea,
        descripcion = descripcion,
        fecha = fecha,
        gravedad = gravedad,
        probabilidad = probabilidad,
        nivelRiesgo = nivelRiesgo.name,
        puntajeSalud = puntajeSalud
    )
}
