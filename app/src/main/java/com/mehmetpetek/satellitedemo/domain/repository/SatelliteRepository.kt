package com.mehmetpetek.satellitedemo.domain.repository

import com.mehmetpetek.satellitedemo.data.Resource
import com.mehmetpetek.satellitedemo.data.local.model.PositionList
import com.mehmetpetek.satellitedemo.data.local.model.Satellite
import com.mehmetpetek.satellitedemo.data.local.model.SatelliteDetail
import kotlinx.coroutines.flow.Flow

interface SatelliteRepository {
    fun getAllSatellite(): Flow<Resource<List<Satellite>?>>
    fun getSatelliteDetail(): Flow<Resource<List<SatelliteDetail>?>>
    fun getAllPosition(): Flow<Resource<PositionList?>>
}