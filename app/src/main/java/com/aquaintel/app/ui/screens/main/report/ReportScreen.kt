package com.aquaintel.app.ui.screens.main.report

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aquaintel.app.data.mock.MockStations
import com.aquaintel.app.ui.components.PrimaryButton
import com.aquaintel.app.ui.theme.AquaIntelTheme
import com.aquaintel.app.ui.theme.ClashDisplay
import com.aquaintel.app.ui.theme.LocalAppColors
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    viewModel: ReportViewModel = hiltViewModel()
) {
    val uiState by viewModel.reportUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            scope.launch {
                snackbarHostState.showSnackbar(uiState.error ?: "Unknown error")
            }
            viewModel.clearError()
        }
    }

    LaunchedEffect(uiState.submitSuccess) {
        if (uiState.submitSuccess) {
            scope.launch {
                snackbarHostState.showSnackbar("Report submitted successfully!")
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalAppColors.current.background)
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Community Reports",
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

            ReportContent(
                uiState = uiState,
                onReportTypeChanged = viewModel::updateReportType,
                onStationChanged = viewModel::updateSelectedStation,
                onDescriptionChanged = viewModel::updateDescription,
                onSeverityChanged = viewModel::updateSeverity,
                onSubmitReport = viewModel::submitReport
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReportContent(
    uiState: ReportUiState,
    onReportTypeChanged: (ReportType) -> Unit,
    onStationChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onSeverityChanged: (ReportSeverity) -> Unit,
    onSubmitReport: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Submit New Report Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LocalAppColors.current.cardBackground
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Report,
                            contentDescription = null,
                            tint = LocalAppColors.current.primary
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Submit New Report",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = ClashDisplay
                            ),
                            color = LocalAppColors.current.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Report Type Selection
                    Text(
                        text = "Report Type",
                        style = MaterialTheme.typography.titleMedium,
                        color = LocalAppColors.current.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(ReportType.values()) { type ->
                            FilterChip(
                                onClick = { onReportTypeChanged(type) },
                                label = { Text(type.displayName, maxLines = 1) },
                                selected = uiState.reportType == type,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = LocalAppColors.current.primary,
                                    selectedLabelColor = LocalAppColors.current.onPrimary
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Station Selection
                    var stationExpanded by remember { mutableStateOf(false) }

                    Text(
                        text = "Select Station",
                        style = MaterialTheme.typography.titleMedium,
                        color = LocalAppColors.current.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    ExposedDropdownMenuBox(
                        expanded = stationExpanded,
                        onExpandedChange = { stationExpanded = !stationExpanded }
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            readOnly = true,
                            value = uiState.selectedStation.ifEmpty { "Choose a station" },
                            onValueChange = {},
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = stationExpanded) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = LocalAppColors.current.primary,
                                unfocusedBorderColor = LocalAppColors.current.divider
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = stationExpanded,
                            onDismissRequest = { stationExpanded = false }
                        ) {
                            MockStations.stations.take(10).forEach { station ->
                                DropdownMenuItem(
                                    text = { Text(station.name) },
                                    onClick = {
                                        onStationChanged(station.name)
                                        stationExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Severity Selection
                    Text(
                        text = "Severity Level",
                        style = MaterialTheme.typography.titleMedium,
                        color = LocalAppColors.current.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(ReportSeverity.values()) { severity ->
                            FilterChip(
                                onClick = { onSeverityChanged(severity) },
                                label = { Text(severity.displayName) },
                                selected = uiState.severity == severity,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = when (severity) {
                                        ReportSeverity.LOW -> LocalAppColors.current.success
                                        ReportSeverity.MEDIUM -> LocalAppColors.current.warning
                                        ReportSeverity.HIGH -> Color(0xFFFF6F00)
                                        ReportSeverity.CRITICAL -> LocalAppColors.current.error
                                    },
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Description
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        color = LocalAppColors.current.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uiState.description,
                        onValueChange = onDescriptionChanged,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Describe the issue in detail...") },
                        minLines = 3,
                        maxLines = 5,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = LocalAppColors.current.primary,
                            unfocusedBorderColor = LocalAppColors.current.divider
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PrimaryButton(
                        text = "Submit Report",
                        onClick = onSubmitReport,
                        isLoading = uiState.isSubmitting
                    )
                }
            }
        }

        // Recent Reports
        item {
            Text(
                text = "Recent Community Reports",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = ClashDisplay
                ),
                color = LocalAppColors.current.onSurface
            )
        }

        items(uiState.recentReports) { report ->
            ReportCard(report = report)
        }
    }
}

@Composable
fun ReportCard(report: CommunityReport) {
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
                        text = report.type.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        color = LocalAppColors.current.onSurface
                    )
                    Text(
                        text = report.station,
                        style = MaterialTheme.typography.bodyMedium,
                        color = LocalAppColors.current.editText
                    )
                    Text(
                        text = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
                            .format(Date(report.timestamp)),
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalAppColors.current.editText
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    // Severity Badge
                    Text(
                        text = report.severity.displayName,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                when (report.severity) {
                                    ReportSeverity.LOW -> LocalAppColors.current.success
                                    ReportSeverity.MEDIUM -> LocalAppColors.current.warning
                                    ReportSeverity.HIGH -> Color(0xFFFF6F00)
                                    ReportSeverity.CRITICAL -> LocalAppColors.current.error
                                }
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Status
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (report.status == "Resolved") {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = LocalAppColors.current.success
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                        }
                        Text(
                            text = report.status,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (report.status == "Resolved") LocalAppColors.current.success
                            else LocalAppColors.current.editText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = report.description,
                style = MaterialTheme.typography.bodyMedium,
                color = LocalAppColors.current.editText
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReportScreenPreview() {
    AquaIntelTheme {
        ReportContent(
            uiState = ReportUiState(
                reportType = ReportType.WATER_QUALITY,
                selectedStation = "Nagpur Central DWLR",
                description = "Sample description",
                severity = ReportSeverity.MEDIUM,
                recentReports = listOf(
                    CommunityReport(
                        id = "R001",
                        type = ReportType.WATER_QUALITY,
                        severity = ReportSeverity.HIGH,
                        description = "Unusual taste and color in water from DWLR station",
                        station = "Nagpur Central DWLR",
                        timestamp = System.currentTimeMillis(),
                        status = "Under Review"
                    )
                )
            ),
            onReportTypeChanged = {},
            onStationChanged = {},
            onDescriptionChanged = {},
            onSeverityChanged = {},
            onSubmitReport = {}
        )
    }
}
