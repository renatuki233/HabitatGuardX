
package com.example.habitatguardx.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun NuevaInspeccionScreen(
    onVolver: () -> Unit
) {
    var nombreArea by rememberSaveable {
        mutableStateOf("")
    }

    var descripcion by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Button(
            onClick = { },
            enabled = nombreArea.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar inspección")
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Dashboard")
        }
    }
}
