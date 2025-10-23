package com.example.regionalapplab.data.dao

import androidx.room.*
import com.example.regionalapplab.data.entity.Territory

@Dao
interface TerritoryDao {
    @Query("SELECT * FROM territories")
    suspend fun getAll(): List<Territory>

    @Query("SELECT * FROM territories WHERE regionId = :regionId")
    fun getTerritoriesByRegion(regionId: Int): List<Territory>


    @Insert
    suspend fun insert(territory: Territory)

    @Update
    suspend fun update(territory: Territory)

    @Delete
    suspend fun delete(territory: Territory)
}
