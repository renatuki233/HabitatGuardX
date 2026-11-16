
package com.example.habitatguardx.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.habitatguardx.data.local.InspeccionEntity
import com.example.habitatguardx.data.repository.InspeccionRepository
import com.example.habitatguardx.domain.usecase.EvaluarRiesgoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InspeccionViewModel(
    private val repository: InspeccionRepository
) : ViewModel() {

    private val evaluarRiesgo = EvaluarRiesgoUseCase()

    private val _guardadoExitoso = MutableStateFlow(false)
    val guardadoExitoso: StateFlow<Boolean> =
        _guardadoExitoso.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun guardarInspeccion(
        nombreArea: String,
        descripcion: String,
        gravedad: Int,
        probabilidad: Int
    ) {
        if (nombreArea.isBlank() || descripcion.isBlank()) {
            _error.value = "Completa todos los campos"
            return
        }

        _guardadoExitoso.value = false
        _error.value = null

        viewModelScope.launch {
            try {
                val nivel = evaluarRiesgo(
                    gravedad,
                    probabilidad
                )

                val fecha = SimpleDateFormat(
                    "yyyy-MM-dd HH:mm",
                    Locale.getDefault()
                ).format(Date())

                val inspeccion = InspeccionEntity(
                    nombreArea = nombreArea.trim(),
                    descripcion = descripcion.trim(),
                    fecha = fecha,
                    gravedad = gravedad,
                    probabilidad = probabilidad,
                    nivelRiesgo = nivel.name,
                    puntajeSalud = ((25 - gravedad * probabilidad) * 100) / 24
                )

                repository.guardarInspeccion(inspeccion)
                _guardadoExitoso.value = true

            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Error al guardar inspección"
            }
        }
    }

    fun limpiarEstado() {
        _guardadoExitoso.value = false
        _error.value = null
    }
}

class InspeccionViewModelFactory(
    private val repository: InspeccionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        require(
            modelClass.isAssignableFrom(
                InspeccionViewModel::class.java
            )
        )
        return InspeccionViewModel(repository) as T
    }
}
