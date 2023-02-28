package com.mehmetpetek.satellitedemo.common.extensions

import android.app.Dialog
import android.view.Window
import androidx.fragment.app.Fragment
import com.mehmetpetek.satellitedemo.app.R

fun Fragment.getLoading(cancellable: Boolean, cancelListener: (() -> Unit)?): Dialog {
    val dialogView = Dialog(requireContext())
    dialogView.window?.requestFeature(Window.FEATURE_NO_TITLE)
    dialogView.setContentView(R.layout.component_loading)
    dialogView.window?.setDimAmount(0.1f)
    dialogView.window?.setBackgroundDrawable(null)
    dialogView.setCancelable(cancellable)
    dialogView.setOnCancelListener { cancelListener?.invoke() }
    dialogView.setCanceledOnTouchOutside(false)
    return dialogView
}