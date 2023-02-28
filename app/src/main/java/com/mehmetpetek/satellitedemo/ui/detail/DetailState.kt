package com.mehmetpetek.satellitedemo.ui.detail

import com.mehmetpetek.satellitedemo.data.local.model.Position
import com.mehmetpetek.satellitedemo.data.local.model.Satellite
import com.mehmetpetek.satellitedemo.data.local.model.SatelliteDetail
import com.mehmetpetek.satellitedemo.ui.base.State

data class DetailState(
    val isLoading: Boolean = false,
    val satellite: Satellite? = null,
    val satelliteDetail: SatelliteDetail? = null,
    val positionList: ArrayList<Position?>? = null
) : State