package com.mehmetpetek.satellitedemo.ui.list

import com.mehmetpetek.satellitedemo.data.local.model.Satellite
import com.mehmetpetek.satellitedemo.ui.base.State

data class ListState(
    val isLoading: Boolean = false,
    val satelliteList: List<Satellite>? = null
) : State