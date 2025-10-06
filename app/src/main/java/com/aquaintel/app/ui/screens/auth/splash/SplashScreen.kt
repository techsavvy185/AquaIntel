package com.aquaintel.app.ui.screens.auth.splash

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aquaintel.app.ui.components.AquaIntelLogo
import com.aquaintel.app.ui.theme.AquaIntelTheme
import com.aquaintel.app.ui.theme.ClashDisplay
import com.aquaintel.app.ui.theme.LocalAppColors

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateToOnboarding: () -> Unit = {},
    onNavigateToDashboard: () -> Unit = {}
) {
    val uiState by viewModel.splashUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkAuthStatus(onNavigateToOnboarding, onNavigateToDashboard)
    }

    SplashScreenLayout(uiState = uiState)
}

@Composable
private fun SplashScreenLayout(uiState: SplashUiState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AquaIntelLogo()
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Real-Time Groundwater Intelligence",
                style = MaterialTheme.typography.titleMedium,
                color = LocalAppColors.current.editText
            )
            Spacer(modifier = Modifier.height(32.dp))
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = LocalAppColors.current.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenPreview() {
    AquaIntelTheme {
        SplashScreenLayout(uiState = SplashUiState())
    }
}
