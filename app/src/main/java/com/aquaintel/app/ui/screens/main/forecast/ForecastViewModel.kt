package com.aquaintel.app.ui.screens.main.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquaintel.app.data.mock.MockPredictions
import com.aquaintel.app.data.mock.MockStations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor() : ViewModel() {

    private val _forecastUiState = MutableStateFlow(ForecastUiState())
    val forecastUiState: StateFlow<ForecastUiState> = _forecastUiState.asStateFlow()

    init {
        loadForecastData()
    }

    private fun loadForecastData() {
        viewModelScope.launch {
            _forecastUiState.update { it.copy(isLoading = true) }

            delay(800) // Simulate loading

            // Use first station as default
            val defaultStation = MockStations.stations.firstOrNull()
            if (defaultStation != null) {
                val predictions = MockPredictions.getPredictionData(
                    defaultStation.id,
                    _forecastUiState.value.forecastPeriod.days
                )
                val seasonalForecast = MockPredictions.getSeasonalForecast(defaultStation.id)

                _forecastUiState.update {
                    it.copy(
                        isLoading = false,
                        predictions = predictions,
                        selectedStationId = defaultStation.id,
                        seasonalForecast = seasonalForecast,
                        riskAssessment = generateRiskAssessment(predictions)
                    )
                }
            }
        }
    }

    fun changeForecastPeriod(period: ForecastPeriod) {
        _forecastUiState.update { it.copy(forecastPeriod = period) }
        loadForecastData()
    }

    fun changeStation(stationId: String) {
        _forecastUiState.update { it.copy(selectedStationId = stationId) }
        loadForecastData()
    }

    private fun generateRiskAssessment(predictions: List<com.aquaintel.app.data.models.PredictionData>): String {
        val avgLevel = predictions.map { it.predictedWaterLevel }.average()
        return when {
            avgLevel < 5 -> "Low Risk - Adequate water levels expected"
            avgLevel < 15 -> "Moderate Risk - Monitor water usage"
            avgLevel < 25 -> "High Risk - Consider conservation measures"
            else -> "Critical Risk - Immediate action required"
        }
    }
}
