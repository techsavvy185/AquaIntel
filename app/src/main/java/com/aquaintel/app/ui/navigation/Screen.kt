package com.aquaintel.app.ui.navigation

sealed class Screen(val route: String) {
    // Auth screens
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Signup : Screen("signup")

    // Main screens
    object Dashboard : Screen("dashboard")
    object Map : Screen("map")
    object StationDetail : Screen("station_detail/{stationId}") {
        fun createRoute(stationId: String) = "station_detail/$stationId"
    }
    object Forecast : Screen("forecast")
    object Report : Screen("report")
    object Settings : Screen("settings")

    // AI Assistant
    object GeminiChat : Screen("gemini_chat")
}
