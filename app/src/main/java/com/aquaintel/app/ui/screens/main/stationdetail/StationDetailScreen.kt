package com.aquaintel.app.ui.screens.main.stationdetail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.aquaintel.app.ui.components.AquiferStressBadge
import com.aquaintel.app.ui.components.DataCard
import com.aquaintel.app.ui.components.StationStatusBadge
import com.aquaintel.app.ui.components.WaterLevelChart
import com.aquaintel.app.ui.theme.AquaIntelTheme
import com.aquaintel.app.ui.theme.ClashDisplay
import com.aquaintel.app.ui.theme.LocalAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationDetailScreen(
    stationId: String,
    viewModel: StationDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.stationDetailUiState.collectAsState()

    LaunchedEffect(stationId) {
        viewModel.loadStationDetails(stationId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = uiState.station?.name ?: "Station Details",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
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
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.error ?: "Unknown error",
                    style = MaterialTheme.typography.bodyLarge,
                    color = LocalAppColors.current.error
                )
            }
        } else {
            StationDetailContent(
                uiState = uiState,
                onTimeRangeChanged = viewModel::changeTimeRange
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StationDetailContent(
    uiState: StationDetailUiState,
    onTimeRangeChanged: (TimeRange) -> Unit
) {
    val station = uiState.station ?: return

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Station Info Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LocalAppColors.current.cardBackground
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = station.name,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontFamily = ClashDisplay
                                ),
                                color = LocalAppColors.current.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = LocalAppColors.current.editText
                                )
                                Text(
                                    text = " ${station.district}, ${station.state}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = LocalAppColors.current.editText
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Aquifer Type: ${station.aquiferType}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = LocalAppColors.current.editText
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            StationStatusBadge(status = station.status)
                            Spacer(modifier = Modifier.height(8.dp))
                            AquiferStressBadge(stress = station.aquiferStress)
                        }
                    }
                }
            }
        }

        // Current Metrics
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DataCard(
                    title = "Water Level",
                    value = station.currentWaterLevel.toString(),
                    unit = "m below ground",
                    icon = Icons.Default.Water,
                    modifier = Modifier.weight(1f)
                )

                DataCard(
                    title = "Station ID",
                    value = station.id,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Time Range Selector
        item {
            Text(
                text = "Historical Data",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = ClashDisplay
                ),
                color = LocalAppColors.current.onSurface
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(TimeRange.values()) { timeRange ->
                    FilterChip(
                        onClick = { onTimeRangeChanged(timeRange) },
                        label = { Text(timeRange.displayName) },
                        selected = uiState.selectedTimeRange == timeRange,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = LocalAppColors.current.primary,
                            selectedLabelColor = LocalAppColors.current.onPrimary
                        )
                    )
                }
            }
        }

        // Water Level Chart
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LocalAppColors.current.cardBackground
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Water Level Trend (${uiState.selectedTimeRange.displayName})",
                        style = MaterialTheme.typography.titleMedium,
                        color = LocalAppColors.current.onSurface
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (uiState.waterLevelData.isNotEmpty()) {
                        WaterLevelChart(
                            data = uiState.waterLevelData,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(
                            text = "No data available for selected time range",
                            style = MaterialTheme.typography.bodyMedium,
                            color = LocalAppColors.current.editText
                        )
                    }
                }
            }
        }

        // Additional Statistics
        item {
            Text(
                text = "Statistics",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = ClashDisplay
                ),
                color = LocalAppColors.current.onSurface
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DataCard(
                    title = "Average Level",
                    value = uiState.waterLevelData.map { it.waterLevel }.average().let {
                        "%.1f".format(it)
                    },
                    unit = "m",
                    modifier = Modifier.weight(1f)
                )

                DataCard(
                    title = "Max Depth",
                    value = uiState.waterLevelData.maxOfOrNull { it.waterLevel }?.let {
                        "%.1f".format(it)
                    } ?: "N/A",
                    unit = "m",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StationDetailScreenPreview() {
    AquaIntelTheme {
        StationDetailContent(
            uiState = StationDetailUiState(
                isLoading = false,
                station = DWLRStation(
                    id = "DWLR_MH_001",
                    name = "Nagpur Central DWLR",
                    latitude = 21.1458,
                    longitude = 79.0882,
                    state = "Maharashtra",
                    district = "Nagpur",
                    aquiferType = "Alluvial",
                    status = StationStatus.ACTIVE,
                    currentWaterLevel = 7.2,
                    aquiferStress = AquiferStress.LOW
                ),
                selectedTimeRange = TimeRange.WEEK
            ),
            onTimeRangeChanged = {}
        )
    }
}
