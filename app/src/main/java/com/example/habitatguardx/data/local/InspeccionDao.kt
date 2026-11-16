
package com.example.habitatguardx.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InspeccionDao {

    @Insert
    suspend fun insertar(
        inspeccion: InspeccionEntity
    )

    @Query("SELECT * FROM inspecciones ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<InspeccionEntity>>

    @Query("SELECT COUNT(*) FROM inspecciones")
    fun contarInspecciones(): Flow<Int>

    @Query(
        "SELECT COUNT(*) FROM inspecciones " +
                "WHERE nivelRiesgo IN ('ALTO', 'CRITICO')"
    )
    fun contarRiesgosAltos(): Flow<Int>
}
