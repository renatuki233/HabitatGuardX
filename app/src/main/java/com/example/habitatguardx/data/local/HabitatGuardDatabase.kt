
package com.example.habitatguardx.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [InspeccionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HabitatGuardDatabase : RoomDatabase() {

    abstract fun inspeccionDao(): InspeccionDao
}
