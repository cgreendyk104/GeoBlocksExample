package com.ipifanisoft.geoblocksexample.util

import com.google.android.gms.maps.model.LatLng

class GEOBlockManager {

    fun getBlockIdAtGeoPoint(lat: Double, lng: Double): String {
        val blockLat = ((lat - minLat) / gapLat).toInt()
        val blockLng = ((lng - minLng) / gapLng).toInt()
        return "$blockLat-$blockLng"
    }

    fun getBlockCoordinatesForGeoPoint(blockId: String): ArrayList<LatLng> {
        val lat = minLat + (blockId.split("-")[0].toDouble() * gapLat)
        val lng = minLng + (blockId.split("-")[1].toDouble() * gapLng)
        val coordinates = ArrayList<LatLng>()
        val northEastPoint = LatLng(lat, lng)
        val northWestPoint = LatLng(lat, lng + gapLng)
        val southWestPoint = LatLng(lat + gapLat, lng + gapLng)
        val southEastPoint = LatLng(lat + gapLat, lng)
        coordinates.add(northEastPoint)
        coordinates.add(northWestPoint)
        coordinates.add(southWestPoint)
        coordinates.add(southEastPoint)
        return coordinates
    }

    companion object {
        const val minLat = -90.0
        const val minLng = -180.0
        const val gapLat = 1.0
        const val gapLng = 1.0
    }

}