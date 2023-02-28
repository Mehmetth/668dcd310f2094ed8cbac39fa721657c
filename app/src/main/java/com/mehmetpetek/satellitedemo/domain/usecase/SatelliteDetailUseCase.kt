package com.mehmetpetek.satellitedemo.domain.usecase

import com.mehmetpetek.satellitedemo.data.local.model.SatelliteDetail
import com.mehmetpetek.satellitedemo.domain.repository.SatelliteRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SatelliteDetailUseCase @Inject constructor(private val satelliteRepository: SatelliteRepository) {

    operator fun invoke(id: Int): Flow<GetSatelliteDetailState> = callbackFlow {
        satelliteRepository.getSatelliteDetail().collect {
            try {
                if (it.data.isNullOrEmpty()) {
                    trySend(GetSatelliteDetailState.Success(null))
                } else {
                    trySend(GetSatelliteDetailState.Success(it.data.find { it.id == id }))
                }
            } catch (ex: java.lang.Exception) {
                trySend(GetSatelliteDetailState.Error(ex))
            }
        }
        awaitClose { cancel() }
    }

    sealed class GetSatelliteDetailState {
        class Success(
            val satelliteDetail: SatelliteDetail?,
        ) : GetSatelliteDetailState()

        class Error(
            val exception: Exception?,
        ) : GetSatelliteDetailState()
    }
}