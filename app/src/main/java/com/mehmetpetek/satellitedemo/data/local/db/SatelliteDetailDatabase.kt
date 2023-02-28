package com.mehmetpetek.satellitedemo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mehmetpetek.satellitedemo.data.local.model.SatelliteDetail

@Database(entities = [SatelliteDetail::class], version = 1)
abstract class SatelliteDetailDatabase : RoomDatabase() {
    abstract fun satelliteDetailDao(): SatelliteDetailDao
}
