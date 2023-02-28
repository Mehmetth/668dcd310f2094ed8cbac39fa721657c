package com.mehmetpetek.satellitedemo.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Satellite(
    var id: Int?,
    var active: Boolean?,
    var name: String?
) : Parcelable
