package com.mehmetpetek.satellitedemo.ui.list

import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mehmetpetek.satellitedemo.app.R
import com.mehmetpetek.satellitedemo.app.databinding.FragmentListBinding
import com.mehmetpetek.satellitedemo.common.extensions.showSnackBar
import com.mehmetpetek.satellitedemo.data.local.model.Satellite
import com.mehmetpetek.satellitedemo.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ListFragment :
    BaseFragment<FragmentListBinding>(FragmentListBinding::inflate),
    SatelliteAdapter.OnSatelliteClickListener {

    private val viewModel by viewModels<ListViewModel>()

    lateinit var satelliteAdapter: SatelliteAdapter
    private var currentSatelliteList: List<Satellite> = emptyList()

    override fun bindScreen() {
        setSearchView()

        lifecycleScope.launchWhenResumed {
            viewModel.effect.collect {
                when (it) {
                    is ListEffect.ShowError -> {
                        requireView().showSnackBar(
                            it.exceptionMessage.toString(),
                            getString(R.string.ok)
                        )
                    }
                    is ListEffect.GoToSatelliteDetail -> {
                        findNavController().navigate(
                            ListFragmentDirections.listFragmentToDetailFragment(
                                it.satellite
                            )
                        )
                    }
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.state.collect {
                setLoadingState(it.isLoading)

                it.satelliteList?.let {
                    currentSatelliteList = it
                    satelliteAdapter = SatelliteAdapter(it, this@ListFragment)

                    binding.rvSatellite.setHasFixedSize(true)
                    binding.rvSatellite.adapter = satelliteAdapter
                }
            }
        }
    }

    private fun setSearchView() {
        binding.svSatellite.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                if (msg.length > 2) {
                    filter(currentSatelliteList, msg)
                } else {
                    filter(currentSatelliteList, "")
                }
                return false
            }
        })
    }

    override fun onSelectSatelliteClicked(satellite: Satellite) {
        viewModel.setEvent(ListEvent.GoToSatelliteDetail(satellite))
    }

    private fun filter(satelliteList: List<Satellite>, text: String) {
        try {
            val filteredlist: MutableList<Satellite> = mutableListOf()
            for (item in satelliteList) {
                if (item.name?.lowercase(Locale.ROOT)
                        ?.contains(text.lowercase(Locale.ROOT)) == true
                ) {
                    filteredlist.add(item)
                }
            }
            if (filteredlist.isEmpty()) {
                requireView().showSnackBar(getString(R.string.no_data), getString(R.string.ok))
            } else {
                satelliteAdapter.filterList(filteredlist)
            }
        } catch (ex: java.lang.Exception) {
            requireView().showSnackBar(ex.message.toString(), getString(R.string.ok))
        }
    }

    override fun onDestroyView() {
        binding.rvSatellite.adapter = null

        super.onDestroyView()
    }
}