package com.aquaintel.app.ui.screens.main.map

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aquaintel.app.data.models.AquiferStress
import com.aquaintel.app.data.models.DWLRStation
import com.aquaintel.app.data.models.StationStatus
import com.aquaintel.app.ui.components.StationCard
import com.aquaintel.app.ui.theme.AquaIntelTheme
import com.aquaintel.app.ui.theme.ClashDisplay
import com.aquaintel.app.ui.theme.LocalAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel(),
    onNavigateToStationDetail: (String) -> Unit = {}
) {
    val uiState by viewModel.mapUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Station Map",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = ClashDisplay
                    )
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = LocalAppColors.current.surface,
                titleContentColor = LocalAppColors.current.onSurface
            )
        )

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = LocalAppColors.current.primary,
                    modifier = Modifier.size(48.dp)
                )
            }
        } else {
            MapContent(
                uiState = uiState,
                onSearchQueryChanged = viewModel::searchStations,
                onStationSelected = viewModel::selectStation,
                onNavigateToStationDetail = onNavigateToStationDetail
            )
        }
    }
}

@Composable
private fun MapContent(
    uiState: MapUiState,
    onSearchQueryChanged: (String) -> Unit,
    onStationSelected: (DWLRStation?) -> Unit,
    onNavigateToStationDetail: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearchQueryChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search stations, districts, or states...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = LocalAppColors.current.primary,
                unfocusedBorderColor = LocalAppColors.current.divider
            )
        )

        // Map Placeholder (In a real app, this would be Google Maps)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = LocalAppColors.current.cardBackground
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🗺️",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = "Interactive Map View",
                        style = MaterialTheme.typography.titleMedium,
                        color = LocalAppColors.current.editText
                    )
                    Text(
                        text = "${uiState.filteredStations.size} stations found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LocalAppColors.current.editText
                    )
                }
            }
        }

        // Selected Station Info (if any)
        uiState.selectedStation?.let { station ->
            StationCard(
                station = station,
                onClick = { onNavigateToStationDetail(station.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MapScreenPreview() {
    AquaIntelTheme {
        MapContent(
            uiState = MapUiState(
                isLoading = false,
                stations = listOf(
                    DWLRStation(
                        id = "1",
                        name = "Test Station",
                        latitude = 0.0,
                        longitude = 0.0,
                        state = "Test State",
                        district = "Test District",
                        aquiferType = "Alluvial",
                        status = StationStatus.ACTIVE,
                        currentWaterLevel = 7.2,
                        aquiferStress = AquiferStress.LOW
                    )
                ),
                filteredStations = listOf(),
                searchQuery = ""
            ),
            onSearchQueryChanged = {},
            onStationSelected = {},
            onNavigateToStationDetail = {}
        )
    }
}
