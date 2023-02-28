package com.mehmetpetek.satellitedemo.data.local.db

import androidx.room.*
import com.mehmetpetek.satellitedemo.data.local.model.SatelliteDetail

@Dao
interface SatelliteDetailDao {
    @Query("SELECT * FROM satellitedetail WHERE id = :id")
    fun getSatelliteDetail(id: Int): SatelliteDetail?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertSatelliteDetail(satelliteDetail: SatelliteDetail)
}