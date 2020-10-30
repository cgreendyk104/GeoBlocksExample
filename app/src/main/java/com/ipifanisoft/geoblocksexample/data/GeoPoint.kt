package com.ipifanisoft.geoblocksexample.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geo_points")
data class GeoPoint(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name ="id") val id: Int,
    @ColumnInfo(name = "blockId") val blockId: String,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lng") val lng: Double
)