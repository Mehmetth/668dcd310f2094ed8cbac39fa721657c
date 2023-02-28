package com.mehmetpetek.satellitedemo.domain.repository

import com.mehmetpetek.satellitedemo.data.local.db.SatelliteDetailDao
import com.mehmetpetek.satellitedemo.data.local.model.SatelliteDetail
import javax.inject.Inject

class SatelliteDetailDBRepository @Inject constructor(
    private val dao: SatelliteDetailDao,
) {
    fun saveSatelliteDetail(satelliteDetail: SatelliteDetail) =
        dao.insertSatelliteDetail(satelliteDetail)

    fun getSatelliteDetail(id: Int): SatelliteDetail? = dao.getSatelliteDetail(id)
}