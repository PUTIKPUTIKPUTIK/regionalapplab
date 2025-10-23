package com.example.regionalapplab.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.regionalapplab.data.database.AppDatabase
import com.example.regionalapplab.data.entity.Territory
import kotlinx.coroutines.launch

class TerritoryViewModel(application: Application) : AndroidViewModel(application) {
    private val territoryDao = AppDatabase.getDatabase(application).territoryDao()
    val territories: SnapshotStateList<Territory> = mutableStateListOf()

    init { loadAll() }

    private fun loadAll() {
        viewModelScope.launch {
            val list = territoryDao.getAll()
            territories.clear()
            territories.addAll(list)
        }
    }

    fun add(territory: Territory) {
        viewModelScope.launch {
            territoryDao.insert(territory)
            loadAll()
        }
    }

    fun update(territory: Territory) {
        viewModelScope.launch {
            territoryDao.update(territory)
            loadAll()
        }
    }

    fun delete(territory: Territory) {
        viewModelScope.launch {
            territoryDao.delete(territory)
            loadAll()
        }
    }
}
