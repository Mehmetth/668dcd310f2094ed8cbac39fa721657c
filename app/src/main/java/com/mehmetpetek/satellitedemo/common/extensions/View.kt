package com.mehmetpetek.satellitedemo.common.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.showSnackBar(message: String, buttonText: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).setAction(buttonText) { null }
        .show()
}

fun View.visibleInvisibleIf(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}