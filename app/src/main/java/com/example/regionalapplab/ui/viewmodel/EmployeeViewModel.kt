package com.example.regionalapplab.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.regionalapplab.data.database.AppDatabase
import com.example.regionalapplab.data.entity.Employee
import kotlinx.coroutines.launch

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {

    private val employeeDao = AppDatabase.getDatabase(application).employeeDao()
    val employees: SnapshotStateList<Employee> = mutableStateListOf()

    init {
        loadAll()
    }

    fun loadAll() {
        viewModelScope.launch {
            val list = employeeDao.getAll()
            employees.clear()
            employees.addAll(list)
        }
    }

    fun add(employee: Employee) {
        viewModelScope.launch {
            employeeDao.insert(employee)
            loadAll()
        }
    }

    fun update(employee: Employee) {
        viewModelScope.launch {
            employeeDao.update(employee)
            loadAll()
        }
    }

    fun delete(employee: Employee) {
        viewModelScope.launch {
            employeeDao.delete(employee)
            loadAll()
        }
    }
}
