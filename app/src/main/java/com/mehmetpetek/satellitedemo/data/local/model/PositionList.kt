package com.mehmetpetek.satellitedemo.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PositionList(
    var list: ArrayList<List?>
) : Parcelable

@Parcelize
data class List(
    var id: Int?,
    var positions: ArrayList<Position?>
) : Parcelable

@Parcelize
data class Position(
    var posX: Double?,
    var posY: Double?
) : Parcelable
