package com.task.satelliteapp

import com.mehmetpetek.satellitedemo.data.local.model.Satellite
import com.mehmetpetek.satellitedemo.domain.usecase.SatelliteListUseCase
import com.mehmetpetek.satellitedemo.ui.list.ListEvent
import com.mehmetpetek.satellitedemo.ui.list.ListState
import com.mehmetpetek.satellitedemo.ui.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {
    private lateinit var listViewModel: ListViewModel

    @Mock
    private lateinit var satelliteListUseCase: SatelliteListUseCase

    private val emittedEventList = mutableListOf(
        ListEvent.GoToSatelliteDetail(Satellite(id = 1, active = true, name = "Test Satellite")),
        ListEvent.Back
    )

    private val satelliteListStateList = mutableListOf(
        SatelliteListUseCase.GetAllSatelliteState.Success(null),
        SatelliteListUseCase.GetAllSatelliteState.Error(Exception())
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.IO)
        listViewModel = ListViewModel(satelliteListUseCase)
    }

    @Test
    fun testState() = runTest {
        val emittedStateList = mutableListOf(
            ListState(
                isLoading = false,
                satelliteList = null
            )
        )
        listViewModel.setState {
            ListState(
                isLoading = false,
                satelliteList = null
            )
        }
        val job = launch {
            listViewModel.state.toList(emittedStateList)
        }

        Assert.assertEquals(ListState(), listViewModel.getCurrentState())
        job.cancel()
    }

    @Test
    fun testEventGoToSatelliteDetail() = runTest {
        listViewModel.setEvent(
            ListEvent.GoToSatelliteDetail(
                Satellite(id = 1, active = true, name = "Test Satellite")
            )
        )
        val job = launch { listViewModel.event.toList(emittedEventList) }
        assert(emittedEventList[0] is ListEvent.GoToSatelliteDetail)
        job.cancel()
    }

    @Test
    fun testEventBack() = runTest {
        listViewModel.setEvent(ListEvent.Back)
        val job = launch { listViewModel.event.toList(emittedEventList) }
        assert(emittedEventList[1] is ListEvent.Back)
        job.cancel()
    }
}