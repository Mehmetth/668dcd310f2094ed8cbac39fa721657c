package com.mehmetpetek.satellitedemo.ui.list

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.mehmetpetek.satellitedemo.domain.usecase.SatelliteListUseCase
import com.mehmetpetek.satellitedemo.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val satelliteListUseCase: SatelliteListUseCase
) : BaseViewModel<ListEvent, ListState, ListEffect>() {

    override fun setInitialState(): ListState = ListState(isLoading = false)

    init {
        getAllSatellite()
    }

    override fun handleEvents(event: ListEvent) {
        when (event) {
            is ListEvent.GoToSatelliteDetail -> {
                setEffect { ListEffect.GoToSatelliteDetail(event.satellite) }
            }
            is ListEvent.Back -> Unit
        }
    }

    @VisibleForTesting
    fun getAllSatellite() {
        setState { getCurrentState().copy(isLoading = true) }
        viewModelScope.launch {
            satelliteListUseCase().collect {
                when (it) {
                    is SatelliteListUseCase.GetAllSatelliteState.Success -> {
                        setState {
                            getCurrentState().copy(
                                isLoading = false,
                                satelliteList = it.satelliteList
                            )
                        }
                    }
                    is SatelliteListUseCase.GetAllSatelliteState.Error -> {
                        setEffect { ListEffect.ShowError(it.exception?.message) }
                        setState { getCurrentState().copy(isLoading = false) }
                    }
                }
            }
        }
    }
}