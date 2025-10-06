package com.aquaintel.app.ui.screens.main.forecast

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
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.aquaintel.app.ui.components.DataCard
import com.aquaintel.app.ui.theme.AquaIntelTheme
import com.aquaintel.app.ui.theme.ClashDisplay
import com.aquaintel.app.ui.theme.LocalAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastScreen(
    viewModel: ForecastViewModel = hiltViewModel()
) {
    val uiState by viewModel.forecastUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Forecast & Predictions",
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
            ForecastContent(
                uiState = uiState,
                onPeriodChanged = viewModel::changeForecastPeriod
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ForecastContent(
    uiState: ForecastUiState,
    onPeriodChanged: (ForecastPeriod) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Forecast Period Selector
        item {
            Text(
                text = "Forecast Period",
                style = MaterialTheme.typography.titleMedium,
                color = LocalAppColors.current.onSurface
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(ForecastPeriod.values()) { period ->
                    FilterChip(
                        onClick = { onPeriodChanged(period) },
                        label = { Text(period.displayName) },
                        selected = uiState.forecastPeriod == period,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = LocalAppColors.current.primary,
                            selectedLabelColor = LocalAppColors.current.onPrimary
                        )
                    )
                }
            }
        }

        // Risk Assessment
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        uiState.riskAssessment.contains("Low") -> LocalAppColors.current.success.copy(alpha = 0.1f)
                        uiState.riskAssessment.contains("Moderate") -> LocalAppColors.current.warning.copy(alpha = 0.1f)
                        uiState.riskAssessment.contains("High") -> LocalAppColors.current.error.copy(alpha = 0.1f)
                        else -> LocalAppColors.current.cardBackground
                    }
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = when {
                            uiState.riskAssessment.contains("Low") -> LocalAppColors.current.success
                            uiState.riskAssessment.contains("Moderate") -> LocalAppColors.current.warning
                            else -> LocalAppColors.current.error
                        }
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Column {
                        Text(
                            text = "Risk Assessment",
                            style = MaterialTheme.typography.titleMedium,
                            color = LocalAppColors.current.onSurface
                        )
                        Text(
                            text = uiState.riskAssessment,
                            style = MaterialTheme.typography.bodyMedium,
                            color = LocalAppColors.current.editText
                        )
                    }
                }
            }
        }

        // Prediction Stats
        item {
            Text(
                text = "Prediction Summary",
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
                    title = "Avg Predicted Level",
                    value = uiState.predictions.map { it.predictedWaterLevel }.average().let {
                        "%.1f".format(it)
                    },
                    unit = "m",
                    icon = Icons.Default.TrendingUp,
                    modifier = Modifier.weight(1f)
                )

                DataCard(
                    title = "Confidence",
                    value = uiState.predictions.map { it.confidence }.average().let {
                        "${(it * 100).toInt()}%"
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Seasonal Forecast
        item {
            Text(
                text = "Seasonal Forecast",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = ClashDisplay
                ),
                color = LocalAppColors.current.onSurface
            )
        }

        items(uiState.seasonalForecast.entries.toList()) { (season, forecast) ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LocalAppColors.current.cardBackground
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = season,
                        style = MaterialTheme.typography.titleMedium,
                        color = LocalAppColors.current.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = forecast,
                        style = MaterialTheme.typography.bodyMedium,
                        color = LocalAppColors.current.editText
                    )
                }
            }
        }

        // AI Insights
        item {
            Text(
                text = "AI Insights",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = ClashDisplay
                ),
                color = LocalAppColors.current.onSurface
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LocalAppColors.current.primary.copy(alpha = 0.1f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🤖 AI Recommendation",
                        style = MaterialTheme.typography.titleMedium,
                        color = LocalAppColors.current.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Based on historical data and current trends, consider implementing water conservation measures during the upcoming dry season. Rainfall patterns suggest reduced recharge capacity in the next 60 days.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LocalAppColors.current.editText
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ForecastScreenPreview() {
    AquaIntelTheme {
        ForecastContent(
            uiState = ForecastUiState(
                isLoading = false,
                riskAssessment = "Moderate Risk - Monitor water usage",
                predictions = listOf(),
                seasonalForecast = mapOf(
                    "Summer (Apr-Jun)" to "Expected depletion of 2.5-4.0 meters",
                    "Monsoon (Jul-Sep)" to "Expected recharge of 4.5-6.0 meters"
                )
            ),
            onPeriodChanged = {}
        )
    }
}
