
package com.example.habitatguardx.di

import android.content.Context
import androidx.room.Room
import com.example.habitatguardx.data.local.HabitatGuardDatabase
import com.example.habitatguardx.data.repository.InspeccionRepository

object DatabaseModule {

    @Volatile
    private var database: HabitatGuardDatabase? = null

    fun provideDatabase(
        context: Context
    ): HabitatGuardDatabase {

        return database ?: synchronized(this) {

            database ?: Room.databaseBuilder(
                context.applicationContext,
                HabitatGuardDatabase::class.java,
                "habitatguard_database"
            ).build().also {
                database = it
            }
        }
    }

    fun provideRepository(
        context: Context
    ): InspeccionRepository {

        return InspeccionRepository(
            provideDatabase(context).inspeccionDao()
        )
    }
}
