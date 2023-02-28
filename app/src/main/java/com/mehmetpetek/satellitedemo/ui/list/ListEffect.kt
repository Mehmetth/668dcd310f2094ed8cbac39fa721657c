package com.mehmetpetek.satellitedemo.ui.list

import com.mehmetpetek.satellitedemo.data.local.model.Satellite
import com.mehmetpetek.satellitedemo.ui.base.Effect

sealed class ListEffect : Effect {
    class ShowError(val exceptionMessage: String?) : ListEffect()
    class GoToSatelliteDetail(val satellite: Satellite) : ListEffect()
}