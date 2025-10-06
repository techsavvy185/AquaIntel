package com.aquaintel.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aquaintel.app.data.models.AquiferStress
import com.aquaintel.app.data.models.DWLRStation
import com.aquaintel.app.data.models.StationStatus
import com.aquaintel.app.ui.theme.LocalAppColors

@Composable
fun StationCard(
    station: DWLRStation,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LocalAppColors.current.cardBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = station.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = LocalAppColors.current.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = LocalAppColors.current.editText
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${station.district}, ${station.state}",
                            style = MaterialTheme.typography.bodySmall,
                            color = LocalAppColors.current.editText
                        )
                    }
                }

                // Status indicator
                StationStatusBadge(status = station.status)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Water Level",
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalAppColors.current.editText
                    )
                    Text(
                        text = "${station.currentWaterLevel} m",
                        style = MaterialTheme.typography.titleMedium,
                        color = LocalAppColors.current.onSurface
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Aquifer Stress",
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalAppColors.current.editText
                    )
                    AquiferStressBadge(stress = station.aquiferStress)
                }
            }
        }
    }
}

@Composable
fun StationStatusBadge(status: StationStatus) {
    val (color, text) = when (status) {
        StationStatus.ACTIVE -> LocalAppColors.current.success to "Active"
        StationStatus.INACTIVE -> LocalAppColors.current.editText to "Inactive"
        StationStatus.MAINTENANCE -> LocalAppColors.current.warning to "Maintenance"
    }

    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = Color.White,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Composable
fun AquiferStressBadge(stress: AquiferStress) {
    val (color, text) = when (stress) {
        AquiferStress.LOW -> LocalAppColors.current.success to "Low"
        AquiferStress.MEDIUM -> LocalAppColors.current.warning to "Medium"
        AquiferStress.HIGH -> Color(0xFFFF6F00) to "High"
        AquiferStress.CRITICAL -> LocalAppColors.current.error to "Critical"
    }

    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = Color.White,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}
