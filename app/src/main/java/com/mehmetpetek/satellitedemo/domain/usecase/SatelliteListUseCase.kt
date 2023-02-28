package com.mehmetpetek.satellitedemo.domain.usecase

import com.mehmetpetek.satellitedemo.data.local.model.Satellite
import com.mehmetpetek.satellitedemo.domain.repository.SatelliteRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SatelliteListUseCase @Inject constructor(private val satelliteRepository: SatelliteRepository) {

    operator fun invoke(): Flow<GetAllSatelliteState> = callbackFlow {
        satelliteRepository.getAllSatellite().collect {
            try {
                trySend(
                    GetAllSatelliteState.Success(it.data)
                )
            } catch (ex: java.lang.Exception) {
                trySend(GetAllSatelliteState.Error(ex))
            }
        }
        awaitClose { cancel() }
    }

    sealed class GetAllSatelliteState {
        class Success(
            val satelliteList: List<Satellite>?,
        ) : GetAllSatelliteState()

        class Error(
            val exception: Exception?,
        ) : GetAllSatelliteState()
    }
}