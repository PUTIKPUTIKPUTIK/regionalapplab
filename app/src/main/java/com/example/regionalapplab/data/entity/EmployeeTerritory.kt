package com.example.regionalapplab.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee_territories")
data class EmployeeTerritory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val employeeId: Int,
    val territoryId: Int
)
