package com.mehmetpetek.satellitedemo.ui.detail

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mehmetpetek.satellitedemo.common.Constant
import com.mehmetpetek.satellitedemo.data.local.model.Satellite
import com.mehmetpetek.satellitedemo.data.local.model.SatelliteDetail
import com.mehmetpetek.satellitedemo.domain.repository.SatelliteDetailDBRepository
import com.mehmetpetek.satellitedemo.domain.usecase.PositionUseCase
import com.mehmetpetek.satellitedemo.domain.usecase.SatelliteDetailUseCase
import com.mehmetpetek.satellitedemo.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val satelliteDetailUseCase: SatelliteDetailUseCase,
    private val positionUseCase: PositionUseCase,
    private val satelliteDetailDBRepository: SatelliteDetailDBRepository
) : BaseViewModel<DetailEvent, DetailState, DetailEffect>() {

    init {
        val satellite = savedStateHandle.get<Satellite>(Constant.Args.SATELLITE)
        setState { getCurrentState().copy(satellite = satellite) }
        satellite?.let {
            checkSavedDBSatelliteDetail(it)
            getPosition(it.id ?: -1)
        }
    }

    override fun setInitialState(): DetailState = DetailState(isLoading = false)

    override fun handleEvents(event: DetailEvent) {}

    @VisibleForTesting
    fun getSatelliteDetail(id: Int) {
        setState { getCurrentState().copy(isLoading = true) }
        viewModelScope.launch {
            satelliteDetailUseCase(id).collect {
                when (it) {
                    is SatelliteDetailUseCase.GetSatelliteDetailState.Success -> {
                        it.satelliteDetail?.let {
                            saveSatelliteDetail(it)
                        }
                        setState {
                            getCurrentState().copy(
                                isLoading = false,
                                satelliteDetail = it.satelliteDetail
                            )
                        }
                    }
                    is SatelliteDetailUseCase.GetSatelliteDetailState.Error -> {
                        setEffect { DetailEffect.ShowError(it.exception?.message) }
                        setState { getCurrentState().copy(isLoading = false) }
                    }
                }
            }
        }
    }

    @VisibleForTesting
    fun getPosition(id: Int) {
        setState { getCurrentState().copy(isLoading = true) }
        viewModelScope.launch {
            positionUseCase(id).collect {
                when (it) {
                    is PositionUseCase.PositionState.Success -> {
                        setState {
                            getCurrentState().copy(
                                isLoading = false,
                                positionList = it.positionList
                            )
                        }
                    }
                    is PositionUseCase.PositionState.Error -> {
                        setEffect { DetailEffect.ShowError(it.exception?.message) }
                        setState { getCurrentState().copy(isLoading = false) }
                    }
                }
            }
        }
    }

    @VisibleForTesting
    fun checkSavedDBSatelliteDetail(satellite: Satellite) {
        val satelliteDetail = satelliteDetailDBRepository.getSatelliteDetail(satellite.id ?: -1)
        if (satelliteDetail == null) {
            getSatelliteDetail(satellite.id ?: -1)
        } else {
            setState {
                getCurrentState().copy(
                    isLoading = false,
                    satelliteDetail = satelliteDetail
                )
            }
        }
    }

    private fun saveSatelliteDetail(satelliteDetail: SatelliteDetail) {
        satelliteDetailDBRepository.saveSatelliteDetail(satelliteDetail)
    }
}