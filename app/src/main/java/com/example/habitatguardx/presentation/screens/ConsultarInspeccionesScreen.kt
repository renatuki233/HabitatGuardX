
package com.example.habitatguardx.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habitatguardx.domain.model.Inspeccion
import com.example.habitatguardx.domain.repository.InspeccionRepositoryContract

@Composable
fun ConsultarInspeccionesScreen(
    repository: InspeccionRepositoryContract,
    onVolver: () -> Unit
){
    val inspecciones by repository
        .obtenerInspecciones()
        .collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Inspecciones registradas",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Dashboard")
        }

        if (inspecciones.isEmpty()) {
            Text("Todavía no existen inspecciones registradas.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = inspecciones,
                    key = { it.id }
                ) { inspeccion ->
                    TarjetaInspeccion(inspeccion)
                }
            }
        }
    }
}

@Composable
private fun TarjetaInspeccion(
    inspeccion: Inspeccion
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = inspeccion.nombreArea,
                style = MaterialTheme.typography.titleLarge
            )

            Text(inspeccion.descripcion)
            Text("Fecha: ${inspeccion.fecha}")
            Text("Gravedad: ${inspeccion.gravedad}/5")
            Text("Probabilidad: ${inspeccion.probabilidad}/5")
            Text("Riesgo: ${inspeccion.nivelRiesgo}")
            Text("Health Score: ${inspeccion.puntajeSalud}/100")
        }
    }
}
