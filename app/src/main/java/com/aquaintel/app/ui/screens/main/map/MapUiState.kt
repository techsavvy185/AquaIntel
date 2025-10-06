package com.aquaintel.app.ui.screens.main.map

import com.aquaintel.app.data.models.DWLRStation

data class MapUiState(
    val isLoading: Boolean = true,
    val stations: List<DWLRStation> = emptyList(),
    val selectedStation: DWLRStation? = null,
    val searchQuery: String = "",
    val filteredStations: List<DWLRStation> = emptyList(),
    val error: String? = null
)
