package com.example.fynda.features.services.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert

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
