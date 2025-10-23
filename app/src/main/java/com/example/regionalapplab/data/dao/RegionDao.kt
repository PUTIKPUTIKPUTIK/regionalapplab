package com.example.regionalapplab.data.dao

import androidx.room.*
import com.example.regionalapplab.data.entity.Region

@Dao
interface RegionDao {
    @Query("SELECT * FROM regions")
    suspend fun getAll(): List<Region>

    @Insert
    suspend fun insert(region: Region)

    @Update
    suspend fun update(region: Region)

    @Delete
    suspend fun delete(region: Region)
}
