package com.aquaintel.app.ui.screens.main.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquaintel.app.data.mock.MockStations
import com.aquaintel.app.data.models.DWLRStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    private val _mapUiState = MutableStateFlow(MapUiState())
    val mapUiState: StateFlow<MapUiState> = _mapUiState.asStateFlow()

    init {
        loadStations()
    }

    private fun loadStations() {
        viewModelScope.launch {
            _mapUiState.update { it.copy(isLoading = true) }

            delay(800) // Simulate loading

            val stations = MockStations.stations
            _mapUiState.update {
                it.copy(
                    isLoading = false,
                    stations = stations,
                    filteredStations = stations
                )
            }
        }
    }

    fun searchStations(query: String) {
        _mapUiState.update { currentState ->
            val filtered = if (query.isBlank()) {
                currentState.stations
            } else {
                currentState.stations.filter { station ->
                    station.name.contains(query, ignoreCase = true) ||
                            station.district.contains(query, ignoreCase = true) ||
                            station.state.contains(query, ignoreCase = true)
                }
            }

            currentState.copy(
                searchQuery = query,
                filteredStations = filtered
            )
        }
    }

    fun selectStation(station: DWLRStation?) {
        _mapUiState.update { it.copy(selectedStation = station) }
    }

    fun clearSelection() {
        _mapUiState.update { it.copy(selectedStation = null) }
    }
}
