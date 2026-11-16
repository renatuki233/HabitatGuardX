
package com.example.habitatguardx.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(
    onNuevaInspeccion: () -> Unit,
    onConsultarInspecciones: () -> Unit
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "HabitatGuard X",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Sistema inteligente de monitoreo",
            style = MaterialTheme.typography.bodyMedium
        )

        HorizontalDivider()

        Text(
            text = "Panel de control",
            style = MaterialTheme.typography.titleLarge
        )

        TarjetaIndicador(
            titulo = "Inspecciones registradas",
            valor = "0"
        )

        TarjetaIndicador(
            titulo = "Riesgos detectados",
            valor = "0"
        )

        TarjetaIndicador(
            titulo = "Campus Health Score",
            valor = "Sin datos"
        )

        Text(
            text = "Gestión de infraestructura",
            style = MaterialTheme.typography.titleLarge
        )

        Button(
            onClick = onNuevaInspeccion,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nueva inspección")
        }

        OutlinedButton(
            onClick = onConsultarInspecciones,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Consultar inspecciones")
        }

        OutlinedButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Escanear código QR")
        }
    }
}

@Composable
fun TarjetaIndicador(
    titulo: String,
    valor: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = valor,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
