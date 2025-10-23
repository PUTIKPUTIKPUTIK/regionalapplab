package com.example.regionalapplab.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lastName: String,
    val firstName: String,
    val secondName: String,
    val title: String,
    val birthDay: String,
    val address: String,
    val city: String,
    val region: String,
    val phone: String,
    val email: String
)
