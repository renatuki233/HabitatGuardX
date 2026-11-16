

package com.example.habitatguardx.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habitatguardx.domain.usecase.EvaluarRiesgoUseCase

import androidx.compose.runtime.collectAsState
import com.example.habitatguardx.presentation.viewmodel.InspeccionViewModel


@Composable
fun NuevaInspeccionScreen(
    onVolver: () -> Unit,
    viewModel: InspeccionViewModel,
    codigoArea: String? = null
) {
    val guardadoExitoso by viewModel.guardadoExitoso.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(guardadoExitoso) {
        if (guardadoExitoso) {
            viewModel.limpiarEstado()
            onVolver()
        }
    }
    var nombreArea by remember(codigoArea) {
        mutableStateOf(codigoArea.orEmpty())
    }

    var descripcion by rememberSaveable {
        mutableStateOf("")
    }

    var gravedad by rememberSaveable {
        mutableIntStateOf(1)
    }

    var probabilidad by rememberSaveable {
        mutableIntStateOf(1)
    }

    val evaluarRiesgo = remember {
        EvaluarRiesgoUseCase()
    }

    val puntaje = gravedad * probabilidad
    val nivelRiesgo = evaluarRiesgo(gravedad, probabilidad)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Nueva inspección",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = nombreArea,
            onValueChange = { nombreArea = it },
            label = { Text("Nombre del área") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        HorizontalDivider()

        Text(
            text = "Evaluación de riesgos",
            style = MaterialTheme.typography.titleLarge
        )

        Text("Gravedad: $gravedad / 5")

        Slider(
            value = gravedad.toFloat(),
            onValueChange = {
                gravedad = it.toInt().coerceIn(1, 5)
            },
            valueRange = 1f..5f,
            steps = 3
        )

        Text("Probabilidad: $probabilidad / 5")

        Slider(
            value = probabilidad.toFloat(),
            onValueChange = {
                probabilidad = it.toInt().coerceIn(1, 5)
            },
            valueRange = 1f..5f,
            steps = 3
        )

        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Resultado de evaluación",
                    style = MaterialTheme.typography.titleMedium
                )

                Text("Puntaje: $puntaje / 25")

                Text(
                    text = "Nivel de riesgo: ${nivelRiesgo.name}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }


        Button(
            onClick = {
                viewModel.guardarInspeccion(
                    nombreArea = nombreArea,
                    descripcion = descripcion,
                    gravedad = gravedad,
                    probabilidad = probabilidad
                )
            },
            enabled = nombreArea.isNotBlank() &&
                    descripcion.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar inspección")
        }
        if (error != null) {
            Text(
                text = error ?: "",
                color = MaterialTheme.colorScheme.error
            )
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Dashboard")
        }
    }
}

