package com.mehmetpetek.satellitedemo.ui.list

import com.mehmetpetek.satellitedemo.data.local.model.Satellite
import com.mehmetpetek.satellitedemo.ui.base.Event

sealed class ListEvent : Event {
    object Back : ListEvent()
    class GoToSatelliteDetail(val satellite: Satellite) : ListEvent()
}