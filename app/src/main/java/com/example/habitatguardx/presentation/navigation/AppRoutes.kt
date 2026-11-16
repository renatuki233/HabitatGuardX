
package com.example.habitatguardx.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object DashboardRoute

@Serializable
data class NuevaInspeccionRoute(
    val codigoArea: String? = null
)

@Serializable
data object ConsultarInspeccionesRoute

@Serializable
data object QRScannerRoute
