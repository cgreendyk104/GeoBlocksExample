package com.ipifanisoft.geoblocksexample.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GeoPointDao {
    @Query("SELECT * FROM geo_points ORDER BY blockId ASC")
    fun getAll(): LiveData<List<GeoPoint>>

    @Query("DELETE from geo_points WHERE blockId = :id")
    suspend fun deleteGeoPointsByBlock(id: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(catch: GeoPoint)

    @Delete
    suspend fun delete(catch: GeoPoint)
}