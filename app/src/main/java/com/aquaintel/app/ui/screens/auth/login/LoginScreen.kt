package com.aquaintel.app.ui.screens.auth.login

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aquaintel.app.ui.components.AquaIntelLogo
import com.aquaintel.app.ui.components.EmailField
import com.aquaintel.app.ui.components.PasswordField
import com.aquaintel.app.ui.components.PrimaryButton
import com.aquaintel.app.ui.components.TertiaryButton
import com.aquaintel.app.ui.theme.AquaIntelTheme
import com.aquaintel.app.ui.theme.ClashDisplay
import com.aquaintel.app.ui.theme.LocalAppColors
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToSignup: () -> Unit = {}
) {
    val uiState by viewModel.loginUiState.collectAsState()

    LoginScreenLayout(
        uiState = uiState,
        onEmailInput = viewModel::emailInput,
        onPasswordInput = viewModel::passwordInput,
        onLoginClicked = { viewModel.onLoginClicked(onNavigateToDashboard) },
        onSignupClicked = onNavigateToSignup,
        clearToastMessage = viewModel::clearError
    )
}

@Composable
private fun LoginScreenLayout(
    uiState: LoginUiState,
    onEmailInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onSignupClicked: () -> Unit,
    clearToastMessage: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage != null) {
            scope.launch {
                snackbarHostState.showSnackbar(message = uiState.toastMessage)
            }
            clearToastMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = LocalAppColors.current.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LocalAppColors.current.background)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                AquaIntelLogo(modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = ClashDisplay
                    ),
                    color = LocalAppColors.current.onSurface,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Login to access groundwater insights",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalAppColors.current.editText,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                EmailField(
                    email = uiState.email,
                    onEmailChange = onEmailInput
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordField(
                    password = uiState.password,
                    onPasswordChange = onPasswordInput
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = "Login",
                    onClick = onLoginClicked,
                    isLoading = uiState.isLoading,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                TertiaryButton(
                    text = "Don't have an account? Sign up",
                    onClick = onSignupClicked,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    AquaIntelTheme {
        LoginScreenLayout(
            uiState = LoginUiState(email = "user@example.com", password = "password123"),
            onEmailInput = {},
            onPasswordInput = {},
            onLoginClicked = {},
            onSignupClicked = {},
            clearToastMessage = {}
        )
    }
}
