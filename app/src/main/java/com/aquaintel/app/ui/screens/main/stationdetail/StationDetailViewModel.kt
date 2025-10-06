package com.aquaintel.app.ui.screens.main.stationdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquaintel.app.data.mock.MockPredictions
import com.aquaintel.app.data.mock.MockRainfallData
import com.aquaintel.app.data.mock.MockStations
import com.aquaintel.app.data.mock.MockTimeSeriesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationDetailViewModel @Inject constructor() : ViewModel() {

    private val _stationDetailUiState = MutableStateFlow(StationDetailUiState())
    val stationDetailUiState: StateFlow<StationDetailUiState> = _stationDetailUiState.asStateFlow()

    fun loadStationDetails(stationId: String) {
        viewModelScope.launch {
            _stationDetailUiState.update { it.copy(isLoading = true) }

            delay(800) // Simulate loading

            val station = MockStations.getStationById(stationId)
            if (station != null) {
                val waterLevelData = MockTimeSeriesData.getTimeSeriesData(
                    stationId,
                    _stationDetailUiState.value.selectedTimeRange.days
                )
                val rainfallData = MockRainfallData.getRainfallData(
                    station.district,
                    _stationDetailUiState.value.selectedTimeRange.days
                )
                val predictions = MockPredictions.getPredictionData(stationId, 30)

                _stationDetailUiState.update {
                    it.copy(
                        isLoading = false,
                        station = station,
                        waterLevelData = waterLevelData,
                        rainfallData = rainfallData,
                        predictions = predictions
                    )
                }
            } else {
                _stationDetailUiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Station not found"
                    )
                }
            }
        }
    }

    fun changeTimeRange(timeRange: TimeRange) {
        val currentStation = _stationDetailUiState.value.station
        if (currentStation != null) {
            _stationDetailUiState.update { it.copy(selectedTimeRange = timeRange) }
            loadStationDetails(currentStation.id) // Reload data with new time range
        }
    }
}
