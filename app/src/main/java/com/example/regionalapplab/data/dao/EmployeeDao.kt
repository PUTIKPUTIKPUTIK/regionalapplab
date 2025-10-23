package com.example.regionalapplab.data

import androidx.room.*
import com.example.regionalapplab.data.entity.Employee

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employees")
    suspend fun getAll(): List<Employee>

    @Insert
    suspend fun insert(employee: Employee)

    @Update
    suspend fun update(employee: Employee)

    @Delete
    suspend fun delete(employee: Employee)
}
