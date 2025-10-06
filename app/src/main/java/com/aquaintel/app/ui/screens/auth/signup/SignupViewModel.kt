package com.aquaintel.app.ui.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : ViewModel() {

    private val _signupUiState = MutableStateFlow(SignupUiState())
    val signupUiState: StateFlow<SignupUiState> = _signupUiState.asStateFlow()

    fun nameInput(input: String) {
        _signupUiState.update { it.copy(name = input) }
    }

    fun emailInput(input: String) {
        _signupUiState.update { it.copy(email = input) }
    }

    fun passwordInput(input: String) {
        _signupUiState.update { it.copy(password = input) }
    }

    fun confirmPasswordInput(input: String) {
        _signupUiState.update { it.copy(confirmPassword = input) }
    }

    fun clearError() {
        _signupUiState.update { it.copy(toastMessage = null) }
    }

    fun onSignupClicked(onNavigateToDashboard: () -> Unit) {
        val name = _signupUiState.value.name
        val email = _signupUiState.value.email
        val password = _signupUiState.value.password
        val confirmPassword = _signupUiState.value.confirmPassword

        when {
            name.isBlank() -> {
                _signupUiState.update { it.copy(toastMessage = "Name cannot be empty") }
                return
            }
            email.isBlank() -> {
                _signupUiState.update { it.copy(toastMessage = "Email cannot be empty") }
                return
            }
            password.isBlank() -> {
                _signupUiState.update { it.copy(toastMessage = "Password cannot be empty") }
                return
            }
            password != confirmPassword -> {
                _signupUiState.update { it.copy(toastMessage = "Passwords do not match") }
                return
            }
            password.length < 6 -> {
                _signupUiState.update { it.copy(toastMessage = "Password must be at least 6 characters") }
                return
            }
        }

        _signupUiState.update { it.copy(isLoading = true, toastMessage = null) }

        viewModelScope.launch {
            delay(1500) // Simulate network call

            // Mock signup - always succeeds for demo
            _signupUiState.update {
                it.copy(isLoading = false, toastMessage = "Account created successfully")
            }
            delay(500)
            onNavigateToDashboard()
        }
    }
}
