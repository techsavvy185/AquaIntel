package com.aquaintel.app.ui.screens.auth.signup

data class SignupUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val toastMessage: String? = null,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)
