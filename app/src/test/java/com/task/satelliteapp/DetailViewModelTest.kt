package com.task.satelliteapp

import androidx.lifecycle.SavedStateHandle
import com.mehmetpetek.satellitedemo.domain.repository.SatelliteDetailDBRepository
import com.mehmetpetek.satellitedemo.domain.usecase.PositionUseCase
import com.mehmetpetek.satellitedemo.domain.usecase.SatelliteDetailUseCase
import com.mehmetpetek.satellitedemo.ui.detail.DetailEffect
import com.mehmetpetek.satellitedemo.ui.detail.DetailState
import com.mehmetpetek.satellitedemo.ui.detail.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {
    private lateinit var detailViewModel: DetailViewModel

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var satelliteDetailUseCase: SatelliteDetailUseCase

    @Mock
    private lateinit var positionUseCase: PositionUseCase

    @Mock
    private lateinit var satelliteDetailDBRepository: SatelliteDetailDBRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.IO)
        detailViewModel = DetailViewModel(
            savedStateHandle,
            satelliteDetailUseCase,
            positionUseCase,
            satelliteDetailDBRepository
        )
    }

    @Test
    fun testState() = runTest {
        val emittedStateList = mutableListOf(
            DetailState(
                isLoading = false,
                satellite = null,
                satelliteDetail = null,
                positionList = null
            )
        )
        detailViewModel.setState {
            DetailState(
                isLoading = false,
                satellite = null,
                satelliteDetail = null,
                positionList = null
            )
        }
        val job = launch {
            detailViewModel.state.toList(emittedStateList)
        }

        Assert.assertEquals(DetailState(), detailViewModel.getCurrentState())
        job.cancel()
    }

    private val satelliteDetailStateList = mutableListOf(
        SatelliteDetailUseCase.GetSatelliteDetailState.Success(null),
        SatelliteDetailUseCase.GetSatelliteDetailState.Error(Exception())
    )

    private val positionStateList = mutableListOf(
        PositionUseCase.PositionState.Success(null),
        PositionUseCase.PositionState.Error(Exception())
    )
    private val emittedEffectList = mutableListOf(
        DetailEffect.ShowError("Exception"),
        DetailEffect.Back
    )

    @Test
    fun testSatelliteDetailShowError() = runTest {
        detailViewModel.getSatelliteDetail(1)

        whenever(satelliteDetailUseCase.invoke(1)).thenReturn(satelliteDetailStateList.asFlow())

        val job = launch {
            detailViewModel.effect.toList(emittedEffectList)
        }

        verify(satelliteDetailUseCase).invoke(1)

        assert(emittedEffectList[0] is DetailEffect.ShowError)
        job.cancel()
    }

    @Test
    fun testSatelliteDetailSuccess() = runTest {
        detailViewModel.getSatelliteDetail(1)

        whenever(satelliteDetailUseCase.invoke(1)).thenReturn(satelliteDetailStateList.asFlow())

        val job = launch {
            detailViewModel.effect.toList(emittedEffectList)
        }

        verify(satelliteDetailUseCase).invoke(1)

        job.cancel()
    }

    @Test
    fun testPositionShowError() = runTest {
        detailViewModel.getPosition(1)

        whenever(positionUseCase.invoke(1)).thenReturn(positionStateList.asFlow())

        val job = launch {
            detailViewModel.effect.toList(emittedEffectList)
        }

        verify(positionUseCase).invoke(1)

        assert(emittedEffectList[0] is DetailEffect.ShowError)
        job.cancel()
    }

    @Test
    fun testPositionSuccess() = runTest {
        detailViewModel.getPosition(1)

        whenever(positionUseCase.invoke(1)).thenReturn(positionStateList.asFlow())

        val job = launch {
            detailViewModel.effect.toList(emittedEffectList)
        }

        verify(positionUseCase).invoke(1)

        job.cancel()
    }
}