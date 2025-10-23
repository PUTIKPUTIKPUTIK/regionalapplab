package com.example.regionalapplab.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "territories")
data class Territory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val description: String,
    val regionId: Int
)
