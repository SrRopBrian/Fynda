package com.example.fynda.features.services.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Upsert

@Entity(tableName = "services")
data class Service (
    @PrimaryKey (autoGenerate = true) val id: String,
    val name: String,
    val category: String,
    val details: String?,
    val workingDays: String,
    val openingHours: Int,
    val closingHours: Int,
    val price: String,
    val userId: String
)

@Dao
interface ServiceDao{
    @Insert
    suspend fun insertService(service: Service)

    @Query("SELECT * FROM services where userId MATCH userId")
    fun getServices(): List<Service>

    @Upsert
    suspend fun upsertService(service: Service)

    @Delete
    suspend fun deleteService(service: Service)
}

@Database(entities = [Service::class], version = 1)
abstract class ServiceDatabase : RoomDatabase() {
    abstract fun serviceDao(): ServiceDao

    companion object {
        @Volatile
        private var INSTANCE: ServiceDatabase? = null
    }
}