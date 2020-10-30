package com.ipifanisoft.geoblocksexample.data

import androidx.lifecycle.LiveData

class GeoPointRepository(private val geoPointDao: GeoPointDao) {

    val allGeoPoints: LiveData<List<GeoPoint>> = geoPointDao.getAll()

    suspend fun deleteGeoPointsByBlock(id: String) {
        return geoPointDao.deleteGeoPointsByBlock(id)
    }

    suspend fun insert(geoPoint: GeoPoint) {
        geoPointDao.insert(geoPoint)
    }

    suspend fun delete(geoPoint: GeoPoint) {
        geoPointDao.delete(geoPoint)
    }
}