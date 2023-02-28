package com.mehmetpetek.satellitedemo.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.mehmetpetek.satellitedemo.app.R
import com.mehmetpetek.satellitedemo.common.extensions.getLoading
import com.mehmetpetek.satellitedemo.common.extensions.gone
import com.mehmetpetek.satellitedemo.common.extensions.visible

abstract class BaseFragment<T : ViewBinding>(
    private val inflate: Inflate<T>,
) : Fragment() {

    private var progressDialog: Dialog? = null
    private var progress: View? = null
    protected lateinit var binding: T
    open val saveBinding: Boolean = false
    open val isBlackTheme: Boolean = true
    abstract fun bindScreen()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (this::binding.isInitialized && saveBinding) {
            binding
        } else {
            binding = inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindScreen()
    }

    fun onBackClicked() {
        hideLoading()
        if (!findNavController().popBackStack()) {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideLoading()
    }

    fun setLoadingState(
        visible: Boolean,
    ) {
        if (visible) showLoading() else hideLoading()
    }


    private fun showLockLoading(
        cancellable: Boolean = false,
        cancelListener: (() -> Unit)? = null,
    ) {
        if (progressDialog == null) {
            val c = {
                onBackClicked()
            }
            progressDialog = getLoading(
                cancellable,
                cancelListener ?: kotlin.run { c })
        }
        progressDialog?.show()
    }

    fun setLockLoadingState(
        visible: Boolean,
        cancellable: Boolean = false,
        cancelListener: (() -> Unit)? = null,
    ) {
        if (visible) showLockLoading(cancellable, cancelListener) else hideLoading()
    }


    private fun showLoading() {
        if (progress == null) {
            progress = layoutInflater.inflate(R.layout.dialog_progress, null)
            val layoutParams = ViewGroup.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            progress?.layoutParams = layoutParams
            (binding.root as ViewGroup).addView(progress)
        }
        progress?.visible()
    }

    fun isLoadingVisible(): Boolean {
        return progress?.visibility == View.VISIBLE || progressDialog?.isShowing == true
    }

    private fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
        progress?.gone()
        progress = null
    }

}