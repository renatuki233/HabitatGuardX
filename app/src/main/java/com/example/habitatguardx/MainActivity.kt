
package com.example.habitatguardx

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.habitatguardx.di.DatabaseModule
import com.example.habitatguardx.domain.model.CatalogoInfraestructura
import com.example.habitatguardx.presentation.navigation.*
import com.example.habitatguardx.presentation.screens.*
import com.example.habitatguardx.presentation.viewmodel.InspeccionViewModel
import com.example.habitatguardx.presentation.viewmodel.InspeccionViewModelFactory
import com.example.habitatguardx.ui.theme.HabitatGuardXTheme

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

                val navController = rememberNavController()

                fun volverAlDashboard() {
                    navController.navigate(DashboardRoute) {
                        popUpTo<DashboardRoute> {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = DashboardRoute
                ) {

                    composable<DashboardRoute> {
                        DashboardScreen(
                            repository = repository,
                            onNuevaInspeccion = {
                                navController.navigate(
                                    NuevaInspeccionRoute()
                                )
                            },
                            onConsultarInspecciones = {
                                navController.navigate(
                                    ConsultarInspeccionesRoute
                                )
                            },
                            onEscanearQR = {
                                navController.navigate(
                                    QRScannerRoute
                                )
                            }
                        )
                    }

                    composable<NuevaInspeccionRoute> { entry ->

                        val ruta =
                            entry.toRoute<NuevaInspeccionRoute>()

                        NuevaInspeccionScreen(
                            onVolver = {
                                volverAlDashboard()
                            },
                            viewModel = inspeccionViewModel,
                            codigoArea = ruta.codigoArea
                        )
                    }

                    composable<ConsultarInspeccionesRoute> {
                        ConsultarInspeccionesScreen(
                            repository = repository,
                            onVolver = {
                                volverAlDashboard()
                            }
                        )
                    }

                    composable<QRScannerRoute> {
                        QRScannerScreen(
                            onVolver = {
                                volverAlDashboard()
                            },
                            onCrearInspeccion = { codigo ->

                                val infraestructura =
                                    CatalogoInfraestructura
                                        .buscarPorQR(codigo)

                                if (infraestructura != null) {

                                    navController.navigate(
                                        NuevaInspeccionRoute(
                                            codigoArea =
                                            infraestructura.nombre
                                        )
                                    )

                                } else {

                                    Toast.makeText(
                                        this@MainActivity,
                                        "Código QR no registrado en el catálogo",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}