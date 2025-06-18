package com.example.fynda.features.services.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Service::class], version = 1)
abstract class ServiceDatabase : RoomDatabase() {
    abstract fun dao(): ServiceDao

    companion object {
        @Volatile
        private var INSTANCE: ServiceDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getServiceDatabase(context: Context): ServiceDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ServiceDatabase::class.java,
                    "service_database"
                ).build()
                INSTANCE = instance
                INSTANCE
            }
        }
    }
}