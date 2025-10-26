package com.example.regionalapplab.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.regionalapplab.data.EmployeeDao
import com.example.regionalapplab.data.dao.EmployeeTerritoryDao
import com.example.regionalapplab.data.dao.RegionDao
import com.example.regionalapplab.data.dao.TerritoryDao
import com.example.regionalapplab.data.entity.Employee
import com.example.regionalapplab.data.entity.EmployeeTerritory
import com.example.regionalapplab.data.entity.Region
import com.example.regionalapplab.data.entity.Territory

@Database(
    entities = [Employee::class, Region::class, Territory::class, EmployeeTerritory::class],
    version = 5,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun regionDao(): RegionDao
    abstract fun territoryDao(): TerritoryDao
    abstract fun employeeTerritoryDao(): EmployeeTerritoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "regional_app_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
