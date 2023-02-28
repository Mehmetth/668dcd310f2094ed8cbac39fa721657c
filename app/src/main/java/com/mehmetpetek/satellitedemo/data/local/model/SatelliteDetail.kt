package com.mehmetpetek.satellitedemo.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "SatelliteDetail")
data class SatelliteDetail(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var cost_per_launch: Int? = null,
    var first_flight: String? = null,
    var height: Int? = null,
    var mass: Int? = null
) : Parcelable