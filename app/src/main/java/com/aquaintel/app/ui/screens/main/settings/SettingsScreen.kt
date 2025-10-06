package com.aquaintel.app.ui.screens.main.settings

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aquaintel.app.ui.theme.AquaIntelTheme
import com.aquaintel.app.ui.theme.ClashDisplay
import com.aquaintel.app.ui.theme.LocalAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.settingsUiState.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Settings",
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

        SettingsContent(
            uiState = uiState,
            onDarkModeToggle = viewModel::toggleDarkMode,
            onNotificationsToggle = viewModel::toggleNotifications,
            onRefreshIntervalChanged = viewModel::updateRefreshInterval,
            onLogoutClick = { showLogoutDialog = true }
        )
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        viewModel.logout {
                            // Navigate to login screen
                        }
                    }
                ) {
                    Text("Logout")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun SettingsContent(
    uiState: SettingsUiState,
    onDarkModeToggle: (Boolean) -> Unit,
    onNotificationsToggle: (Boolean) -> Unit,
    onRefreshIntervalChanged: (RefreshInterval) -> Unit,
    onLogoutClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Profile Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LocalAppColors.current.cardBackground
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = LocalAppColors.current.primary
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Column {
                        Text(
                            text = uiState.userName,
                            style = MaterialTheme.typography.titleLarge,
                            color = LocalAppColors.current.onSurface
                        )
                        Text(
                            text = uiState.userEmail,
                            style = MaterialTheme.typography.bodyMedium,
                            color = LocalAppColors.current.editText
                        )
                    }
                }
            }
        }

        // Appearance Section
        item {
            Text(
                text = "Appearance",
                style = MaterialTheme.typography.titleMedium,
                color = LocalAppColors.current.editText
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LocalAppColors.current.cardBackground
                )
            ) {
                SettingsToggleItem(
                    icon = Icons.Default.DarkMode,
                    title = "Dark Mode",
                    description = "Enable dark theme",
                    checked = uiState.isDarkMode,
                    onCheckedChange = onDarkModeToggle
                )
            }
        }

        // Notifications Section
        item {
            Text(
                text = "Notifications",
                style = MaterialTheme.typography.titleMedium,
                color = LocalAppColors.current.editText
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LocalAppColors.current.cardBackground
                )
            ) {
                SettingsToggleItem(
                    icon = Icons.Default.Notifications,
                    title = "Push Notifications",
                    description = "Receive alerts and updates",
                    checked = uiState.notificationsEnabled,
                    onCheckedChange = onNotificationsToggle
                )
            }
        }

        // Data & Sync Section
        item {
            Text(
                text = "Data & Sync",
                style = MaterialTheme.typography.titleMedium,
                color = LocalAppColors.current.editText
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LocalAppColors.current.cardBackground
                )
            ) {
                Column {
                    SettingsClickableItem(
                        icon = Icons.Default.Refresh,
                        title = "Data Refresh Interval",
                        description = uiState.dataRefreshInterval.displayName,
                        onClick = { /* Show interval picker dialog */ }
                    )
                }
            }
        }

        // About Section
        item {
            Text(
                text = "About",
                style = MaterialTheme.typography.titleMedium,
                color = LocalAppColors.current.editText
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LocalAppColors.current.cardBackground
                )
            ) {
                Column {
                    SettingsClickableItem(
                        icon = Icons.Default.Info,
                        title = "App Version",
                        description = "1.0.0 (Build 1)",
                        onClick = {}
                    )

                    Divider(color = LocalAppColors.current.divider)

                    SettingsClickableItem(
                        icon = Icons.Default.Security,
                        title = "Privacy Policy",
                        description = "View our privacy policy",
                        onClick = {}
                    )

                    Divider(color = LocalAppColors.current.divider)

                    SettingsClickableItem(
                        icon = Icons.Default.Info,
                        title = "Terms of Service",
                        description = "View terms of service",
                        onClick = {}
                    )
                }
            }
        }

        // Logout
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onLogoutClick),
                colors = CardDefaults.cardColors(
                    containerColor = LocalAppColors.current.error.copy(alpha = 0.1f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = null,
                        tint = LocalAppColors.current.error
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Logout",
                        style = MaterialTheme.typography.titleMedium,
                        color = LocalAppColors.current.error
                    )
                }
            }
        }

        // App Info Footer
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "AquaIntel",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontFamily = ClashDisplay
                    ),
                    color = LocalAppColors.current.primary
                )
                Text(
                    text = "Real-Time Groundwater Intelligence Platform",
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalAppColors.current.editText
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ministry of Jal Shakti, Government of India",
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalAppColors.current.editText
                )
            }
        }
    }
}

@Composable
fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = LocalAppColors.current.primary
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = LocalAppColors.current.onSurface
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalAppColors.current.editText
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = LocalAppColors.current.primary,
                checkedTrackColor = LocalAppColors.current.primary.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun SettingsClickableItem(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = LocalAppColors.current.primary
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = LocalAppColors.current.onSurface
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = LocalAppColors.current.editText
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenPreview() {
    AquaIntelTheme {
        SettingsContent(
            uiState = SettingsUiState(
                userName = "Dr. Rajesh Kumar",
                userEmail = "rajesh.kumar@example.com",
                isDarkMode = false,
                notificationsEnabled = true
            ),
            onDarkModeToggle = {},
            onNotificationsToggle = {},
            onRefreshIntervalChanged = {},
            onLogoutClick = {}
        )
    }
}
