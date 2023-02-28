package com.mehmetpetek.satellitedemo.domain.usecase

import com.mehmetpetek.satellitedemo.data.local.model.Position
import com.mehmetpetek.satellitedemo.domain.repository.SatelliteRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class PositionUseCase @Inject constructor(private val satelliteRepository: SatelliteRepository) {

    operator fun invoke(id: Int): Flow<PositionState> = callbackFlow {
        satelliteRepository.getAllPosition().collect {
            try {
                it.data?.let {
                    trySend(
                        PositionState.Success(it.list.find { it?.id == id }?.positions)
                    )
                } ?: kotlin.run {
                    trySend(
                        PositionState.Success(null)
                    )
                }
            } catch (ex: java.lang.Exception) {
                trySend(PositionState.Error(ex))
            }
        }
        awaitClose { cancel() }
    }

    sealed class PositionState {
        class Success(
            val positionList: ArrayList<Position?>?,
        ) : PositionState()

        class Error(
            val exception: Exception?,
        ) : PositionState()
    }
}