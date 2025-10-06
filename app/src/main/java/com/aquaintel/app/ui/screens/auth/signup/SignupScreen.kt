package com.aquaintel.app.ui.screens.auth.signup

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    val uiState by viewModel.signupUiState.collectAsState()

    SignupScreenLayout(
        uiState = uiState,
        onNameInput = viewModel::nameInput,
        onEmailInput = viewModel::emailInput,
        onPasswordInput = viewModel::passwordInput,
        onConfirmPasswordInput = viewModel::confirmPasswordInput,
        onSignupClicked = { viewModel.onSignupClicked(onNavigateToDashboard) },
        onLoginClicked = onNavigateToLogin,
        clearToastMessage = viewModel::clearError
    )
}

@Composable
private fun SignupScreenLayout(
    uiState: SignupUiState,
    onNameInput: (String) -> Unit,
    onEmailInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onConfirmPasswordInput: (String) -> Unit,
    onSignupClicked: () -> Unit,
    onLoginClicked: () -> Unit,
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
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {
                AquaIntelLogo(modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = ClashDisplay
                    ),
                    color = LocalAppColors.current.onSurface,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Join us to monitor groundwater resources",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalAppColors.current.editText,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                com.aquaintel.app.ui.components.NameField(
                    name = uiState.name,
                    onNameChange = onNameInput
                )

                Spacer(modifier = Modifier.height(16.dp))

                EmailField(
                    email = uiState.email,
                    onEmailChange = onEmailInput
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordField(
                    password = uiState.password,
                    onPasswordChange = onPasswordInput
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordField(
                    password = uiState.confirmPassword,
                    onPasswordChange = onConfirmPasswordInput,
                    label = "Confirm Password"
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = "Sign Up",
                    onClick = onSignupClicked,
                    isLoading = uiState.isLoading,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                TertiaryButton(
                    text = "Already have an account? Login",
                    onClick = onLoginClicked,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignupScreenPreview() {
    AquaIntelTheme {
        SignupScreenLayout(
            uiState = SignupUiState(),
            onNameInput = {},
            onEmailInput = {},
            onPasswordInput = {},
            onConfirmPasswordInput = {},
            onSignupClicked = {},
            onLoginClicked = {},
            clearToastMessage = {}
        )
    }
}
