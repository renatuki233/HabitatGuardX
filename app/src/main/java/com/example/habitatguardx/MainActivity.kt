package com.example.habitatguardx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.habitatguardx.ui.theme.HabitatGuardXTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.habitatguardx.presentation.screens.NuevaInspeccionScreen

import com.example.habitatguardx.presentation.screens.ConsultarInspeccionesScreen

import androidx.lifecycle.ViewModelProvider
import com.example.habitatguardx.di.DatabaseModule
import com.example.habitatguardx.presentation.viewmodel.InspeccionViewModel
import com.example.habitatguardx.presentation.viewmodel.InspeccionViewModelFactory


import com.example.habitatguardx.presentation.screens.DashboardScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = DatabaseModule.provideRepository(
            applicationContext
        )

        val inspeccionViewModel = ViewModelProvider(
            this,
            InspeccionViewModelFactory(repository)
        )[InspeccionViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            HabitatGuardXTheme {

                var pantallaActual by rememberSaveable {
                    mutableStateOf("dashboard")
                }

                when (pantallaActual) {

                    "dashboard" -> DashboardScreen(
                        onNuevaInspeccion = {
                            pantallaActual = "nueva_inspeccion"
                        },
                        onConsultarInspecciones = {
                            pantallaActual = "consultar_inspecciones"
                        }
                    )

                    "nueva_inspeccion" -> NuevaInspeccionScreen(
                        onVolver = {
                            pantallaActual = "dashboard"
                        },
                        viewModel = inspeccionViewModel
                    )

                    "consultar_inspecciones" -> ConsultarInspeccionesScreen(
                        repository = repository,
                        onVolver = {
                            pantallaActual = "dashboard"
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HabitatGuardXTheme {
        Greeting("Android")
    }
}