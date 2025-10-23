package com.example.regionalapplab.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.regionalapplab.data.database.AppDatabase
import com.example.regionalapplab.data.entity.Region
import kotlinx.coroutines.launch

class RegionViewModel(application: Application) : AndroidViewModel(application) {

    private val regionDao = AppDatabase.getDatabase(application).regionDao()
    val regions: SnapshotStateList<Region> = mutableStateListOf()

    init { loadAll() }

    fun loadAll() {
        viewModelScope.launch {
            val list = regionDao.getAll()
            regions.clear()
            regions.addAll(list)
        }
    }

    fun add(region: Region) = viewModelScope.launch {
        regionDao.insert(region)
        loadAll()
    }

    fun update(region: Region) = viewModelScope.launch {
        regionDao.update(region)
        loadAll()
    }

    fun delete(region: Region) = viewModelScope.launch {
        regionDao.delete(region)
        loadAll()
    }
}
