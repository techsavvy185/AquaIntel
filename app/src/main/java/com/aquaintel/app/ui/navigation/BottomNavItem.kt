package com.aquaintel.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Dashboard : BottomNavItem(
        route = Screen.Dashboard.route,
        icon = Icons.Default.Dashboard,
        label = "Dashboard"
    )

    object Map : BottomNavItem(
        route = Screen.Map.route,
        icon = Icons.Default.Map,
        label = "Map"
    )

    object Forecast : BottomNavItem(
        route = Screen.Forecast.route,
        icon = Icons.Default.TrendingUp,
        label = "Forecast"
    )

    object Report : BottomNavItem(
        route = Screen.Report.route,
        icon = Icons.Default.Report,
        label = "Report"
    )

    object Settings : BottomNavItem(
        route = Screen.Settings.route,
        icon = Icons.Default.Settings,
        label = "Settings"
    )
}

val bottomNavItems = listOf(
    BottomNavItem.Dashboard,
    BottomNavItem.Map,
    BottomNavItem.Forecast,
    BottomNavItem.Report,
    BottomNavItem.Settings
)
