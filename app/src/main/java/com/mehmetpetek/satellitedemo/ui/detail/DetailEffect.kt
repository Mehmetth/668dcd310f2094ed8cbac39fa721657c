package com.mehmetpetek.satellitedemo.ui.detail

import com.mehmetpetek.satellitedemo.ui.base.Effect

sealed class DetailEffect : Effect {
    class ShowError(val exceptionMessage: String?) : DetailEffect()
    object Back : DetailEffect()
}