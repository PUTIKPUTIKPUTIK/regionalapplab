package com.example.regionalapplab.data.dao

import androidx.room.*
import com.example.regionalapplab.data.entity.EmployeeTerritory

@Dao
interface EmployeeTerritoryDao {
    @Query("SELECT * FROM employee_territories")
    suspend fun getAll(): List<EmployeeTerritory>

    @Insert
    suspend fun insert(employeeTerritory: EmployeeTerritory)

    @Update
    suspend fun update(employeeTerritory: EmployeeTerritory)

    @Delete
    suspend fun delete(employeeTerritory: EmployeeTerritory)
}
