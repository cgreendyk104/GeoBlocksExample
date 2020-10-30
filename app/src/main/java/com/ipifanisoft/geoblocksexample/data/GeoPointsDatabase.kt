package com.ipifanisoft.geoblocksexample.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [
    GeoPoint::class
], version = 1, exportSchema = false)

abstract class GeoPointsDatabase : RoomDatabase() {

    abstract fun geoPointDao(): GeoPointDao

    companion object {
        private const val databaseName = "GeoPointsData.db"

        @Volatile
        private var INSTANCE:GeoPointsDatabase? = null
        fun getDatabase(context: Context): GeoPointsDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GeoPointsDatabase::class.java,
                    databaseName
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
