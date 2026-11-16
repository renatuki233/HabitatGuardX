
package com.example.habitatguardx.domain.model

object CatalogoInfraestructura {

    private val areas = listOf(
        Infraestructura(
            codigoQR = "HABITATGUARD-AREA-001",
            nombre = "Laboratorio de informática",
            tipo = "Laboratorio",
            edificio = "Bloque A",
            piso = 2,
            descripcion = "Laboratorio destinado a prácticas académicas."
        ),
        Infraestructura(
            codigoQR = "HABITATGUARD-AREA-002",
            nombre = "Biblioteca central",
            tipo = "Biblioteca",
            edificio = "Bloque B",
            piso = 1,
            descripcion = "Espacio de lectura y consulta."
        ),
        Infraestructura(
            codigoQR = "HABITATGUARD-AREA-003",
            nombre = "Sala de servidores",
            tipo = "Infraestructura tecnológica",
            edificio = "Bloque C",
            piso = 1,
            descripcion = "Área destinada a equipos de servidores."
        )
    )

    fun buscarPorQR(codigo: String): Infraestructura? {
        return areas.find {
            it.codigoQR == codigo.trim()
        }
    }

    fun obtenerTodas(): List<Infraestructura> {
        return areas
    }
}
