package com.aquaintel.app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aquaintel.app.ui.screens.auth.login.LoginScreen
import com.aquaintel.app.ui.screens.auth.onboarding.OnboardingScreen
import com.aquaintel.app.ui.screens.auth.signup.SignupScreen
import com.aquaintel.app.ui.screens.auth.splash.SplashScreen
import com.aquaintel.app.ui.screens.main.dashboard.DashboardScreen
import com.aquaintel.app.ui.screens.main.forecast.ForecastScreen
import com.aquaintel.app.ui.screens.main.gemini.GeminiChatScreen
import com.aquaintel.app.ui.screens.main.map.MapScreen
import com.aquaintel.app.ui.screens.main.report.ReportScreen
import com.aquaintel.app.ui.screens.main.settings.SettingsScreen
import com.aquaintel.app.ui.screens.main.stationdetail.StationDetailScreen
import com.aquaintel.app.ui.theme.LocalAppColors

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Auth Flow
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // Main Flow with Bottom Navigation
        composable(Screen.Dashboard.route) {
            MainScreenWithBottomNav(navController = navController)
        }

        composable(Screen.Map.route) {
            MainScreenWithBottomNav(navController = navController)
        }

        composable(Screen.Forecast.route) {
            MainScreenWithBottomNav(navController = navController)
        }

        composable(Screen.Report.route) {
            MainScreenWithBottomNav(navController = navController)
        }

        composable(Screen.Settings.route) {
            MainScreenWithBottomNav(navController = navController)
        }

        composable(
            route = Screen.StationDetail.route,
            arguments = listOf(navArgument("stationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val stationId = backStackEntry.arguments?.getString("stationId") ?: ""
            StationDetailScreen(
                stationId = stationId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Gemini Chat Screen
        composable(Screen.GeminiChat.route) {
            GeminiChatScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun MainScreenWithBottomNav(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = LocalAppColors.current.surface
            ) {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = LocalAppColors.current.primary,
                            selectedTextColor = LocalAppColors.current.primary,
                            unselectedIconColor = LocalAppColors.current.editText,
                            unselectedTextColor = LocalAppColors.current.editText,
                            indicatorColor = LocalAppColors.current.primary.copy(alpha = 0.1f)
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentDestination?.route) {
                Screen.Dashboard.route -> DashboardScreen(
                    onNavigateToStationDetail = { stationId ->
                        navController.navigate(Screen.StationDetail.createRoute(stationId))
                    },
                    onNavigateToGeminiChat = {
                        navController.navigate(Screen.GeminiChat.route)
                    }
                )
                Screen.Map.route -> MapScreen(
                    onNavigateToStationDetail = { stationId ->
                        navController.navigate(Screen.StationDetail.createRoute(stationId))
                    }
                )
                Screen.Forecast.route -> ForecastScreen()
                Screen.Report.route -> ReportScreen()
                Screen.Settings.route -> SettingsScreen()
            }
        }
    }
}
