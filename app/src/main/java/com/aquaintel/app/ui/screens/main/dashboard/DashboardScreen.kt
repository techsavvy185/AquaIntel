package com.aquaintel.app.ui.screens.main.dashboard

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
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aquaintel.app.data.models.AquiferStress
import com.aquaintel.app.data.models.DWLRStation
import com.aquaintel.app.data.models.StationStatus
import com.aquaintel.app.ui.components.DataCard
import com.aquaintel.app.ui.components.GaugeIndicator
import com.aquaintel.app.ui.components.StationCard
import com.aquaintel.app.ui.components.WaterLevelChart
import com.aquaintel.app.ui.theme.AquaIntelTheme
import com.aquaintel.app.ui.theme.ClashDisplay
import com.aquaintel.app.ui.theme.LocalAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToStationDetail: (String) -> Unit = {},
    onNavigateToGeminiChat: () -> Unit = {}
) {
    val uiState by viewModel.dashboardUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Dashboard",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontFamily = ClashDisplay
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.refreshData() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalAppColors.current.surface,
                    titleContentColor = LocalAppColors.current.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToGeminiChat,
                containerColor = Color(0xFF4285F4),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "Chat with Gemini AI"
                )
            }
        },
        containerColor = LocalAppColors.current.background
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = LocalAppColors.current.primary,
                    modifier = Modifier.size(48.dp)
                )
            }
        } else {
            DashboardContent(
                uiState = uiState,
                onStationClick = onNavigateToStationDetail,
                paddingValues = paddingValues
            )
        }
    }
}

@Composable
private fun DashboardContent(
    uiState: DashboardUiState,
    onStationClick: (String) -> Unit,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = paddingValues.calculateTopPadding() + 16.dp,
            bottom = paddingValues.calculateBottomPadding() + 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Key Metrics Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DataCard(
                    title = "Water Level",
                    value = uiState.currentWaterLevel.toString(),
                    unit = "m",
                    icon = Icons.Default.Water,
                    modifier = Modifier.weight(1f)
                )

                DataCard(
                    title = "Aquifer Stress",
                    value = uiState.aquiferStress,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Soil Quality Gauge
        item {

            Column(
                modifier = Modifier.padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Soil Quality Index",
                    style = MaterialTheme.typography.titleMedium,
                    color = LocalAppColors.current.editText
                )
                Spacer(modifier = Modifier.height(16.dp))
                GaugeIndicator(
                    value = uiState.soilQualityIndex.toFloat(),
                    maxValue = 10f,
                    label = "Out of 10",
                )
            }

        }

        // Recent Water Level Trend
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LocalAppColors.current.cardBackground
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "7-Day Water Level Trend",
                        style = MaterialTheme.typography.titleMedium,
                        color = LocalAppColors.current.onSurface
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (uiState.recentData.isNotEmpty()) {
                        WaterLevelChart(
                            data = uiState.recentData,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        // Alerts Section
        item {
            Text(
                text = "Alerts & Notifications",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = ClashDisplay
                ),
                color = LocalAppColors.current.onSurface
            )
        }

        items(uiState.alerts) { alert ->
            AlertCard(alert = alert)
        }

        // Nearby Stations
        item {
            Text(
                text = "Nearby Stations",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = ClashDisplay
                ),
                color = LocalAppColors.current.onSurface
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(uiState.nearbyStations) { station ->
                    StationCard(
                        station = station,
                        onClick = { onStationClick(station.id) },
                        modifier = Modifier.size(width = 280.dp, height = 120.dp)
                    )
                }
            }
        }

        // Conservation Tips
        item {
            Text(
                text = "Conservation Tips",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = ClashDisplay
                ),
                color = LocalAppColors.current.onSurface
            )
        }

        items(uiState.conservationTips) { tip ->
            ConservationTipCard(tip = tip)
        }
    }
}

@Composable
fun AlertCard(alert: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LocalAppColors.current.warning.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = LocalAppColors.current.warning
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = alert,
                style = MaterialTheme.typography.bodyMedium,
                color = LocalAppColors.current.onSurface
            )
        }
    }
}

@Composable
fun ConservationTipCard(tip: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LocalAppColors.current.success.copy(alpha = 0.1f)
        )
    ) {
        Text(
            text = tip,
            style = MaterialTheme.typography.bodyMedium,
            color = LocalAppColors.current.onSurface,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DashboardScreenPreview() {
    AquaIntelTheme {
        DashboardContent(
            uiState = DashboardUiState(
                isLoading = false,
                currentWaterLevel = 7.2,
                aquiferStress = "Low",
                soilQualityIndex = 8.2,
                alerts = listOf("Water level declining in 3 nearby stations"),
                conservationTips = listOf("Install rainwater harvesting systems"),
                nearbyStations = listOf(
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
                )
            ),
            onStationClick = {}
        )
    }
}
