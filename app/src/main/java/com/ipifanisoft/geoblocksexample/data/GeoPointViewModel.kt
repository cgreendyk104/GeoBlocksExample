package com.ipifanisoft.geoblocksexample.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GeoPointViewModel(application: Application): AndroidViewModel(application) {

    private val repository: GeoPointRepository
    val allGeoPoints: LiveData<List<GeoPoint>>

    init {
        val geoPointDao = GeoPointsDatabase.getDatabase(application).geoPointDao()
        repository = GeoPointRepository(geoPointDao)
        allGeoPoints = repository.allGeoPoints
    }

    fun deleteGeoPointsByBlock(id: String) = viewModelScope.launch {
        repository.deleteGeoPointsByBlock(id)
    }

    fun insert(geoPoint: GeoPoint) = viewModelScope.launch {
        repository.insert(geoPoint)
    }

    fun delete(geoPoint: GeoPoint) = viewModelScope.launch {
        repository.delete(geoPoint)
    }
}